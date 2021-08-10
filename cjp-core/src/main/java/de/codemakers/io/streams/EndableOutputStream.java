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

public class EndableOutputStream extends OutputStream {
    
    public static final int CLOSED_INT = -1;
    //
    public static final byte ESCAPE_BYTE = Byte.MIN_VALUE;
    public static final int ESCAPE_BYTE_INT = ESCAPE_BYTE & 0xFF;
    //
    public static final byte ENDED_BYTE = -2;
    public static final int ENDED_BYTE_INT = ENDED_BYTE & 0xFF;
    
    protected final OutputStream outputStream;
    
    public EndableOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    
    @Override
    public void write(int b) throws IOException {
        if (((byte) b) == ESCAPE_BYTE || ((byte) b) == ENDED_BYTE) {
            outputStream.write(ESCAPE_BYTE_INT);
        }
        outputStream.write(b);
    }
    
    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }
    
    @Override
    public void close() throws IOException {
        outputStream.write(ENDED_BYTE);
        flush();
        outputStream.close();
    }
    
}
