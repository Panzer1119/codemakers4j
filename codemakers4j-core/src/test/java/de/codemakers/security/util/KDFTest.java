/*
 *     Copyright 2018 - 2020 Paul Hagedorn (Panzer1119)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package de.codemakers.security.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Arrays;

public class KDFTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        final SecretKeyFactory secretKeyFactory_1 = KDFUtil.createPBKDF2WithHmacSHA_1();
        logger.info("secretKeyFactory_1=" + secretKeyFactory_1);
        final SecretKeyFactory secretKeyFactory_256 = KDFUtil.createPBKDF2WithHmacSHA_256();
        logger.info("secretKeyFactory_256=" + secretKeyFactory_256);
        final SecretKeyFactory secretKeyFactory_384 = KDFUtil.createPBKDF2WithHmacSHA_384();
        logger.info("secretKeyFactory_384=" + secretKeyFactory_384);
        final SecretKeyFactory secretKeyFactory_512 = KDFUtil.createPBKDF2WithHmacSHA_512();
        logger.info("secretKeyFactory_512=" + secretKeyFactory_512);
        final String password = "Test1234";
        logger.info("password=" + password);
        final byte[] salt = EasyCryptUtil.generateSecureRandomBytes(32);
        logger.info("salt=" + Arrays.toString(salt));
        final int iterations = 100000;
        logger.info("iterations=" + iterations);
        final int length = 512;
        logger.info("length=" + length);
        final PBEKeySpec keySpec_512 = new PBEKeySpec(password.toCharArray(), salt, iterations, length);
        logger.info("keySpec_512=" + keySpec_512);
        final long start = System.currentTimeMillis();
        final SecretKey secretKey_512 = secretKeyFactory_512.generateSecret(keySpec_512);
        final long stop = System.currentTimeMillis();
        keySpec_512.clearPassword();
        final long duration = stop - start;
        logger.info("Time taken: " + duration + " ms");
        logger.info("secretKey_512=" + secretKey_512);
        logger.info("secretKey_512=" + Arrays.toString(secretKey_512.getEncoded()));
        logger.info("secretKey_512=" + secretKey_512.getEncoded().length);
        logger.info("secretKey_512=" + secretKey_512.getEncoded().length * 8);
    }
    
}
