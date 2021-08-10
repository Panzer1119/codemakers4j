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

import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.util.Require;
import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.base.util.interfaces.Version;
import de.codemakers.security.util.SecureHashUtil;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class IncrementalData extends Data implements Version {
    
    protected final AtomicLong version = new AtomicLong(Long.MIN_VALUE);
    
    public IncrementalData(byte[] data) {
        super(data);
    }
    
    public DeltaData changeData(byte[] data_new) {
        final byte[] data_old = getData();
        setData(data_new);
        return new XORDeltaData(data_old, data_new, version.incrementAndGet()).generateHash32BytesSHA256(data_new);
    }
    
    public ReturningAction<DeltaData> changeDataAction(byte[] data_new) {
        return new ReturningAction<>(() -> changeData(data_new));
    }
    
    public IncrementalData incrementData(DeltaData deltaData) {
        return incrementData(deltaData, false);
    }
    
    public IncrementalData incrementData(DeltaData deltaData, boolean forceIncrement) {
        Objects.requireNonNull(deltaData);
        if (!forceIncrement) {
            if (deltaData.getVersion() == getVersion()) {
                throw new IllegalArgumentException(deltaData.getClass().getName() + "'s version is the same as this version (\"" + getVersion() + "\")");
            } else if (Math.abs(deltaData.getVersion() - getVersion()) != 1) {
                throw new IllegalArgumentException(deltaData.getClass().getName() + "'s version \"" + deltaData.getVersion() + "\" is not 1 offset from this version \"" + getVersion() + "\" (offset is " + Math.abs(deltaData.getVersion() - getVersion()) + ")");
            }
        }
        byte[] data_new;
        if (deltaData.getLength() < 0 || getLength() < 0) {
            data_new = deltaData.getDataNew();
        } else {
            data_new = deltaData.getData(getData());
        }
        if (!forceIncrement && (deltaData instanceof HashedDeltaData)) {
            final HashedDeltaData hashedDeltaData = (HashedDeltaData) deltaData;
            if (hashedDeltaData.getHashLength() == HashedDeltaData.HASH_LENGTH_SHA_256) {
                if (!SecureHashUtil.isDataValidSHA_256(data_new, hashedDeltaData.getHash())) { //FIXME Hardcoded hash length
                    throw new IllegalArgumentException("The hash of the new data is not equal to the given hash");
                }
            } else if (hashedDeltaData.getHashLength() == HashedDeltaData.HASH_LENGTH_SHA_512) {
                if (!SecureHashUtil.isDataValidSHA_512(data_new, hashedDeltaData.getHash())) { //FIXME Hardcoded hash length
                    throw new IllegalArgumentException("The hash of the new data is not equal to the given hash");
                }
            }
        }
        setData(data_new);
        version.set(deltaData.getVersion());
        return this;
    }
    
    public int getLength() {
        return data == null ? -1 : data.length;
    }
    
    @Override
    public IncrementalData setData(byte[] data) {
        super.setData(data);
        return this;
    }
    
    @Override
    public IncrementalData copy() {
        return new IncrementalData(getData());
    }
    
    @Override
    public void set(Copyable copyable) {
        final IncrementalData incrementalData = Require.clazz(copyable, IncrementalData.class);
        if (incrementalData != null) {
            version.set(incrementalData.version.get());
            setData(incrementalData.getData());
        }
    }
    
    @Override
    public Optional<byte[]> toBytes() {
        final ByteBuffer byteBuffer = ByteBuffer.allocate((Long.SIZE + Integer.SIZE) / Byte.SIZE + (data == null ? 0 : data.length));
        byteBuffer.putLong(version.get());
        byteBuffer.putInt(arrayLength(data));
        if (data != null) {
            byteBuffer.put(data);
        }
        return Optional.of(byteBuffer.array());
    }
    
    @Override
    public boolean fromBytes(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        this.version.set(byteBuffer.getLong());
        final int temp = byteBuffer.getInt();
        if (temp >= 0) {
            this.data = new byte[temp];
            byteBuffer.get(this.data);
        } else {
            this.data = null;
        }
        return true;
    }
    
    @Override
    public long getVersion() {
        return version.get();
    }
    
    @Override
    public String toString() {
        return "IncrementalData{" + "version=" + version + ", data=" + Arrays.toString(data) + '}';
    }
    
}
