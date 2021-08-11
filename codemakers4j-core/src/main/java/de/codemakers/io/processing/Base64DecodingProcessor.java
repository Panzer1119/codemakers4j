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

package de.codemakers.io.processing;

import java.util.Arrays;
import java.util.Base64;

public class Base64DecodingProcessor extends Processor {
    
    public static final int OUTPUT_BYTES = 4;
    
    private int index = 0;
    
    public Base64DecodingProcessor() {
        this.buffer = new byte[OUTPUT_BYTES];
    }
    
    @Override
    public byte[] process(byte[] bytes, int offset, int length) {
        if (offset > 0) {
            System.arraycopy(bytes, offset, bytes, 0, bytes.length - offset);
            bytes = Arrays.copyOf(bytes, bytes.length - offset);
        }
        if (length != bytes.length) {
            bytes = Arrays.copyOf(bytes, length);
        }
        byte[] output = new byte[0];
        for (byte b : bytes) {
            final byte[] temp = process(b);
            if (temp != null) {
                //output = ArrayUtil.concatArrays(output, temp); //Maybe bad for the performance
                output = Arrays.copyOf(output, output.length + temp.length);
                System.arraycopy(temp, 0, output, output.length - temp.length, temp.length);
            }
        }
        if (output.length == 0) {
            return null;
        }
        return output;
    }
    
    @Override
    public byte[] process(byte b) {
        buffer[index++] = b;
        if (index == OUTPUT_BYTES) {
            index = 0;
            return Base64.getDecoder().decode(buffer);
        }
        return null;
    }
    
    @Override
    public byte[] doFinal() {
        if (index == 0) {
            return null;
        }
        final byte[] temp = Base64.getDecoder().decode(Arrays.copyOf(buffer, index));
        index = 0;
        return temp;
    }
    
}
