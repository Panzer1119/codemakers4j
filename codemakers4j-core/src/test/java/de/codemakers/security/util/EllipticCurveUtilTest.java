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

import at.favre.lib.crypto.HKDF;
import de.codemakers.io.file.AdvancedFile;
import de.codemakers.security.interfaces.Decryptor;
import de.codemakers.security.interfaces.Encryptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class EllipticCurveUtilTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final AdvancedFile ADVANCED_FILE_ECDSA_KEY_PAIR_1 = new AdvancedFile("src/test/resources/ec/keyPair_1.txt");
    public static final AdvancedFile ADVANCED_FILE_ECDSA_KEY_PAIR_2 = new AdvancedFile("src/test/resources/ec/keyPair_2.txt");
    public static KeyPair ECDSA_KEY_PAIR_1 = null;
    public static KeyPair ECDSA_KEY_PAIR_2 = null;
    
    public static final void main(String[] args) throws Exception {
        final SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        initECDSA(secureRandom);
        final byte[] staticSalt = new byte[32];
        secureRandom.nextBytes(staticSalt);
        //// Part 1
        /*
            Each Partner is generating his EC KeyPair (the PublicKey is then shared with the other partner)
         */
        // Partner 1
        final KeyPairGenerator keyPairGenerator_1 = EllipticCurveUtil.createKeyPairGeneratorEC(secureRandom, 256);
        final KeyPair keyPair_1 = keyPairGenerator_1.generateKeyPair();
        logger.info("keyPair_1=" + keyPair_1);
        logger.info("keyPair_1.getPrivate()=" + keyPair_1.getPrivate());
        logger.info("keyPair_1.getPublic()=" + keyPair_1.getPublic());
        final byte[] bytes_publicKey_1 = keyPair_1.getPublic().getEncoded();
        logger.info("bytes_publicKey_1=" + Arrays.toString(bytes_publicKey_1));
        // Partner 2
        final KeyPairGenerator keyPairGenerator_2 = EllipticCurveUtil.createKeyPairGeneratorEC(secureRandom, 256);
        final KeyPair keyPair_2 = keyPairGenerator_2.generateKeyPair();
        logger.info("keyPair_2=" + keyPair_2);
        logger.info("keyPair_2.getPrivate()=" + keyPair_2.getPrivate());
        logger.info("keyPair_2.getPublic()=" + keyPair_2.getPublic());
        final byte[] bytes_publicKey_2 = keyPair_2.getPublic().getEncoded();
        logger.info("bytes_publicKey_2=" + Arrays.toString(bytes_publicKey_2));
        //// Part 1.5
        /*
            PublicKeys are exchanged and signed/verified
         */
        /// Signing
        // Partner 1
        final Signature signature_sign_1 = Signature.getInstance(EllipticCurveUtil.ALGORITHM_SHA256withECDSA);
        signature_sign_1.initSign(ECDSA_KEY_PAIR_1.getPrivate());
        signature_sign_1.update(bytes_publicKey_1);
        final byte[] signature_publicKey_1 = signature_sign_1.sign();
        // Partner 2
        final Signature signature_sign_2 = Signature.getInstance(EllipticCurveUtil.ALGORITHM_SHA256withECDSA);
        signature_sign_2.initSign(ECDSA_KEY_PAIR_2.getPrivate());
        signature_sign_2.update(bytes_publicKey_2);
        final byte[] signature_publicKey_2 = signature_sign_2.sign();
        /// Verifying
        // Partner 1
        final Signature signature_verify_1 = Signature.getInstance(EllipticCurveUtil.ALGORITHM_SHA256withECDSA);
        signature_verify_1.initVerify(ECDSA_KEY_PAIR_2.getPublic());
        signature_verify_1.update(bytes_publicKey_2);
        if (!signature_verify_1.verify(signature_publicKey_2)) {
            throw new Exception("ECDSA Verification 1 failed");
        } else {
            logger.info("Partner 1 verified with Partner 2's ECDSA PublicKey, that Partner 2's EC PublicKey is real");
        }
        final PublicKey partner_1 = KeyFactory.getInstance(EllipticCurveUtil.ALGORITHM_EC).generatePublic(new X509EncodedKeySpec(bytes_publicKey_2));
        // Partner 2
        final Signature signature_verify_2 = Signature.getInstance(EllipticCurveUtil.ALGORITHM_SHA256withECDSA);
        signature_verify_2.initVerify(ECDSA_KEY_PAIR_1.getPublic());
        signature_verify_2.update(bytes_publicKey_1);
        if (!signature_verify_2.verify(signature_publicKey_1)) {
            throw new Exception("ECDSA Verification 2 failed");
        } else {
            logger.info("Partner 2 verified with Partner 1's ECDSA PublicKey, that Partner 1's EC PublicKey is real");
        }
        final PublicKey partner_2 = KeyFactory.getInstance(EllipticCurveUtil.ALGORITHM_EC).generatePublic(new X509EncodedKeySpec(bytes_publicKey_1));
        //// Part 2
        /*
            After the partners exchanged their PublicKeys, both are generating a shared secret
         */
        // Partner 1
        final KeyAgreement keyAgreement_1 = EllipticCurveUtil.createKeyAgreement();
        logger.info("keyAgreement_1=" + keyAgreement_1);
        //final PublicKey partner_1 = KeyFactory.getInstance(EllipticCurveUtil.ALGORITHM_EC).generatePublic(new X509EncodedKeySpec(bytes_publicKey_2));
        logger.info("partner_1=" + partner_1);
        keyAgreement_1.init(keyPair_1.getPrivate());
        keyAgreement_1.doPhase(partner_1, true);
        logger.info("keyAgreement_1=" + keyAgreement_1);
        final byte[] sharedSecret_1 = keyAgreement_1.generateSecret();
        logger.info("sharedSecret_1=" + Arrays.toString(sharedSecret_1));
        logger.info("sharedSecret_1.length=" + sharedSecret_1.length);
        // Partner 2
        final KeyAgreement keyAgreement_2 = EllipticCurveUtil.createKeyAgreement();
        logger.info("keyAgreement_2=" + keyAgreement_2);
        //final PublicKey partner_2 = KeyFactory.getInstance(EllipticCurveUtil.ALGORITHM_EC).generatePublic(new X509EncodedKeySpec(bytes_publicKey_1));
        logger.info("partner_2=" + partner_2);
        keyAgreement_2.init(keyPair_2.getPrivate());
        keyAgreement_2.doPhase(partner_2, true);
        logger.info("keyAgreement_2=" + keyAgreement_2);
        final byte[] sharedSecret_2 = keyAgreement_2.generateSecret();
        logger.info("sharedSecret_2=" + Arrays.toString(sharedSecret_2));
        logger.info("sharedSecret_2.length=" + sharedSecret_2.length);
        //// Part 3
        /*
            Now both partners are generating the exact same high-quality AES SecretKey via an HKDF (HmacSha512) (library used for this)
         */
        // Partner 1
        final HKDF hkdf_1 = HKDF.fromHmacSha512();
        final byte[] pseudoRandomKey_1 = hkdf_1.extract(staticSalt, sharedSecret_1);
        logger.info("pseudoRandomKey_1=" + Arrays.toString(pseudoRandomKey_1));
        final byte[] expandedAESKey_1 = hkdf_1.expand(pseudoRandomKey_1, "aes-key".getBytes(), 32);
        //final byte[] expandedIV_1 = hkdf_1.expand(pseudoRandomKey_1, "aes-iv".getBytes(), 32);
        logger.info("expandedAESKey_1=" + Arrays.toString(expandedAESKey_1));
        //logger.info("expandedIV_1=" + Arrays.toString(expandedIV_1));
        final SecretKey secretKey_1 = new SecretKeySpec(expandedAESKey_1, AESCryptUtil.ALGORITHM_AES);
        // Partner 2
        final HKDF hkdf_2 = HKDF.fromHmacSha512();
        final byte[] pseudoRandomKey_2 = hkdf_2.extract(staticSalt, sharedSecret_2);
        logger.info("pseudoRandomKey_2=" + Arrays.toString(pseudoRandomKey_2));
        final byte[] expandedAESKey_2 = hkdf_1.expand(pseudoRandomKey_2, "aes-key".getBytes(), 32);
        //final byte[] expandedIV_2 = hkdf_1.expand(pseudoRandomKey_2, "aes-iv".getBytes(), 32);
        logger.info("expandedAESKey_2=" + Arrays.toString(expandedAESKey_2));
        //logger.info("expandedIV_2=" + Arrays.toString(expandedIV_2));
        final SecretKey secretKey_2 = new SecretKeySpec(expandedAESKey_2, AESCryptUtil.ALGORITHM_AES);
        //// Part 4
        /*
            Sending messages to test the encryption/decryption
         */
        /// Init
        // Partner 1
        final Encryptor encryptor_1 = AESCryptUtil.createEncryptorAESGCMNoPadding(secretKey_1, 128);
        final Decryptor decryptor_1 = AESCryptUtil.createDecryptorAESGCMNoPadding(secretKey_1, 128);
        // Partner 2
        final Encryptor encryptor_2 = AESCryptUtil.createEncryptorAESGCMNoPadding(secretKey_2, 128);
        final Decryptor decryptor_2 = AESCryptUtil.createDecryptorAESGCMNoPadding(secretKey_2, 128);
        /// Encrypting
        logger.info("Encrypting");
        // Partner 1
        final String message_1 = "This is a message from Partner 1 to Partner 2";
        logger.info("message_1=" + message_1);
        final byte[] iv_1 = AESCryptUtil.generateSecureRandomIVAESGCM();
        final byte[] message_encrypted_1 = encryptor_1.encrypt(message_1.getBytes(), iv_1);
        // Partner 2
        final String message_2 = "This is a message from Partner 2 to Partner 1";
        logger.info("message_2=" + message_2);
        final byte[] iv_2 = AESCryptUtil.generateSecureRandomIVAESGCM();
        final byte[] message_encrypted_2 = encryptor_2.encrypt(message_2.getBytes(), iv_2);
        /// Decrypting
        logger.info("Decrypting");
        // Partner 1
        final byte[] message_decrypted_1 = decryptor_1.decrypt(message_encrypted_2, iv_2);
        logger.info("message_decrypted_1=" + new String(message_decrypted_1));
        // Partner 2
        final byte[] message_decrypted_2 = decryptor_2.decrypt(message_encrypted_1, iv_1);
        logger.info("message_decrypted_2=" + new String(message_decrypted_2));
    }
    
    private static void initECDSA(final SecureRandom secureRandom) throws Exception {
        ADVANCED_FILE_ECDSA_KEY_PAIR_1.getParentFile().mkdirs();
        ADVANCED_FILE_ECDSA_KEY_PAIR_2.getParentFile().mkdirs();
        if (!ADVANCED_FILE_ECDSA_KEY_PAIR_1.exists()) {
            ECDSA_KEY_PAIR_1 = createECDSA(ADVANCED_FILE_ECDSA_KEY_PAIR_1, secureRandom);
        } else {
            ECDSA_KEY_PAIR_1 = loadECDSA(ADVANCED_FILE_ECDSA_KEY_PAIR_1);
        }
        logger.info("ECDSA_KEY_PAIR_1=" + ECDSA_KEY_PAIR_1);
        if (!ADVANCED_FILE_ECDSA_KEY_PAIR_2.exists()) {
            ECDSA_KEY_PAIR_2 = createECDSA(ADVANCED_FILE_ECDSA_KEY_PAIR_2, secureRandom);
        } else {
            ECDSA_KEY_PAIR_2 = loadECDSA(ADVANCED_FILE_ECDSA_KEY_PAIR_2);
        }
        logger.info("ECDSA_KEY_PAIR_2=" + ECDSA_KEY_PAIR_2);
    }
    
    private static KeyPair createECDSA(AdvancedFile advancedFile, final SecureRandom secureRandom) throws Exception {
        final KeyPairGenerator keyPairGenerator = EllipticCurveUtil.createKeyPairGeneratorEC(secureRandom, 256);
        final KeyPair keyPair = keyPairGenerator.generateKeyPair();
        final PublicKey publicKey = keyPair.getPublic();
        final PrivateKey privateKey = keyPair.getPrivate();
        final DataOutputStream dataOutputStream = new DataOutputStream(advancedFile.createOutputStream(false));
        dataOutputStream.writeInt(publicKey.getEncoded().length);
        dataOutputStream.write(publicKey.getEncoded());
        dataOutputStream.writeInt(privateKey.getEncoded().length);
        dataOutputStream.write(privateKey.getEncoded());
        dataOutputStream.flush();
        dataOutputStream.close();
        return keyPair;
    }
    
    private static KeyPair loadECDSA(AdvancedFile advancedFile) throws Exception {
        final DataInputStream dataInputStream = new DataInputStream(advancedFile.createInputStream());
        final byte[] bytes_publicKey = new byte[dataInputStream.readInt()];
        dataInputStream.read(bytes_publicKey);
        final byte[] bytes_privateKey = new byte[dataInputStream.readInt()];
        dataInputStream.read(bytes_privateKey);
        dataInputStream.close();
        final PublicKey publicKey = KeyFactory.getInstance(EllipticCurveUtil.ALGORITHM_EC).generatePublic(new X509EncodedKeySpec(bytes_publicKey));
        final PrivateKey privateKey = KeyFactory.getInstance(EllipticCurveUtil.ALGORITHM_EC)
                .generatePrivate(new PKCS8EncodedKeySpec(bytes_privateKey));
        return new KeyPair(publicKey, privateKey);
    }
    
}
