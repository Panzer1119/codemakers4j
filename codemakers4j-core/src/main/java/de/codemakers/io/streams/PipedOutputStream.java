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

import java.io.IOException;
import java.io.OutputStream;

public class PipedOutputStream extends OutputStream {
    
    protected PipedInputStream pipedInputStream = null;
    
    public PipedOutputStream() {
    }
    
    public PipedOutputStream(PipedInputStream pipedInputStream) throws IOException {
        connect(pipedInputStream);
    }
    
    /**
     * Connects this {@link PipedOutputStream} to a receiver. If this object
     * is already connected to some other {@link PipedInputStream}, an
     * {@link IOException} is thrown.
     * <p>
     * If <code>pipedInputStream</code> is an unconnected {@link PipedInputStream} and
     * <code>pipedOutputStream</code> is an unconnected {@link PipedOutputStream}, they may
     * be connected by either the call:
     * <blockquote><pre>
     * pipedOutputStream.connect(pipedInputStream)</pre></blockquote>
     * or the call:
     * <blockquote><pre>
     * pipedInputStream.connect(pipedOutputStream)</pre></blockquote>
     * The two calls have the same effect.
     *
     * @param pipedInputStream the {@link PipedInputStream} to connect to.
     *
     * @throws IOException if an I/O error occurs.
     * @see java.io.PipedOutputStream#connect(java.io.PipedInputStream)
     */
    public synchronized void connect(PipedInputStream pipedInputStream) throws IOException {
        if (pipedInputStream == null) {
            throw new NullPointerException();
        } else if (this.pipedInputStream != null || pipedInputStream.connected) {
            throw new IOException("Already connected");
        }
        this.pipedInputStream = pipedInputStream;
        pipedInputStream.in = -1;
        pipedInputStream.out = 0;
        pipedInputStream.connected = true;
    }
    
    /**
     * Writes the specified <code>byte</code> to the {@link PipedOutputStream}.
     * <p>
     * Implements the <code>write</code> method of <code>OutputStream</code>.
     *
     * @param b the <code>byte</code> to be written.
     *
     * @throws IOException if the Pipe is <a href=#BROKEN> broken</a>,
     * {@link #connect(PipedInputStream) unconnected},
     * closed, or if an I/O error occurs.
     * @see java.io.PipedOutputStream#write(int)
     */
    @Override
    public synchronized void write(int b) throws IOException {
        if (pipedInputStream == null) {
            throw new IOException("Pipe not connected");
        }
        pipedInputStream.receive(b);
    }
    
    @Override
    public synchronized void write(byte[] data) throws IOException {
        write(data, 0, data.length);
    }
    
    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to this {@link PipedOutputStream}.
     * This method blocks until all the bytes are written to the output
     * stream.
     *
     * @param data the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     *
     * @throws IOException if the Pipe is <a href=#BROKEN> broken</a>,
     * {@link #connect(PipedInputStream) unconnected},
     * closed, or if an I/O error occurs.
     * @see java.io.PipedOutputStream#write(byte[], int, int)
     */
    public synchronized void write(byte[] data, int off, int len) throws IOException {
        if (pipedInputStream == null) {
            throw new IOException("Pipe not connected");
        } else if (data == null) {
            throw new NullPointerException();
        } else if ((off < 0) || (off > data.length) || (len < 0) || ((off + len) > data.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        pipedInputStream.receive(data, off, len);
    }
    
    /**
     * Flushes this {@link PipedOutputStream} and forces any buffered output bytes
     * to be written out.
     * This will notify any readers that bytes are waiting in the pipe.
     *
     * @throws IOException if an I/O error occurs.
     * @see java.io.PipedOutputStream#flush()
     */
    public synchronized void flush() throws IOException {
        if (pipedInputStream != null) {
            synchronized (pipedInputStream) {
                pipedInputStream.notifyAll();
            }
        }
    }
    
    /**
     * Closes this {@link PipedOutputStream} and releases any system resources
     * associated with this stream. This stream may no longer be used for
     * writing bytes.
     *
     * @throws IOException if an I/O error occurs.
     * @see java.io.PipedOutputStream#close()
     */
    public synchronized void close() throws IOException {
        if (pipedInputStream != null) {
            pipedInputStream.receivedLast();
        }
    }
    
}
