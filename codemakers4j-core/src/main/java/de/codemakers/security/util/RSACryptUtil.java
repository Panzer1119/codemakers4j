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

import javax.crypto.KeyGenerator;
import java.security.SecureRandom;

public class RSACryptUtil {
    
    public static final String ALGORITHM_RSA = "RSA";
    public static final String ALGORITHM_SHA256withRSA = SecureHashUtil.ALGORITHM_SHA256withRSA;
    
    public static final int KEYSIZE_RSA_0 = 0;
    public static final int KEYSIZE_RSA_128 = 128;
    public static final int KEYSIZE_RSA_256 = 256;
    public static final int KEYSIZE_RSA_512 = 512;
    public static final int KEYSIZE_RSA_1024 = 1024;
    public static final int KEYSIZE_RSA_2048 = 2048;
    public static final int KEYSIZE_RSA_4096 = 4096;
    public static final int KEYSIZE_RSA_8192 = 8192;
    public static final int KEYSIZE_RSA_16384 = 16384;
    
    public static KeyGenerator createRSAKeyGenerator() {
        try {
            return KeyGenerator.getInstance(ALGORITHM_RSA);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static KeyGenerator createRSAKeyGenerator(int keySize) {
        final KeyGenerator keyGenerator = createRSAKeyGenerator();
        keyGenerator.init(keySize);
        return keyGenerator;
    }
    
    public static KeyGenerator createRSAKeyGenerator(int keySize, SecureRandom secureRandom) {
        final KeyGenerator keyGenerator = createRSAKeyGenerator();
        keyGenerator.init(keySize, secureRandom);
        return keyGenerator;
    }
    
    public static KeyGenerator createRSAKeyGenerator(SecureRandom secureRandom) {
        final KeyGenerator keyGenerator = createRSAKeyGenerator();
        keyGenerator.init(secureRandom);
        return keyGenerator;
    }
    
    public static KeyGenerator create1024BitRSAKeyGenerator() {
        return createRSAKeyGenerator(KEYSIZE_RSA_1024);
    }
    
    public static KeyGenerator create1024BitRSAKeyGenerator(SecureRandom secureRandom) {
        return createRSAKeyGenerator(KEYSIZE_RSA_1024, secureRandom);
    }
    
    public static KeyGenerator create2048BitRSAKeyGenerator() {
        return createRSAKeyGenerator(KEYSIZE_RSA_2048);
    }
    
    public static KeyGenerator create2048BitRSAKeyGenerator(SecureRandom secureRandom) {
        return createRSAKeyGenerator(KEYSIZE_RSA_2048, secureRandom);
    }
    
    public static KeyGenerator create4096BitRSAKeyGenerator() {
        return createRSAKeyGenerator(KEYSIZE_RSA_4096);
    }
    
    public static KeyGenerator create4096BitRSAKeyGenerator(SecureRandom secureRandom) {
        return createRSAKeyGenerator(KEYSIZE_RSA_4096, secureRandom);
    }
    
    public static KeyGenerator create8192BitRSAKeyGenerator() {
        return createRSAKeyGenerator(KEYSIZE_RSA_8192);
    }
    
    public static KeyGenerator create8192BitRSAKeyGenerator(SecureRandom secureRandom) {
        return createRSAKeyGenerator(KEYSIZE_RSA_8192, secureRandom);
    }
    
    public static KeyGenerator create16384BitRSAKeyGenerator() {
        return createRSAKeyGenerator(KEYSIZE_RSA_16384);
    }
    
    public static KeyGenerator create16384BitRSAKeyGenerator(SecureRandom secureRandom) {
        return createRSAKeyGenerator(KEYSIZE_RSA_16384, secureRandom);
    }
    
}
