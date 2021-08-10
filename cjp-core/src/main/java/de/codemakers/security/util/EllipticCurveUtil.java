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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.KeyAgreement;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class EllipticCurveUtil {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final String ALGORITHM_EC = "EC";
    public static final String ALGORITHM_ECDH = "ECDH";
    public static final String ALGORITHM_SHA256withECDSA = SecureHashUtil.ALGORITHM_SHA256withECDSA;
    
    public static final int KEYSIZE_ECC_0 = 0;
    public static final int KEYSIZE_ECC_128 = 128;
    public static final int KEYSIZE_ECC_256 = 256;
    public static final int KEYSIZE_ECC_384 = 384;
    public static final int KEYSIZE_ECC_521 = 521;
    
    public static KeyPairGenerator createKeyPairGeneratorEC(SecureRandom secureRandom, int keySize) {
        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_EC);
            keyPairGenerator.initialize(keySize, secureRandom);
            return keyPairGenerator;
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static KeyAgreement createKeyAgreement() {
        try {
            return KeyAgreement.getInstance(ALGORITHM_ECDH);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
}
