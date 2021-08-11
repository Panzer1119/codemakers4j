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

import de.codemakers.base.Standard;
import de.codemakers.io.streams.exceptions.StreamClosedException;

import java.io.IOException;
import java.io.InputStream;

public class EndableInputStream extends InputStream {
    
    public static final int CLOSED_INT = EndableOutputStream.CLOSED_INT;
    //
    public static final byte ESCAPE_BYTE = EndableOutputStream.ESCAPE_BYTE;
    public static final int ESCAPE_BYTE_INT = EndableOutputStream.ESCAPE_BYTE_INT;
    //
    public static final byte ENDED_BYTE = EndableOutputStream.ENDED_BYTE;
    public static final int ENDED_BYTE_INT = EndableOutputStream.ENDED_BYTE_INT;
    
    protected final InputStream inputStream;
    
    public EndableInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    @Override
    public int read() throws IOException {
        final int temp = inputStream.read();
        if (temp == ESCAPE_BYTE) {
            return inputStream.read();
        } else if (temp == ENDED_BYTE) {
            Standard.silentClose(this);
            throw new StreamClosedException();
        }
        return temp;
    }
    
    @Override
    public int available() throws IOException {
        return inputStream.available();
    }
    
    @Override
    public void close() throws IOException {
        inputStream.close();
    }
    
    @Override
    public synchronized void mark(int readlimit) {
        inputStream.mark(readlimit);
    }
    
    @Override
    public synchronized void reset() throws IOException {
        inputStream.reset();
    }
    
    @Override
    public boolean markSupported() {
        return inputStream.markSupported();
    }
    
}
