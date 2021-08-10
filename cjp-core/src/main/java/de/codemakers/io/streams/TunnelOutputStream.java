/*
 *    Copyright 2018 - 2021 Paul Hagedorn (Panzer1119)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.codemakers.io.streams;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import de.codemakers.base.util.ConvertUtil;
import de.codemakers.io.streams.exceptions.StreamClosedException;

import java.io.IOException;
import java.io.OutputStream;

public class TunnelOutputStream extends OutputStream {
    
    public static final int DEFAULT_BUFFER_SIZE = 256;
    public static final int MAXIMUM_STREAMS_PER_TUNNEL = Byte.MAX_VALUE - Byte.MIN_VALUE + 1;
    
    protected final OutputStream outputStream;
    protected final BiMap<Byte, EndableOutputStream> outputStreams = Maps.synchronizedBiMap(HashBiMap.create());
    private final int bufferSize;
    private final transient byte[][] buffers;
    private final transient int[] bufferLengths = new int[MAXIMUM_STREAMS_PER_TUNNEL];
    
    public TunnelOutputStream(OutputStream outputStream) {
        this(outputStream, DEFAULT_BUFFER_SIZE);
    }
    
    public TunnelOutputStream(OutputStream outputStream, int bufferSize) {
        this.outputStream = outputStream;
        this.bufferSize = bufferSize;
        this.buffers = new byte[MAXIMUM_STREAMS_PER_TUNNEL][bufferSize];
    }
    
    public OutputStream getOutputStream() {
        return outputStream;
    }
    
    public BiMap<Byte, EndableOutputStream> getOutputStreams() {
        return outputStreams;
    }
    
    public EndableOutputStream getOutputStream(byte id) {
        return outputStreams.get(id);
    }
    
    public byte getId(EndableOutputStream endableOutputStream) {
        return outputStreams.inverse().get(endableOutputStream);
    }
    
    public byte getLowestId() {
        return outputStreams.keySet().stream().sorted().findFirst().orElse(Byte.MIN_VALUE);
    }
    
    public byte getHighestId() {
        return outputStreams.keySet().stream().sorted().skip(outputStreams.size() - 1).findFirst().orElse(Byte.MIN_VALUE);
    }
    
    protected synchronized byte getNextId() {
        byte id = Byte.MIN_VALUE;
        while (outputStreams.containsKey(id)) {
            if (id == Byte.MAX_VALUE) {
                throw new ArrayIndexOutOfBoundsException("There is no id left for another " + EndableOutputStream.class.getSimpleName());
            }
            id++;
        }
        return id;
    }
    
    public static int getIndexFromId(byte id) {
        return id & 0xFF;
    }
    
    public static byte getIdFromIndex(int index) {
        return (byte) index;
    }
    
    private byte[] getBuffer(byte id) {
        return buffers[getIndexFromId(id)];
    }
    
    private TunnelOutputStream setBuffer(byte id, byte[] buffer) {
        buffers[getIndexFromId(id)] = buffer;
        return this;
    }
    
    private int getBufferLength(byte id) {
        return bufferLengths[getIndexFromId(id)];
    }
    
    private TunnelOutputStream setBufferLength(byte id, int bufferLength) {
        bufferLengths[getIndexFromId(id)] = bufferLength;
        return this;
    }
    
    @Override
    public synchronized void write(int b) throws IOException {
        outputStream.write(b);
    }
    
    @Override
    public synchronized void flush() throws IOException {
        outputStream.flush();
    }
    
    @Override
    public synchronized void close() throws IOException {
        outputStream.close();
    }
    
    protected synchronized void write(byte id, int b) throws IOException {
        if (!outputStreams.containsKey(id)) {
            throw new StreamClosedException("There is no " + EndableOutputStream.class.getSimpleName() + " with the id " + id);
        }
        appendToBufferOrSendAll(id, b);
    }
    
    protected synchronized void appendToBufferOrSendAll(byte id, int b) throws IOException {
        if (bufferSize == 0) {
            writeId(id);
            writeLength(1);
            write(b);
            return;
        }
        getBuffer(id)[getBufferLength(id)] = (byte) b;
        setBufferLength(id, getBufferLength(id) + 1);
        if (getBufferLength(id) == bufferSize) {
            flushBuffer(id);
        }
    }
    
    protected synchronized void flushBuffer(byte id) throws IOException {
        final int length = getBufferLength(id);
        if (length == 0) {
            return;
        }
        writeId(id);
        writeLength(length);
        write(getBuffer(id), 0, length);
        setBufferLength(id, 0);
    }
    
    protected synchronized void writeId(byte id) throws IOException {
        write(id & 0xFF);
    }
    
    protected synchronized void writeLength(int length) throws IOException {
        write(ConvertUtil.intToByteArray(length));
    }
    
    protected synchronized void removeOutputStream(byte id) {
        outputStreams.remove(id);
        setBuffer(id, null);
        setBufferLength(id, 0);
    }
    
    public EndableOutputStream createOutputStream() {
        return getOrCreateOutputStream(getNextId());
    }
    
    public EndableOutputStream getOrCreateOutputStream(byte id) {
        if (outputStreams.containsKey(id)) {
            return outputStreams.get(id);
        }
        setBuffer(id, new byte[bufferSize]);
        setBufferLength(id, 0);
        final OutputStream outputStream = new OutputStream() {
            private boolean closed = false;
            
            @Override
            public synchronized void write(int b) throws IOException {
                if (closed) {
                    throw new StreamClosedException("There is no " + EndableOutputStream.class.getSimpleName() + " with the id " + id);
                }
                TunnelOutputStream.this.write(id, b);
            }
            
            @Override
            public synchronized void flush() throws IOException {
                if (closed) {
                    throw new StreamClosedException("There is no " + EndableOutputStream.class.getSimpleName() + " with the id " + id);
                }
                TunnelOutputStream.this.flushBuffer(id);
                TunnelOutputStream.this.flush();
            }
            
            @Override
            public synchronized void close() throws IOException {
                if (closed) {
                    throw new StreamClosedException("There is no " + EndableOutputStream.class.getSimpleName() + " with the id " + id);
                }
                flush();
                TunnelOutputStream.this.removeOutputStream(id);
                closed = true;
            }
        };
        final EndableOutputStream endableOutputStream = new EndableOutputStream(outputStream);
        outputStreams.put(id, endableOutputStream);
        return endableOutputStream;
    }
    
    @Override
    public String toString() {
        return "TunnelOutputStream{" + "outputStream=" + outputStream + ", outputStreams=" + outputStreams + ", bufferSize=" + bufferSize + '}';
    }
    
}
