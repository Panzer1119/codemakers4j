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
import de.codemakers.base.util.interfaces.Hasher;
import de.codemakers.security.util.SecureHashUtil;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Optional;

public abstract class HashedDeltaData extends DeltaData {
    
    public static final Hasher HASHER_32SHA_256 = SecureHashUtil.createHasher32SHA256();
    public static final Hasher HASHER_64SHA_512 = SecureHashUtil.createHasher64SHA512();
    public static final int HASH_LENGTH_SHA_256 = HASHER_32SHA_256.getHashLength();
    public static final int HASH_LENGTH_SHA_512 = HASHER_64SHA_512.getHashLength();
    
    protected int hashLength = HASH_LENGTH_SHA_256;
    protected byte[] hash = null;
    
    public HashedDeltaData() {
        super();
    }
    
    public HashedDeltaData(long version) {
        super(version);
    }
    
    public HashedDeltaData(byte[] data_new) {
        super(data_new);
    }
    
    public HashedDeltaData(byte[] data_new, long version) {
        super(data_new, version);
    }
    
    public HashedDeltaData(int length, byte[] data_new) {
        super(length, data_new);
    }
    
    public HashedDeltaData(int length, byte[] data_new, long version) {
        super(length, data_new, version);
    }
    
    public HashedDeltaData(byte[] data_old, byte[] data_new) {
        super(data_old, data_new);
    }
    
    public HashedDeltaData(byte[] data_old, byte[] data_new, long version) {
        super(data_old, data_new, version);
    }
    
    @Override
    public HashedDeltaData setVersion(long version) {
        super.setVersion(version);
        return this;
    }
    
    @Override
    public HashedDeltaData setLength(int length) {
        super.setLength(length);
        return this;
    }
    
    @Override
    public HashedDeltaData setDataNew(byte[] data_new) {
        super.setDataNew(data_new);
        return this;
    }
    
    public int getHashLength() {
        return hashLength;
    }
    
    public HashedDeltaData setHashLength(int hashLength) {
        this.hashLength = hashLength;
        return this;
    }
    
    public byte[] getHash() {
        return hash;
    }
    
    public HashedDeltaData setHash(byte[] hash) {
        if (hash.length != hashLength) {
            throw new IllegalArgumentException("hash.length does not match hashLength");
        }
        this.hash = hash;
        return this;
    }
    
    public HashedDeltaData generateHash32BytesSHA256(byte[] data_new) {
        return generateHash(HASHER_32SHA_256, data_new);
    }
    
    public HashedDeltaData generateHash64BytesSHA512(byte[] data_new) {
        return generateHash(HASHER_64SHA_512, data_new);
    }
    
    public HashedDeltaData generateHash(Hasher hasher, byte[] data_new) {
        setHashLength(hasher.getHashLength());
        setHash(hasher.hashWithoutException(data_new));
        return this;
    }
    
    @Override
    public void set(Copyable copyable) {
        final HashedDeltaData deltaData = Require.clazz(copyable, HashedDeltaData.class);
        if (deltaData != null) {
            setVersion(deltaData.version);
            setLength(deltaData.length);
            setDataNew(deltaData.data_new);
            setHash(deltaData.hash);
        }
    }
    
    @Override
    public Optional<byte[]> toBytes() {
        final ByteBuffer byteBuffer = ByteBuffer.allocate((Long.SIZE + Integer.SIZE) / Byte.SIZE + 1 + (data_new == null ? 0 : data_new.length) + 1 + (hash == null ? 0 : hash.length));
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
        return true;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "hash=" + Arrays.toString(hash) + ", version=" + version + ", length=" + length + ", data_new=" + Arrays.toString(data_new) + '}';
    }
    
    @Override
    public long getBitSize() {
        return super.getBitSize() + (hash == null ? 0 : Byte.SIZE * hash.length);
    }
    
}
