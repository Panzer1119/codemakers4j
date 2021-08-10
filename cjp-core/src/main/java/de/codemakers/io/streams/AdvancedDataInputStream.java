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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AdvancedDataInputStream extends DataInputStream {
    
    /**
     * Creates a DataInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */
    public AdvancedDataInputStream(InputStream in) {
        super(in);
    }
    
    public byte[] readBytes() throws IOException {
        final int length = readInt();
        if (length < 0) {
            return null;
        }
        final byte[] temp = new byte[length];
        read(temp);
        return temp;
    }
    
}
