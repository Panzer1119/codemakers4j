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

package de.codemakers.base.util;

import de.codemakers.base.exceptions.NotImplementedRuntimeException;
import de.codemakers.base.util.interfaces.Hasher;
import net.jpountz.xxhash.XXHash32;
import net.jpountz.xxhash.XXHash64;
import net.jpountz.xxhash.XXHashFactory;
import net.openhft.hashing.LongHashFunction;

public class HashUtil {
    
    public static final Hasher HASHER_64_XX = createHasher64XX();
    public static final Hasher HASHER_64_XXHASH_64_SAFE = createHasher64XXHash64Safe();
    public static final Hasher HASHER_64_XXHASH_64_FASTEST = createHasher64XXHash64Fastest();
    public static final Hasher HASHER_64_XXHASH_64_FASTEST_JAVA = createHasher64XXHash64FastestJava();
    public static final Hasher HASHER_32_XXHASH_32_SAFE = createHasher32XXHash32Safe();
    public static final Hasher HASHER_32_XXHASH_32_FASTEST = createHasher32XXHash32Fastest();
    public static final Hasher HASHER_32_XXHASH_32_FASTEST_JAVA = createHasher32XXHash32FastestJava();
    
    public static Hasher createHasher64XX() {
        return fromLongHashFunction(LongHashFunction.xx());
    }
    
    public static Hasher createHasher64XX(long seed) {
        return fromLongHashFunction(LongHashFunction.xx(seed));
    }
    
    public static Hasher fromLongHashFunction(final LongHashFunction longHashFunction) {
        return new Hasher() {
            @Override
            public byte[] hash(byte[] data, int offset, int length) throws Exception {
                return ConvertUtil.longToByteArray(longHashFunction.hashBytes(data, offset, length));
            }
            
            @Override
            public byte[] hash(byte[] data) throws Exception {
                return ConvertUtil.longToByteArray(longHashFunction.hashBytes(data));
            }
            
            @Override
            public byte[] hash() throws Exception {
                return ConvertUtil.longToByteArray(longHashFunction.hashVoid());
            }
            
            @Override
            public void update(byte[] data, int offset, int length) throws Exception {
                throw new NotImplementedRuntimeException();
            }
    
            @Override
            public int getHashLength() {
                return 8;
            }
        };
    }
    
    public static Hasher createHasher64XXHash64Safe() {
        return createHasher64XXHash64Safe(0);
    }
    
    public static Hasher createHasher64XXHash64Safe(int seed) {
        return fromXXHash64(XXHashFactory.safeInstance().hash64(), seed);
    }
    
    public static Hasher createHasher64XXHash64Fastest() {
        return createHasher64XXHash64Fastest(0);
    }
    
    public static Hasher createHasher64XXHash64Fastest(int seed) {
        return fromXXHash64(XXHashFactory.fastestInstance().hash64(), seed);
    }
    
    public static Hasher createHasher64XXHash64FastestJava() {
        return createHasher64XXHash64FastestJava(0);
    }
    
    public static Hasher createHasher64XXHash64FastestJava(int seed) {
        return fromXXHash64(XXHashFactory.fastestJavaInstance().hash64(), seed);
    }
    
    public static Hasher fromXXHash64(final XXHash64 xxHash64, final long seed) {
        return new Hasher() {
            @Override
            public byte[] hash(byte[] data, int offset, int length) throws Exception {
                return ConvertUtil.longToByteArray(xxHash64.hash(data, offset, length, seed));
            }
            
            @Override
            public byte[] hash() throws Exception {
                throw new NotImplementedRuntimeException();
            }
            
            @Override
            public void update(byte[] data, int offset, int length) throws Exception {
                throw new NotImplementedRuntimeException();
            }
    
            @Override
            public int getHashLength() {
                return 8;
            }
        };
    }
    
    public static Hasher createHasher32XXHash32Safe() {
        return createHasher32XXHash32Safe(0);
    }
    
    public static Hasher createHasher32XXHash32Safe(int seed) {
        return fromXXHash32(XXHashFactory.safeInstance().hash32(), seed);
    }
    
    public static Hasher createHasher32XXHash32Fastest() {
        return createHasher32XXHash32Fastest(0);
    }
    
    public static Hasher createHasher32XXHash32Fastest(int seed) {
        return fromXXHash32(XXHashFactory.fastestInstance().hash32(), seed);
    }
    
    public static Hasher createHasher32XXHash32FastestJava() {
        return createHasher32XXHash32FastestJava(0);
    }
    
    public static Hasher createHasher32XXHash32FastestJava(int seed) {
        return fromXXHash32(XXHashFactory.fastestJavaInstance().hash32(), seed);
    }
    
    public static Hasher fromXXHash32(final XXHash32 xxHash32, final int seed) {
        return new Hasher() {
            @Override
            public byte[] hash(byte[] data, int offset, int length) throws Exception {
                return ConvertUtil.intToByteArray(xxHash32.hash(data, offset, length, seed));
            }
            
            @Override
            public byte[] hash() throws Exception {
                throw new NotImplementedRuntimeException();
            }
            
            @Override
            public void update(byte[] data, int offset, int length) throws Exception {
                throw new NotImplementedRuntimeException();
            }
    
            @Override
            public int getHashLength() {
                return 4;
            }
        };
    }
    
}
