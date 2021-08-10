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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DynamicMultiOutputStream<O extends OutputStream> extends OutputStream {
    
    private final List<O> outputStreams = new CopyOnWriteArrayList<>();
    //private final AtomicBoolean continueOnError = new AtomicBoolean(false);
    
    public DynamicMultiOutputStream(Collection<O> outputStreams) {
        this.outputStreams.addAll(outputStreams);
    }
    
    public DynamicMultiOutputStream(O... outputStreams) {
        this.outputStreams.addAll(Arrays.asList(outputStreams));
    }
    
    public List<O> getOutputStreams() {
        return outputStreams;
    }
    
    public DynamicMultiOutputStream removeAllOutputStreams() {
        outputStreams.clear();
        return this;
    }
    
    public boolean addOutputStream(O outputStream) {
        return outputStreams.add(outputStream);
    }
    
    public boolean addOutputStreams(Collection<O> outputStreams) {
        return outputStreams.addAll(outputStreams);
    }
    
    public boolean addOutputStreams(O... outputStreams) {
        return addOutputStreams(Arrays.asList(outputStreams));
    }
    
    public boolean removeOutputStream(O outputStream) {
        return outputStreams.remove(outputStream);
    }
    
    public boolean removeOutputStreams(Collection<O> outputStreams) {
        return outputStreams.removeAll(outputStreams);
    }
    
    public boolean removeOutputStreams(O... outputStreams) {
        return removeOutputStreams(Arrays.asList(outputStreams));
    }
    
    /*
    public boolean isContinueOnError() {
        return continueOnError.get();
    }
    
    public DynamicMultiOutputStream setContinueOnError(boolean continueOnError) {
        this.continueOnError.set(continueOnError);
        return this;
    }
    */
    
    @Override
    public void write(int b) throws IOException {
        for (O outputStream : outputStreams) {
            outputStream.write(b);
        }
    }
    
    @Override
    public void flush() throws IOException {
        for (O outputStream : outputStreams) {
            outputStream.flush();
        }
    }
    
    @Override
    public void close() throws IOException {
        for (O outputStream : outputStreams) {
            outputStream.close();
        }
    }
    
}
