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
import java.io.InputStream;
import java.io.InterruptedIOException;

public class PipedInputStream extends InputStream {
    
    /**
     * The default size of the pipe's circular input buffer.
     */
    public static final int DEFAULT_PIPE_SIZE = 1024;
    
    boolean closedByWriter = false;
    volatile boolean closedByReader = false;
    boolean connected = false;
    
    /**
     * The circular buffer into which incoming data is placed.
     */
    protected volatile byte buffer[] = null;
    
    /**
     * The index of the position in the circular buffer at which the
     * next byte of data will be stored when received from the connected
     * {@link PipedOutputStream}. <code>in&lt;0</code> implies the buffer is empty,
     * <code>in==out</code> implies the buffer is full
     */
    protected volatile int in = -1;
    
    /**
     * The index of the position in the circular buffer at which the next
     * byte of data will be read by this {@link PipedInputStream}.
     */
    protected volatile int out = 0;
    
    public PipedInputStream() {
        this(DEFAULT_PIPE_SIZE);
    }
    
    public PipedInputStream(int pipeSize) {
        initPipe(pipeSize);
    }
    
    public PipedInputStream(PipedOutputStream pipedOutputStream) throws IOException {
        this(pipedOutputStream, DEFAULT_PIPE_SIZE);
    }
    
    public PipedInputStream(PipedOutputStream pipedOutputStream, int pipSize) throws IOException {
        connect(pipedOutputStream);
        initPipe(pipSize);
    }
    
    /**
     * Causes this {@link PipedInputStream} to be connected
     * to the {@link PipedOutputStream} <code>pipedOutputStream</code>.
     * If this object is already connected to some
     * other {@link PipedOutputStream}, an {@link IOException}
     * is thrown.
     * <p>
     * If <code>pipedOutputStream</code> is an
     * unconnected {@link PipedOutputStream} and <code>pipedInputStream</code>
     * is an unconnected {@link PipedInputStream}, they
     * may be connected by either the call:
     *
     * <pre><code>pipedInputStream.connect(pipedOutputStream)</code> </pre>
     * <p>
     * or the call:
     *
     * <pre><code>pipedOutputStream.connect(pipedInputStream)</code> </pre>
     * <p>
     * The two calls have the same effect.
     *
     * @param pipedOutputStream The piped output stream to connect to.
     *
     * @throws IOException if an I/O error occurs.
     * @see java.io.PipedInputStream#connect(java.io.PipedOutputStream)
     */
    public void connect(PipedOutputStream pipedOutputStream) throws IOException {
        pipedOutputStream.connect(this);
    }
    
    private void initPipe(int pipeSize) {
        if (pipeSize <= 0) {
            throw new IllegalArgumentException("Pipe Size <= 0");
        }
        buffer = new byte[pipeSize];
    }
    
    /**
     * Receives a byte of data.  This method will block if no input is
     * available.
     *
     * @param b the byte being received
     *
     * @throws IOException If the Pipe is <a href="#BROKEN"> <code>broken</code></a>,
     * {@link #connect(PipedOutputStream) unconnected},
     * closed, or if an I/O error occurs.
     */
    protected void receive(int b) throws IOException {
        checkStateForReceive();
        if (in == out) {
            awaitSpace();
        }
        if (in < 0) {
            in = 0;
            out = 0;
        }
        buffer[in++] = (byte) (b & 0xFF);
        if (in >= buffer.length) {
            in = 0;
        }
        synchronized (this) {
            notify();
        }
    }
    
    /**
     * Receives data into an array of bytes.  This method will
     * block until some input is available.
     *
     * @param data the buffer into which the data is received
     * @param off the start offset of the data
     * @param len the maximum number of bytes received
     *
     * @throws IOException If the Pipe is <a href="#BROKEN"> broken</a>,
     * {@link #connect(PipedOutputStream) unconnected},
     * closed,or if an I/O error occurs.
     */
    protected void receive(byte[] data, int off, int len) throws IOException {
        checkStateForReceive();
        int bytesToTransfer = len;
        while (bytesToTransfer > 0) {
            if (in == out) {
                awaitSpace();
            }
            int nextTransferAmount = 0;
            if (out < in) {
                nextTransferAmount = buffer.length - in;
            } else if (in < out) {
                if (in == -1) {
                    in = out = 0;
                    nextTransferAmount = buffer.length - in;
                } else {
                    nextTransferAmount = out - in;
                }
            }
            if (nextTransferAmount > bytesToTransfer) {
                nextTransferAmount = bytesToTransfer;
            }
            assert (nextTransferAmount > 0);
            System.arraycopy(data, off, buffer, in, nextTransferAmount);
            bytesToTransfer -= nextTransferAmount;
            off += nextTransferAmount;
            in += nextTransferAmount;
            if (in >= buffer.length) {
                in = 0;
            }
        }
        synchronized (this) {
            notify();
        }
    }
    
    private void checkStateForReceive() throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByWriter || closedByReader) {
            throw new IOException("Pipe closed");
        }
    }
    
    private void awaitSpace() throws IOException {
        while (in == out) {
            checkStateForReceive();
            //Full: kick any waiting readers
            notifyAll();
            try {
                wait(1000);
            } catch (InterruptedException ex) {
                throw new InterruptedIOException();
            }
        }
    }
    
    /**
     * Notifies all waiting threads that the last byte of data has been
     * received.
     */
    synchronized void receivedLast() {
        closedByWriter = true;
        notifyAll();
    }
    
    /**
     * Reads the next byte of data from this {@link PipedInputStream}. The
     * value byte is returned as an <code>int</code> in the range
     * <code>0</code> to <code>255</code>.
     * This method blocks until input data is available, the end of the
     * stream is detected, or an exception is thrown.
     *
     * @return the next byte of data, or <code>-1</code> if the end of the
     * stream is reached.
     *
     * @throws IOException if the Pipe is
     * {@link #connect(PipedOutputStream) unconnected},
     * <a href="#BROKEN"> <code>broken</code></a>, closed,
     * or if an I/O error occurs.
     * @see java.io.PipedInputStream#read()
     */
    @Override
    public synchronized int read() throws IOException {
        if (!connected) {
            throw new IOException("Pipe not connected");
        } else if (closedByReader) {
            throw new IOException("Pipe closed");
        }
        while (in < 0) {
            if (closedByWriter) {
                return -1;
            }
            try {
                wait(100);
            } catch (InterruptedException ex) {
                throw new InterruptedIOException();
            }
        }
        final int temp = buffer[out++] & 0xFF;
        if (out >= buffer.length) {
            out = 0;
        }
        if (in == out) {
            //Now empty
            in = -1;
        }
        return temp;
    }
    
    @Override
    public synchronized int read(byte[] data) throws IOException {
        return read(data, 0, data.length);
    }
    
    /**
     * Reads up to <code>len</code> bytes of data from this {@link PipedInputStream}
     * into an array of bytes. Less than <code>len</code> bytes
     * will be read if the end of the data stream is reached or if
     * <code>len</code> exceeds the pipe's buffer size.
     * If <code>len </code> is zero, then no bytes are read and 0 is returned;
     * otherwise, the method blocks until at least 1 byte of input is
     * available, end of the stream has been detected, or an exception is
     * thrown.
     *
     * @param data the buffer into which the data is read.
     * @param off the start offset in the destination array <code>b</code>
     * @param len the maximum number of bytes read.
     *
     * @return the total number of bytes read into the buffer, or
     * <code>-1</code> if there is no more data because the end of
     * the stream has been reached.
     *
     * @throws NullPointerException If <code>b</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @throws IOException if the Pipe is <a href="#BROKEN"> <code>broken</code></a>,
     * {@link #connect(PipedOutputStream) unconnected},
     * closed, or if an I/O error occurs.
     * @see java.io.PipedInputStream#read(byte[], int, int)
     */
    public synchronized int read(byte[] data, int off, int len) throws IOException {
        if (data == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > data.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }
        //possibly wait on the first character
        final int b = read();
        if (b < 0) {
            return -1;
        }
        data[off] = (byte) b;
        int read = 1;
        while ((in >= 0) && (len > 1)) {
            int available = -1;
            if (in > out) {
                available = Math.min((buffer.length - out), (in - out));
            } else {
                available = buffer.length - out;
            }
            //A byte is read beforehand outside the loop
            if (available > (len - 1)) {
                available = len - 1;
            }
            System.arraycopy(buffer, out, data, off + read, available);
            out += available;
            read += available;
            len -= available;
            if (out >= buffer.length) {
                out = 0;
            }
            if (in == out) {
                //Now empty
                in = -1;
            }
        }
        return read;
    }
    
    /**
     * Returns the number of bytes that can be read from this input
     * stream without blocking.
     *
     * @return the number of bytes that can be read from this input stream
     * without blocking, or {@code 0} if this {@link PipedInputStream} has been
     * closed by invoking its {@link #close()} method, or if the Pipe
     * is {@link #connect(PipedOutputStream) unconnected}, or
     * <a href="#BROKEN"> <code>broken</code></a>.
     *
     * @throws IOException if an I/O error occurs.
     * @see java.io.PipedInputStream#available()
     */
    public synchronized int available() throws IOException {
        if (in < 0) {
            return 0;
        } else if (in == out) {
            return buffer.length;
        } else if (in > out) {
            return in - out;
        } else {
            return in + buffer.length - out;
        }
    }
    
    /**
     * Closes this {@link PipedInputStream} and releases any system resources
     * associated with the stream.
     *
     * @throws IOException if an I/O error occurs.
     * @see java.io.PipedInputStream#close()
     */
    public void close() throws IOException {
        closedByReader = true;
        synchronized (this) {
            in = -1;
        }
    }
    
}
