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

import javax.crypto.SecretKeyFactory;

public class KDFUtil {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final String ALGORITHM_PBKDF2WithHmacSHA_1 = "PBKDF2WithHmacSHA1";
    public static final String ALGORITHM_PBKDF2WithHmacSHA_256 = "PBKDF2WithHmacSHA256";
    public static final String ALGORITHM_PBKDF2WithHmacSHA_384 = "PBKDF2WithHmacSHA384";
    public static final String ALGORITHM_PBKDF2WithHmacSHA_512 = "PBKDF2WithHmacSHA512";
    
    public static SecretKeyFactory createPBKDF2WithHmacSHA_1() {
        try {
            return SecretKeyFactory.getInstance(ALGORITHM_PBKDF2WithHmacSHA_1);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static SecretKeyFactory createPBKDF2WithHmacSHA_256() {
        try {
            return SecretKeyFactory.getInstance(ALGORITHM_PBKDF2WithHmacSHA_256);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static SecretKeyFactory createPBKDF2WithHmacSHA_384() {
        try {
            return SecretKeyFactory.getInstance(ALGORITHM_PBKDF2WithHmacSHA_384);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    public static SecretKeyFactory createPBKDF2WithHmacSHA_512() {
        try {
            return SecretKeyFactory.getInstance(ALGORITHM_PBKDF2WithHmacSHA_512);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
}
