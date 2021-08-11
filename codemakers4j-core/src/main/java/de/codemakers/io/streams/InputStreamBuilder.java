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

import de.codemakers.base.exceptions.RethrownRuntimeException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Objects;
import java.util.zip.*;

public class InputStreamBuilder {
    
    protected InputStream inputStream = null;
    
    public InputStreamBuilder(InputStream inputStream) {
        setInputStream(inputStream);
    }
    
    public InputStreamBuilder setInputStream(InputStream inputStream) {
        this.inputStream = Objects.requireNonNull(inputStream, "inputStream");
        return this;
    }
    
    public <T extends InputStream> T getInputStream() {
        return (T) inputStream;
    }
    
    public <T extends InputStream> T getInputStream(Class<T> clazz) {
        return (T) inputStream;
    }
    
    public InputStreamBuilder toBufferedInputStream() {
        return setInputStream(new BufferedInputStream(inputStream));
    }
    
    public InputStreamBuilder toBufferedInputStream(int bufferSize) {
        return setInputStream(new BufferedInputStream(inputStream, bufferSize));
    }
    
    public InputStreamBuilder toDataInputStream() {
        return setInputStream(new DataInputStream(inputStream));
    }
    
    public InputStreamBuilder toObjectInputStream() {
        try {
            return setInputStream(new ObjectInputStream(inputStream));
        } catch (Exception ex) {
            throw new RethrownRuntimeException(ex);
        }
    }
    
    public InputStreamBuilder toDeflaterInputStream() {
        return setInputStream(new DeflaterInputStream(inputStream));
    }
    
    public InputStreamBuilder toDeflaterInputStream(Deflater deflater) {
        return setInputStream(new DeflaterInputStream(inputStream, deflater));
    }
    
    public InputStreamBuilder toDeflaterInputStream(Deflater deflater, int bufferSize) {
        return setInputStream(new DeflaterInputStream(inputStream, deflater, bufferSize));
    }
    
    public InputStreamBuilder toInflaterInputStream() {
        return setInputStream(new InflaterInputStream(inputStream));
    }
    
    public InputStreamBuilder toInflaterInputStream(Inflater inflater) {
        return setInputStream(new InflaterInputStream(inputStream, inflater));
    }
    
    public InputStreamBuilder toInflaterInputStream(Inflater inflater, int bufferSize) {
        return setInputStream(new InflaterInputStream(inputStream, inflater, bufferSize));
    }
    
    public InputStreamBuilder toGZIPInputStream() {
        try {
            return setInputStream(new GZIPInputStream(inputStream));
        } catch (Exception ex) {
            throw new RethrownRuntimeException(ex);
        }
    }
    
    public InputStreamBuilder toGZIPInputStream(int bufferSize) {
        try {
            return setInputStream(new GZIPInputStream(inputStream, bufferSize));
        } catch (Exception ex) {
            throw new RethrownRuntimeException(ex);
        }
    }
    
    public InputStreamBuilder toCipherInputStream(Cipher cipher) {
        return setInputStream(new CipherInputStream(inputStream, cipher));
    }
    
    @Override
    public String toString() {
        return "InputStreamBuilder{" + "inputStream=" + inputStream + '}';
    }
    
}
