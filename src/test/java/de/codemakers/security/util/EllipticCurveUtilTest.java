/*
 *     Copyright 2018 - 2019 Paul Hagedorn (Panzer1119)
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

import at.favre.lib.crypto.HKDF;
import de.codemakers.base.logger.Logger;
import de.codemakers.io.file.AdvancedFile;

import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class EllipticCurveUtilTest {
    
    public static final AdvancedFile ADVANCED_FILE_ECDSA_KEY_PAIR_1 = new AdvancedFile("src/test/resources/ec/keyPair_1.txt");
    public static final AdvancedFile ADVANCED_FILE_ECDSA_KEY_PAIR_2 = new AdvancedFile("src/test/resources/ec/keyPair_2.txt");
    
    public static final void main(String[] args) throws Exception {
        initECDSA();
        final byte[] staticSalt = new byte[32];
        SecureRandom.getInstanceStrong().nextBytes(staticSalt);
        //// Part 1
        /*
            Each Partner is generating his EC KeyPair (the PublicKey is then shared with the other partner)
         */
        // Partner 1
        final KeyPairGenerator keyPairGenerator_1 = EllipticCurveUtil.createKeyPairGeneratorEC(SecureRandom.getInstanceStrong(), 256);
        final KeyPair keyPair_1 = keyPairGenerator_1.generateKeyPair();
        Logger.log("keyPair_1=" + keyPair_1);
        Logger.log("keyPair_1.getPrivate()=" + keyPair_1.getPrivate());
        Logger.log("keyPair_1.getPublic()=" + keyPair_1.getPublic());
        // Partner 2
        final KeyPairGenerator keyPairGenerator_2 = EllipticCurveUtil.createKeyPairGeneratorEC(SecureRandom.getInstanceStrong(), 256);
        final KeyPair keyPair_2 = keyPairGenerator_2.generateKeyPair();
        Logger.log("keyPair_2=" + keyPair_2);
        Logger.log("keyPair_2.getPrivate()=" + keyPair_2.getPrivate());
        Logger.log("keyPair_2.getPublic()=" + keyPair_2.getPublic());
        //// Part 2
        /*
            After the partners exchanged their PublicKeys, both are generating a shared secret
         */
        // Partner 1
        final KeyAgreement keyAgreement_1 = EllipticCurveUtil.createKeyAgreement();
        Logger.log("keyAgreement_1=" + keyAgreement_1);
        final PublicKey partner_1 = KeyFactory.getInstance(EllipticCurveUtil.ALGORITHM_EC).generatePublic(new X509EncodedKeySpec(keyPair_2.getPublic().getEncoded()));
        Logger.log("partner_1=" + partner_1);
        keyAgreement_1.init(keyPair_1.getPrivate());
        keyAgreement_1.doPhase(partner_1, true);
        Logger.log("keyAgreement_1=" + keyAgreement_1);
        final byte[] sharedSecret_1 = keyAgreement_1.generateSecret();
        Logger.log("sharedSecret_1=" + Arrays.toString(sharedSecret_1));
        Logger.log("sharedSecret_1.length=" + sharedSecret_1.length);
        // Partner 2
        final KeyAgreement keyAgreement_2 = EllipticCurveUtil.createKeyAgreement();
        Logger.log("keyAgreement_2=" + keyAgreement_2);
        final PublicKey partner_2 = KeyFactory.getInstance(EllipticCurveUtil.ALGORITHM_EC).generatePublic(new X509EncodedKeySpec(keyPair_1.getPublic().getEncoded()));
        Logger.log("partner_2=" + partner_2);
        keyAgreement_2.init(keyPair_2.getPrivate());
        keyAgreement_2.doPhase(partner_2, true);
        Logger.log("keyAgreement_2=" + keyAgreement_2);
        final byte[] sharedSecret_2 = keyAgreement_2.generateSecret();
        Logger.log("sharedSecret_2=" + Arrays.toString(sharedSecret_2));
        Logger.log("sharedSecret_2.length=" + sharedSecret_2.length);
        //// Part 3
        /*
            Now both partners are generating the exact same high-quality AES SecretKey via an HKDF (HmacSha512) (library used for this)
         */
        // Partner 1
        final HKDF hkdf_1 = HKDF.fromHmacSha512();
        final byte[] pseudoRandomKey_1 = hkdf_1.extract(staticSalt, sharedSecret_1);
        Logger.log("pseudoRandomKey_1=" + Arrays.toString(pseudoRandomKey_1));
        final byte[] expandedAESKey_1 = hkdf_1.expand(pseudoRandomKey_1, "aes-key".getBytes(), 32);
        //final byte[] expandedIV_1 = hkdf_1.expand(pseudoRandomKey_1, "aes-iv".getBytes(), 32);
        Logger.log("expandedAESKey_1=" + Arrays.toString(expandedAESKey_1));
        //Logger.log("expandedIV_1=" + Arrays.toString(expandedIV_1));
        // Partner 2
        final HKDF hkdf_2 = HKDF.fromHmacSha512();
        final byte[] pseudoRandomKey_2 = hkdf_2.extract(staticSalt, sharedSecret_2);
        Logger.log("pseudoRandomKey_2=" + Arrays.toString(pseudoRandomKey_2));
        final byte[] expandedAESKey_2 = hkdf_1.expand(pseudoRandomKey_2, "aes-key".getBytes(), 32);
        //final byte[] expandedIV_2 = hkdf_1.expand(pseudoRandomKey_2, "aes-iv".getBytes(), 32);
        Logger.log("expandedAESKey_2=" + Arrays.toString(expandedAESKey_2));
        //Logger.log("expandedIV_2=" + Arrays.toString(expandedIV_2));
    }
    
    private static void initECDSA() throws Exception {
        ADVANCED_FILE_ECDSA_KEY_PAIR_1.getParentFile().mkdirs();
        ADVANCED_FILE_ECDSA_KEY_PAIR_2.getParentFile().mkdirs();
        ADVANCED_FILE_ECDSA_KEY_PAIR_1.createNewFile();
    }
    
}
