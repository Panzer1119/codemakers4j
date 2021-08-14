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
    public static final String ALGORITHM_SHA1 = "SHA-1";
    public static final String ALGORITHM_SHA256 = "SHA-256";
    public static final String ALGORITHM_SHA384 = "SHA-384";
    public static final String ALGORITHM_SHA512 = "SHA-512";
    
    private static final MessageDigest SHA1 = createSHA1();
    private static final MessageDigest SHA256 = createSHA256();
    private static final MessageDigest SHA384 = createSHA384();
    private static final MessageDigest SHA512 = createSHA512();
    
    public static MessageDigest createSHA1() {
        try {
            return MessageDigest.getInstance(ALGORITHM_SHA1);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static MessageDigest createSHA256() {
        try {
            return MessageDigest.getInstance(ALGORITHM_SHA256);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static MessageDigest createSHA384() {
        try {
            return MessageDigest.getInstance(ALGORITHM_SHA384);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static MessageDigest createSHA512() {
        try {
            return MessageDigest.getInstance(ALGORITHM_SHA512);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static Hasher createHasher20SHA1() {
        return fromMessageDigest(createSHA1());
    }
    
    public static Hasher createHasher32SHA256() {
        return fromMessageDigest(createSHA256());
    }
    
    public static Hasher createHasher48SHA384() {
        return fromMessageDigest(createSHA384());
    }
    
    public static Hasher createHasher64SHA512() {
        return fromMessageDigest(createSHA512());
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
    
    public static byte[] hashSHA1(byte[] data) {
        if (data == null) {
            return null;
        }
        SHA1.reset();
        return SHA1.digest(data);
    }
    
    public static byte[] hashSHA256(byte[] data) {
        if (data == null) {
            return null;
        }
        SHA256.reset();
        return SHA256.digest(data);
    }
    
    public static byte[] hashSHA384(byte[] data) {
        if (data == null) {
            return null;
        }
        SHA384.reset();
        return SHA384.digest(data);
    }
    
    public static byte[] hashSHA512(byte[] data) {
        if (data == null) {
            return null;
        }
        SHA512.reset();
        return SHA512.digest(data);
    }
    
    public static boolean isDataValidSHA1(byte[] data, byte[] hash) {
        if (hash == null || data == null) {
            return hash == data;
        }
        return Arrays.equals(hashSHA1(data), hash);
    }
    
    public static boolean isDataValidSHA256(byte[] data, byte[] hash) {
        if (hash == null || data == null) {
            return hash == data;
        }
        return Arrays.equals(hashSHA256(data), hash);
    }
    
    public static boolean isDataValidSHA384(byte[] data, byte[] hash) {
        if (hash == null || data == null) {
            return hash == data;
        }
        return Arrays.equals(hashSHA384(data), hash);
    }
    
    public static boolean isDataValidSHA512(byte[] data, byte[] hash) {
        if (hash == null || data == null) {
            return hash == data;
        }
        return Arrays.equals(hashSHA512(data), hash);
    }
    
}
