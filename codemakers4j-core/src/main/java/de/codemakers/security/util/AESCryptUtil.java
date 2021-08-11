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

import de.codemakers.security.interfaces.Decryptor;
import de.codemakers.security.interfaces.Encryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Random;

public class AESCryptUtil {
    
    public static final String ALGORITHM_AES = "AES";
    
    public static final String MODE_AES = "AES";
    public static final String MODE_AES_CBC_PKCS5Padding = "AES/CBC/PKCS5Padding";
    public static final String MODE_AES_GCM_NoPadding = "AES/GCM/NoPadding";
    
    public static final int IV_BYTES_CBC = 16;
    public static final int IV_BYTES_GCM = 12;
    
    public static final Cipher createCipherAES() {
        return EasyCryptUtil.createCipher(MODE_AES);
    }
    
    public static final Cipher createCipherAESCBCPKCS5Padding() {
        return EasyCryptUtil.createCipher(MODE_AES_CBC_PKCS5Padding);
    }
    
    public static final Cipher createCipherAESGCMNoPadding() {
        return EasyCryptUtil.createCipher(MODE_AES_GCM_NoPadding);
    }
    
    public static final byte[] generateSecureRandomIVAESCBC() {
        return EasyCryptUtil.generateSecureRandomBytes(IV_BYTES_CBC);
    }
    
    public static final byte[] generateSecureRandomIVAESGCM() {
        return EasyCryptUtil.generateSecureRandomBytes(IV_BYTES_GCM);
    }
    
    public static final byte[] generateRandomIVAESCBC(Random random) {
        return EasyCryptUtil.generateRandomBytes(IV_BYTES_CBC, random);
    }
    
    public static final byte[] generateRandomIVAESGCM(Random random) {
        return EasyCryptUtil.generateRandomBytes(IV_BYTES_GCM, random);
    }
    
    public static final Encryptor createEncryptorAESCBCPKCS5Padding(SecretKey secretKey) {
        return EasyCryptUtil.createEncryptor(() -> createCipherAESCBCPKCS5Padding(), secretKey, IvParameterSpec::new);
    }
    
    public static final Encryptor createEncryptorAESGCMNoPadding(SecretKey secretKey, int authentication_tag_bits) {
        return EasyCryptUtil.createEncryptor(() -> createCipherAESGCMNoPadding(), secretKey, (iv) -> new GCMParameterSpec(authentication_tag_bits, iv));
    }
    
    public static final Decryptor createDecryptorAESCBCPKCS5Padding(SecretKey secretKey) {
        return EasyCryptUtil.createDecryptor(() -> createCipherAESCBCPKCS5Padding(), secretKey, IvParameterSpec::new);
    }
    
    public static final Decryptor createDecryptorAESGCMNoPadding(SecretKey secretKey, int authentication_tag_bits) {
        return EasyCryptUtil.createDecryptor(() -> createCipherAESGCMNoPadding(), secretKey, (iv) -> new GCMParameterSpec(authentication_tag_bits, iv));
    }
    
}
