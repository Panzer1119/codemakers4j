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
import de.codemakers.base.util.interfaces.ByteSerializable;
import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.base.util.interfaces.Version;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;

public abstract class DeltaData implements ByteSerializable, Copyable, Version {
    
    protected long version;
    protected int length;
    protected byte[] data_new;
    
    public DeltaData() {
        this(Long.MIN_VALUE);
    }
    
    public DeltaData(long version) {
        this(null, version);
    }
    
    public DeltaData(byte[] data_new) {
        this(data_new, Long.MIN_VALUE);
    }
    
    public DeltaData(byte[] data_new, long version) {
        this(data_new == null ? -1 : data_new.length, data_new, version);
    }
    
    public DeltaData(int length, byte[] data_new) {
        this(length, data_new, Long.MIN_VALUE);
    }
    
    public DeltaData(int length, byte[] data_new, long version) {
        this.length = length;
        this.version = version;
        setData(data_new);
    }
    
    public DeltaData(byte[] data_old, byte[] data_new) {
        this(data_old, data_new, Long.MIN_VALUE);
    }
    
    public DeltaData(byte[] data_old, byte[] data_new, long version) {
        this.length = data_new == null ? -1 : data_new.length;
        this.version = version;
        setData(data_old, data_new);
    }
    
    public abstract byte[] getData();
    
    public abstract DeltaData setData(byte[] data_new);
    
    public abstract byte[] getData(byte[] data_old);
    
    public abstract DeltaData setData(byte[] data_old, byte[] data_new);
    
    @Override
    public long getVersion() {
        return version;
    }
    
    public DeltaData setVersion(long version) {
        this.version = version;
        return this;
    }
    
    public int getLength() {
        return length;
    }
    
    public DeltaData setLength(int length) {
        this.length = length;
        return this;
    }
    
    public byte[] getDataNew() {
        return data_new;
    }
    
    public DeltaData setDataNew(byte[] data_new) {
        this.data_new = data_new;
        return this;
    }
    
    @Override
    public void set(Copyable copyable) {
        final DeltaData deltaData = Require.clazz(copyable, DeltaData.class);
        if (deltaData != null) {
            setVersion(deltaData.version);
            setLength(deltaData.length);
            setDataNew(deltaData.data_new);
        }
    }
    
    @Override
    public Optional<byte[]> toBytes() {
        final ByteBuffer byteBuffer = ByteBuffer.allocate((Long.SIZE + Integer.SIZE) / Byte.SIZE + 1 + (data_new == null ? 0 : data_new.length));
        byteBuffer.putLong(version);
        byteBuffer.putInt(length);
        byteBuffer.putInt(arrayLength(data_new));
        if (data_new != null) {
            byteBuffer.put(data_new);
        }
        return Optional.of(byteBuffer.array());
    }
    
    @Override
    public boolean fromBytes(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        this.version = byteBuffer.getLong();
        this.length = byteBuffer.getInt();
        final int temp = byteBuffer.getInt();
        if (temp >= 0) {
            this.data_new = new byte[temp];
            byteBuffer.get(this.data_new);
        } else {
            this.data_new = null;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "length=" + length + ", data_new=" + Arrays.toString(data_new) + '}';
    }
    
    public long getBitSize() {
        return Long.SIZE + Integer.SIZE + (data_new == null ? 0 : Byte.SIZE * data_new.length);
    }
    
}
