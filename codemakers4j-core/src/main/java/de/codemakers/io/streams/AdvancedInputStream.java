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

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class AdvancedInputStream<T extends InputStream> implements AdvancedStream<AdvancedInputStream> {
    
    private final T stream;
    
    public AdvancedInputStream(T stream) {
        Objects.requireNonNull(stream);
        this.stream = stream;
    }
    
    public final T getStream() {
        return stream;
    }
    
    @Override
    public AdvancedInputStream<BufferedInputStream> asBuffered() {
        return new AdvancedInputStream<>(new BufferedInputStream(stream));
    }
    
    @Override
    public AdvancedInputStream<InflaterInputStream> asInflated() {
        return new AdvancedInputStream<>(new InflaterInputStream(stream));
    }
    
    @Override
    public AdvancedInputStream<DeflaterInputStream> asDeflated() {
        return new AdvancedInputStream<>(new DeflaterInputStream(stream));
    }
    
    @Override
    public AdvancedInputStream<GZIPInputStream> asGZIP() throws IOException {
        return new AdvancedInputStream<>(new GZIPInputStream(stream));
    }
    
    @Override
    public byte[] toByteArray() throws IOException {
        return IOUtils.toByteArray(stream);
    }
    
    @Override
    public void close() throws IOException {
        stream.close();
    }
    
}
