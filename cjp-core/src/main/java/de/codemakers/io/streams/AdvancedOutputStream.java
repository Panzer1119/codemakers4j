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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterOutputStream;

public class AdvancedOutputStream<T extends OutputStream> implements AdvancedStream<AdvancedOutputStream> {
    
    private final T stream;
    
    public AdvancedOutputStream(T stream) {
        Objects.requireNonNull(stream);
        this.stream = stream;
    }
    
    public final T getStream() {
        return stream;
    }
    
    @Override
    public AdvancedOutputStream<BufferedOutputStream> asBuffered() {
        return new AdvancedOutputStream<>(new BufferedOutputStream(stream));
    }
    
    @Override
    public AdvancedOutputStream<InflaterOutputStream> asInflated() {
        return new AdvancedOutputStream<>(new InflaterOutputStream(stream));
    }
    
    @Override
    public AdvancedOutputStream<DeflaterOutputStream> asDeflated() {
        return new AdvancedOutputStream<>(new DeflaterOutputStream(stream));
    }
    
    @Override
    public AdvancedOutputStream<GZIPOutputStream> asGZIP() throws IOException {
        return new AdvancedOutputStream<>(new GZIPOutputStream(stream));
    }
    
    @Override
    public byte[] toByteArray() {
        return null;
    }
    
    @Override
    public void close() throws IOException {
        stream.close();
    }
    
}
