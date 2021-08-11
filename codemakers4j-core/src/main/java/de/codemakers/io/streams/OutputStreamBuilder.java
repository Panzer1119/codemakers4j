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
import javax.crypto.CipherOutputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.zip.*;

public class OutputStreamBuilder {
    
    protected OutputStream outputStream = null;
    
    public OutputStreamBuilder(OutputStream outputStream) {
        setOutputStream(outputStream);
    }
    
    public OutputStreamBuilder setOutputStream(OutputStream outputStream) {
        this.outputStream = Objects.requireNonNull(outputStream, "outputStream");
        return this;
    }
    
    public <T extends OutputStream> T getOutputStream() {
        return (T) outputStream;
    }
    
    public <T extends OutputStream> T getOutputStream(Class<T> clazz) {
        return (T) outputStream;
    }
    
    public OutputStreamBuilder toBufferedOutputStream() {
        return setOutputStream(new BufferedOutputStream(outputStream));
    }
    
    public OutputStreamBuilder toBufferedOutputStream(int bufferSize) {
        return setOutputStream(new BufferedOutputStream(outputStream, bufferSize));
    }
    
    public OutputStreamBuilder toDataOutputStream() {
        return setOutputStream(new DataOutputStream(outputStream));
    }
    
    public OutputStreamBuilder toObjectOutputStream() {
        try {
            return setOutputStream(new ObjectOutputStream(outputStream));
        } catch (Exception ex) {
            throw new RethrownRuntimeException(ex);
        }
    }
    
    public OutputStreamBuilder toDeflaterOutputStream() {
        return setOutputStream(new DeflaterOutputStream(outputStream));
    }
    
    public OutputStreamBuilder toDeflaterOutputStream(Deflater deflater) {
        return setOutputStream(new DeflaterOutputStream(outputStream, deflater));
    }
    
    public OutputStreamBuilder toDeflaterOutputStream(Deflater deflater, int bufferSize) {
        return setOutputStream(new DeflaterOutputStream(outputStream, deflater, bufferSize));
    }
    
    public OutputStreamBuilder toInflaterOutputStream() {
        return setOutputStream(new InflaterOutputStream(outputStream));
    }
    
    public OutputStreamBuilder toInflaterOutputStream(Inflater inflater) {
        return setOutputStream(new InflaterOutputStream(outputStream, inflater));
    }
    
    public OutputStreamBuilder toInflaterOutputStream(Inflater inflater, int bufferSize) {
        return setOutputStream(new InflaterOutputStream(outputStream, inflater, bufferSize));
    }
    
    public OutputStreamBuilder toGZIPInputStream() {
        try {
            return setOutputStream(new GZIPOutputStream(outputStream));
        } catch (Exception ex) {
            throw new RethrownRuntimeException(ex);
        }
    }
    
    public OutputStreamBuilder toGZIPInputStream(int bufferSize) {
        try {
            return setOutputStream(new GZIPOutputStream(outputStream, bufferSize));
        } catch (Exception ex) {
            throw new RethrownRuntimeException(ex);
        }
    }
    
    public OutputStreamBuilder toCipherOutputStream(Cipher cipher) {
        return setOutputStream(new CipherOutputStream(outputStream, cipher));
    }
    
    @Override
    public String toString() {
        return "OutputStreamBuilder{" + "outputStream=" + outputStream + '}';
    }
    
}
