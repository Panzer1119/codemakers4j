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

package de.codemakers.base.entities.data;

import de.codemakers.base.util.Require;
import de.codemakers.base.util.interfaces.Copyable;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;

public class DirectDeltaData extends HashedDeltaData {
    
    protected byte[] indices;
    
    public DirectDeltaData(byte[] data_old, byte[] data_new) {
        this(data_old, data_new, Long.MIN_VALUE);
    }
    
    public DirectDeltaData(byte[] data_old, byte[] data_new, long version) {
        super(version);
        this.length = data_new == null ? -1 : data_new.length;
        setData(data_old, data_new);
    }
    
    private DirectDeltaData(int length, byte[] data_new, byte[] indices) {
        this(length, data_new, indices, Long.MIN_VALUE);
    }
    
    private DirectDeltaData(int length, byte[] data_new, byte[] indices, long version) {
        super(length, data_new);
        this.indices = indices;
    }
    
    @Override
    public DirectDeltaData setVersion(long version) {
        super.setVersion(version);
        return this;
    }
    
    @Override
    public byte[] getData() {
        if (data_new == null) {
            return null;
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public DirectDeltaData setData(byte[] data_new) {
        if (data_new == null) {
            this.data_new = null;
            this.indices = null;
            return this;
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public byte[] getData(byte[] data_old) {
        if (data_old == null || data_new == null) {
            return this.data_new;
        }
        final byte[] bytes = new byte[this.length];
        int index = 0;
        for (int i = 0; i < bytes.length; i++) {
            if ((this.indices[i / 8] & (1 << (i % 8))) != 0) {
                bytes[i] = data_new[index];
                index++;
            } else if (i < data_old.length) {
                bytes[i] = data_old[i];
            }
        }
        return bytes;
    }
    
    @Override
    public DirectDeltaData setData(byte[] data_old, byte[] data_new) {
        if (data_old == null || data_new == null) {
            this.data_new = data_new;
            this.indices = null;
            return this;
        }
        this.data_new = new byte[this.length];
        this.indices = new byte[(this.length + 7) / 8];
        int index = 0;
        for (int i = 0; i < data_new.length; i++) {
            if ((i >= data_old.length) || (data_new[i] != data_old[i])) {
                this.data_new[index] = data_new[i];
                this.indices[i / 8] |= (1 << (i % 8));
                index++;
            }
        }
        this.data_new = Arrays.copyOf(this.data_new, index);
        return this;
    }
    
    public byte[] getIndices() {
        return indices;
    }
    
    public DirectDeltaData setIndices(byte[] indices) {
        this.indices = indices;
        return this;
    }
    
    @Override
    public DirectDeltaData copy() {
        return new DirectDeltaData(length, data_new, indices);
    }
    
    @Override
    public void set(Copyable copyable) {
        final DirectDeltaData dataDelta = Require.clazz(copyable, DirectDeltaData.class);
        if (dataDelta != null) {
            setLength(dataDelta.length);
            setDataNew(dataDelta.data_new);
            setIndices(dataDelta.indices);
        }
    }
    
    @Override
    public Optional<byte[]> toBytes() {
        final ByteBuffer byteBuffer = ByteBuffer.allocate((Long.SIZE + Integer.SIZE) / Byte.SIZE + 1 + (data_new == null ? 0 : data_new.length) + 1 + (hash == null ? 0 : hash.length) + 1 + (indices == null ? 0 : indices.length));
        byteBuffer.putLong(version);
        byteBuffer.putInt(length);
        byteBuffer.putInt(arrayLength(data_new));
        if (data_new != null) {
            byteBuffer.put(data_new);
        }
        byteBuffer.putInt(arrayLength(hash));
        if (hash != null) {
            byteBuffer.put(hash);
        }
        byteBuffer.putInt(arrayLength(indices));
        if (indices != null) {
            byteBuffer.put(indices);
        }
        return Optional.of(byteBuffer.array());
    }
    
    @Override
    public boolean fromBytes(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        this.version = byteBuffer.getLong();
        this.length = byteBuffer.getInt();
        int temp = byteBuffer.getInt();
        if (temp >= 0) {
            this.data_new = new byte[temp];
            byteBuffer.get(this.data_new);
        } else {
            this.data_new = null;
        }
        temp = byteBuffer.getInt();
        if (temp >= 0) {
            this.hash = new byte[temp];
            byteBuffer.get(this.hash);
        } else {
            this.hash = null;
        }
        temp = byteBuffer.getInt();
        if (temp >= 0) {
            this.indices = new byte[temp];
            byteBuffer.get(this.indices);
        } else {
            this.indices = null;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "indices=" + Arrays.toString(indices) + ", hash=" + Arrays.toString(hash) + ", version=" + version + ", length=" + length + ", data_new=" + Arrays.toString(data_new) + '}';
    }
    
    @Override
    public long getBitSize() {
        return super.getBitSize() + (indices == null ? 0 : Byte.SIZE * indices.length);
    }
    
}
