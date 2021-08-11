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

public abstract class Processor {
    
    protected byte[] buffer = null;
    
    public byte[] process(byte[] bytes) {
        return process(bytes, 0, bytes.length);
    }
    
    public abstract byte[] process(byte[] bytes, int offset, int length);
    
    public abstract byte[] process(byte b);
    
    public abstract byte[] doFinal();
    
    public byte[] getBuffer() {
        return buffer;
    }
    
    public Processor setBuffer(byte[] buffer) {
        this.buffer = buffer;
        return this;
    }
    
    public Processor reset() {
        buffer = null;
        return this;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Processor processor = (Processor) o;
        return Arrays.equals(buffer, processor.buffer);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(buffer);
    }
    
    @Override
    public String toString() {
        return "Processor{" + "buffer=" + Arrays.toString(buffer) + '}';
    }
    
}
