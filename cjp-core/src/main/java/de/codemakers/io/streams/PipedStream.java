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

import de.codemakers.base.util.interfaces.Closeable;
import de.codemakers.base.util.interfaces.Resettable;
import de.codemakers.base.util.tough.ToughFunction;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedStream implements Closeable, Resettable {
    
    protected InputStream inputStream = null;
    protected OutputStream outputStream = null;
    
    public PipedStream() {
        resetWithoutException();
    }
    
    public <R extends InputStream> R convertInputStream(ToughFunction<InputStream, R> toughFunction) {
        final R r = toughFunction.applyWithoutException(inputStream);
        if (r == null) {
            throw new NullPointerException();
        }
        inputStream = r;
        return r;
    }
    
    public <R extends OutputStream> R convertOutputStream(ToughFunction<OutputStream, R> toughFunction) {
        final R r = toughFunction.applyWithoutException(outputStream);
        if (r == null) {
            throw new NullPointerException();
        }
        outputStream = r;
        return r;
    }
    
    public <T extends InputStream> T getInputStream(Class<T> clazz) {
        return (T) inputStream;
    }
    
    public InputStream getInputStream() {
        return inputStream;
    }
    
    public <T extends OutputStream> T getOutputStream(Class<T> clazz) {
        return (T) outputStream;
    }
    
    public OutputStream getOutputStream() {
        return outputStream;
    }
    
    @Override
    public void closeIntern() throws Exception {
        if (inputStream != null) {
            inputStream.close();
        }
        if (outputStream != null) {
            outputStream.close();
        }
    }
    
    @Override
    public boolean reset() throws Exception {
        inputStream = new PipedInputStream();
        outputStream = new PipedOutputStream((PipedInputStream) inputStream);
        return true;
    }
    
    @Override
    public String toString() {
        return "PipedStream{" + "inputStream=" + inputStream + ", outputStream=" + outputStream + '}';
    }
    
}
