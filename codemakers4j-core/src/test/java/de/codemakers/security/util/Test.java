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

import de.codemakers.security.interfaces.Signer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Arrays;

public class Test {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        logger.info("Test");
        final KeyPairGenerator keyPairGenerator = EllipticCurveUtil.createKeyPairGeneratorEC(SecureRandom.getInstanceStrong(), EllipticCurveUtil.KEYSIZE_ECC_521);
        final KeyPair keyPair = keyPairGenerator.generateKeyPair();
        logger.info("keyPair=" + keyPair);
        logger.info("keyPair.getPrivate()=" + keyPair.getPrivate());
        logger.info("keyPair.getPublic()=" + keyPair.getPublic());
        logger.info("keyPair.getPrivate().getAlgorithm()=" + keyPair.getPrivate().getAlgorithm());
        logger.info("keyPair.getPublic().getAlgorithm()=" + keyPair.getPublic().getAlgorithm());
        final Signer signer = EasyCryptUtil.signerOfSHA256withECDSA(keyPair.getPrivate());
        logger.info("signer=" + signer);
        logger.info("signed:" + Arrays.toString(signer.sign("test".getBytes())));
        logger.info("signed.length  :" + signer.sign("test".getBytes()).length);
        logger.info("signed.length*8:" + (signer.sign("test".getBytes()).length * 8));
        final boolean success = EasyCryptUtil.publicKeyCanVerifyDataSignedWithPrivateKeyECDSA(keyPair.getPrivate(), keyPair.getPublic());
        logger.info("success=" + success);
    }
    
}
