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

package de.codemakers.security.util;

import de.codemakers.base.util.interfaces.Hasher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.util.Arrays;

public class SecureHashUtil {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final String ALGORITHM_SHA1withRSA = "SHA1withRSA";
    public static final String ALGORITHM_SHA256withRSA = "SHA256withRSA";
    public static final String ALGORITHM_SHA384withRSA = "SHA384withRSA";
    public static final String ALGORITHM_SHA512withRSA = "SHA512withRSA";
    public static final String ALGORITHM_SHA256withECDSA = "SHA256withECDSA";
    public static final String ALGORITHM_SHA_1 = "SHA-1";
    public static final String ALGORITHM_SHA_256 = "SHA-256";
    public static final String ALGORITHM_SHA_384 = "SHA-384";
    public static final String ALGORITHM_SHA_512 = "SHA-512";
    
    private static final MessageDigest SHA_1 = createSHA_1();
    private static final MessageDigest SHA_256 = createSHA_256();
    private static final MessageDigest SHA_384 = createSHA_384();
    private static final MessageDigest SHA_512 = createSHA_512();
    
    public static MessageDigest createSHA_1() {
        try {
            return MessageDigest.getInstance(ALGORITHM_SHA_1);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static MessageDigest createSHA_256() {
        try {
            return MessageDigest.getInstance(ALGORITHM_SHA_256);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static MessageDigest createSHA_384() {
        try {
            return MessageDigest.getInstance(ALGORITHM_SHA_384);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static MessageDigest createSHA_512() {
        try {
            return MessageDigest.getInstance(ALGORITHM_SHA_512);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static Hasher createHasher20SHA_1() {
        return fromMessageDigest(createSHA_1());
    }
    
    public static Hasher createHasher32SHA_256() {
        return fromMessageDigest(createSHA_256());
    }
    
    public static Hasher createHasher48SHA_384() {
        return fromMessageDigest(createSHA_384());
    }
    
    public static Hasher createHasher64SHA_512() {
        return fromMessageDigest(createSHA_512());
    }
    
    public static Hasher fromMessageDigest(final MessageDigest messageDigest) {
        return new Hasher() {
            @Override
            public byte[] hash(byte[] data, int offset, int length) throws Exception {
                messageDigest.update(data, offset, length);
                return messageDigest.digest();
            }
            
            @Override
            public byte[] hash(byte[] data) throws Exception {
                return messageDigest.digest(data);
            }
            
            @Override
            public byte[] hash() throws Exception {
                return messageDigest.digest();
            }
            
            @Override
            public void update(byte[] data, int offset, int length) throws Exception {
                messageDigest.update(data, offset, length);
            }
    
            @Override
            public int getHashLength() {
                return messageDigest.getDigestLength();
            }
        };
    }
    
    public static byte[] hashSHA_1(byte[] data) {
        if (data == null) {
            return null;
        }
        SHA_1.reset();
        return SHA_1.digest(data);
    }
    
    public static byte[] hashSHA_256(byte[] data) {
        if (data == null) {
            return null;
        }
        SHA_256.reset();
        return SHA_256.digest(data);
    }
    
    public static byte[] hashSHA_384(byte[] data) {
        if (data == null) {
            return null;
        }
        SHA_384.reset();
        return SHA_384.digest(data);
    }
    
    public static byte[] hashSHA_512(byte[] data) {
        if (data == null) {
            return null;
        }
        SHA_512.reset();
        return SHA_512.digest(data);
    }
    
    public static boolean isDataValidSHA_1(byte[] data, byte[] hash) {
        if (hash == null || data == null) {
            return hash == data;
        }
        return Arrays.equals(hashSHA_1(data), hash);
    }
    
    public static boolean isDataValidSHA_256(byte[] data, byte[] hash) {
        if (hash == null || data == null) {
            return hash == data;
        }
        return Arrays.equals(hashSHA_256(data), hash);
    }
    
    public static boolean isDataValidSHA_384(byte[] data, byte[] hash) {
        if (hash == null || data == null) {
            return hash == data;
        }
        return Arrays.equals(hashSHA_384(data), hash);
    }
    
    public static boolean isDataValidSHA_512(byte[] data, byte[] hash) {
        if (hash == null || data == null) {
            return hash == data;
        }
        return Arrays.equals(hashSHA_512(data), hash);
    }
    
}
