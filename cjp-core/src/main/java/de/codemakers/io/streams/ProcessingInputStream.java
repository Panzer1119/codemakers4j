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

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ProcessingInputStream extends FilterInputStream {
    
    private final InputStream inputStream;
    private Processor processor;
    private byte[] inputBuffer = new byte[512];
    private boolean done = false;
    private byte[] outputBuffer;
    private int outputStart = 0;
    private int outputFinish = 0;
    private boolean closed = false;
    
    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param inputStream the underlying input stream, or <code>null</code> if
     * this instance is to be created without an underlying stream.
     */
    protected ProcessingInputStream(InputStream inputStream) {
        this(inputStream, null);
    }
    
    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * @param inputStream the underlying input stream, or <code>null</code> if
     * @param processor Processor
     * this instance is to be created without an underlying stream.
     */
    public ProcessingInputStream(InputStream inputStream, Processor processor) {
        super(inputStream);
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(processor);
        this.inputStream = inputStream;
        this.processor = processor;
    }
    
    private int getMoreData() throws IOException {
        if (done) {
            return -1;
        } else {
            int read = inputStream.read(inputBuffer);
            if (read == -1) {
                this.done = true;
                try {
                    outputBuffer = processor.doFinal();
                } catch (Exception ex) {
                    outputBuffer = null;
                    throw new IOException(ex);
                }
                if (outputBuffer == null) {
                    return -1;
                } else {
                    outputStart = 0;
                    outputFinish = outputBuffer.length;
                    return outputFinish;
                }
            } else {
                try {
                    outputBuffer = processor.process(inputBuffer, 0, read);
                } catch (Exception ex) {
                    outputBuffer = null;
                    throw ex;
                }
                outputStart = 0;
                if (outputBuffer == null) {
                    outputFinish = 0;
                } else {
                    outputFinish = outputBuffer.length;
                }
                return outputFinish;
            }
        }
    }
    
    @Override
    public int read() throws IOException {
        if (outputStart >= outputFinish) {
            int read = getMoreData();
            while (read == 0) {
                read = getMoreData();
            }
            if (read == -1) {
                return -1;
            }
        }
        return outputBuffer[outputStart++] & 255;
    }
    
    @Override
    public int read(byte[] bytes) throws IOException {
        return read(bytes, 0, bytes.length);
    }
    
    @Override
    public int read(byte[] bytes, int offset, int length) throws IOException {
        int read = 0;
        if (outputStart >= outputFinish) {
            while (read == 0) {
                read = getMoreData();
            }
            if (read == -1) {
                return -1;
            }
        }
        if (length <= 0) {
            return 0;
        } else {
            int amount = outputFinish - outputStart;
            if (length < amount) {
                amount = length;
            }
            if (bytes != null) {
                System.arraycopy(outputBuffer, outputStart, bytes, offset, amount);
            }
            outputStart += amount;
            return amount;
        }
    }
    
    @Override
    public long skip(long amount) {
        if (amount <= 0) {
            return 0;
        }
        final int available = outputFinish - outputStart;
        if (amount > available) {
            amount = available;
        }
        outputStart += amount;
        return amount;
    }
    
    @Override
    public int available() {
        return outputFinish - outputStart;
    }
    
    @Override
    public void close() throws IOException {
        if (!closed) {
            inputStream.close();
            if (!done) {
                try {
                    processor.doFinal();
                } catch (Exception ex) {
                    //Nothing
                }
            }
            outputStart = 0;
            outputFinish = 0;
            closed = true;
        }
    }
    
    @Override
    public boolean markSupported() {
        return false;
    }
    
    public Processor getProcessor() {
        return processor;
    }
    
    public ProcessingInputStream setProcessor(Processor processor) {
        this.processor = processor;
        return this;
    }
    
}
