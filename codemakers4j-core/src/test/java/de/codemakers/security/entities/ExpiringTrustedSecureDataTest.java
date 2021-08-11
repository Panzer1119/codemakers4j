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

package de.codemakers.security.entities;

import de.codemakers.security.managers.ExpiringTrustedSecureDataManager;
import de.codemakers.security.util.EasyCryptUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

public class ExpiringTrustedSecureDataTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        logger.info(ExpiringTrustedSecureDataTest.class.getName());
        final ExpiringTrustedSecureDataManager expiringTrustedSecureDataManager = new ExpiringTrustedSecureDataManager(5, TimeUnit.SECONDS);
        logger.info(expiringTrustedSecureDataManager);
        expiringTrustedSecureDataManager.start();
        logger.info(expiringTrustedSecureDataManager);
        final String text_1 = "This is a sentence!";
        logger.info(text_1);
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        logger.info(keyPairGenerator);
        keyPairGenerator.initialize(1024, SecureRandom.getInstanceStrong());
        logger.info(keyPairGenerator);
        final KeyPair keyPair = keyPairGenerator.generateKeyPair();
        logger.info(keyPair);
        final KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        logger.info(keyGenerator);
        keyGenerator.init(256, SecureRandom.getInstanceStrong());
        logger.info(keyGenerator);
        final SecretKey secretKey = keyGenerator.generateKey();
        logger.info(secretKey);
        final ExpiringTrustedSecureData expiringTrustedSecureData = new ExpiringTrustedSecureData(text_1.getBytes(), EasyCryptUtil.encryptorOfCipher(Cipher.getInstance("AES"), secretKey), EasyCryptUtil.signerOfSHA512withRSA(keyPair.getPrivate()));
        logger.info(expiringTrustedSecureData);
        //expiringTrustedSecureData.createTimestamp(EasyCryptUtil.signerOfSHA256withRSA(keyPair.getPrivate()));
        expiringTrustedSecureData.createTimestamp(EasyCryptUtil.encryptorOfCipher(Cipher.getInstance("AES"), secretKey), EasyCryptUtil.signerOfSHA512withRSA(keyPair.getPrivate()));
        logger.info(expiringTrustedSecureData);
        logger.info("=========================================================================================");
        final ExpiringTrustedSecureData expiringTrustedSecureData_c_1 = expiringTrustedSecureData.copy();
        logger.info(expiringTrustedSecureData_c_1);
        //if (expiringTrustedSecureDataManager.acceptExpiringTrustedSecureData(expiringTrustedSecureData_c_1, EasyCryptUtil.verifierOfSHA256withRSA(keyPair.getPublic()))) {
        if (expiringTrustedSecureDataManager.acceptExpiringTrustedSecureData(expiringTrustedSecureData_c_1, EasyCryptUtil.decryptorOfCipher(Cipher.getInstance("AES"), secretKey), EasyCryptUtil.verifierOfSHA512withRSA(keyPair.getPublic()))) {
            logger.info("ACCEPTED 1");
            logger.info("VALID 1: " + expiringTrustedSecureData_c_1.verifyWithoutException(EasyCryptUtil.verifierOfSHA512withRSA(keyPair.getPublic())));
            logger.info(new String(expiringTrustedSecureData_c_1.getData()));
            final String text_2 = new String(expiringTrustedSecureData_c_1.decryptWithoutException(EasyCryptUtil.decryptorOfCipher(Cipher.getInstance("AES"), secretKey)));
            logger.info("1: " + text_2);
        } else {
            logger.info("REJECTED 1");
        }
        logger.info("=========================================================================================");
        logger.info(expiringTrustedSecureDataManager);
        logger.info("=========================================================================================");
        final ExpiringTrustedSecureData expiringTrustedSecureData_c_2 = expiringTrustedSecureData.copy();
        logger.info(expiringTrustedSecureData_c_2);
        /*
        logger.info("=========================================================================================");
        logger.info("=========================================================================================");
        logger.info("=========================================================================================");
        logger.info(expiringTrustedSecureDataManager);
        */
        Thread.sleep(6000);
        /*
        logger.info(expiringTrustedSecureDataManager);
        logger.info("=========================================================================================");
        logger.info("=========================================================================================");
        logger.info("=========================================================================================");
        */
        //if (expiringTrustedSecureDataManager.acceptExpiringTrustedSecureData(expiringTrustedSecureData_c_2, EasyCryptUtil.verifierOfSHA256withRSA(keyPair.getPublic()))) {
        if (expiringTrustedSecureDataManager.acceptExpiringTrustedSecureData(expiringTrustedSecureData_c_2, EasyCryptUtil.decryptorOfCipher(Cipher.getInstance("AES"), secretKey), EasyCryptUtil.verifierOfSHA512withRSA(keyPair.getPublic()))) {
            logger.info("ACCEPTED 2");
            logger.info("VALID 2: " + expiringTrustedSecureData_c_2.verifyWithoutException(EasyCryptUtil.verifierOfSHA512withRSA(keyPair.getPublic())));
            logger.info(new String(expiringTrustedSecureData_c_2.getData()));
            final String text_2 = new String(expiringTrustedSecureData_c_2.decryptWithoutException(EasyCryptUtil.decryptorOfCipher(Cipher.getInstance("AES"), secretKey)));
            logger.info("2: " + text_2);
        } else {
            logger.info("REJECTED 2");
        }
        logger.info("=========================================================================================");
        logger.info(expiringTrustedSecureDataManager);
        logger.info("==========================================");
        expiringTrustedSecureDataManager.stop();
        logger.info(expiringTrustedSecureDataManager);
        logger.info("==========================================");
        expiringTrustedSecureDataManager.clear();
        logger.info(expiringTrustedSecureDataManager);
    }
    
}
