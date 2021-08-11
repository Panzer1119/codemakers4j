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

import de.codemakers.io.processing.Processor;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class ProcessingOutputStream extends FilterOutputStream {
    
    private final OutputStream outputStream;
    private Processor processor;
    private byte[] outputBuffer = null;
    private boolean closed = false;
    
    /**
     * Creates an output stream filter built on top of the specified
     * underlying output stream.
     *
     * @param outputStream the underlying output stream to be assigned to
     * the field {@code this.out} for later use, or
     * <code>null</code> if this instance is to be
     * created without an underlying stream.
     */
    protected ProcessingOutputStream(OutputStream outputStream) {
        this(outputStream, null);
    }
    
    /**
     * Creates an output stream filter built on top of the specified
     * underlying output stream.
     *
     * @param outputStream the underlying output stream to be assigned to
     * @param processor Processor
     * the field {@code this.out} for later use, or
     * <code>null</code> if this instance is to be
     * created without an underlying stream.
     */
    public ProcessingOutputStream(OutputStream outputStream, Processor processor) {
        super(outputStream);
        Objects.requireNonNull(outputStream);
        Objects.requireNonNull(processor);
        this.outputStream = outputStream;
        this.processor = processor;
    }
    
    @Override
    public void write(int b) throws IOException {
        outputBuffer = processor.process((byte) b);
        if (outputBuffer != null) {
            outputStream.write(outputBuffer);
            outputBuffer = null;
        }
    }
    
    @Override
    public void write(byte[] bytes) throws IOException {
        outputBuffer = processor.process(bytes);
        if (outputBuffer != null) {
            outputStream.write(outputBuffer);
            outputBuffer = null;
        }
    }
    
    @Override
    public void flush() throws IOException {
        if (outputBuffer != null) {
            outputStream.write(outputBuffer);
            outputBuffer = null;
        }
        super.flush();
    }
    
    @Override
    public void close() throws IOException {
        if (!closed) {
            try {
                outputBuffer = processor.doFinal();
            } catch (Exception ex) {
                outputBuffer = null;
            }
            try {
                flush();
            } catch (Exception ex) {
                //Nothing
            }
            outputStream.close();
            closed = true;
        }
    }
    
    public Processor getProcessor() {
        return processor;
    }
    
    public ProcessingOutputStream setProcessor(Processor processor) {
        this.processor = processor;
        return this;
    }
    
}
