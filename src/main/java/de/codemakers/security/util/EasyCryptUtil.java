/*
 *     Copyright 2018 Paul Hagedorn (Panzer1119)
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

import de.codemakers.security.interfaces.Cryptor;
import de.codemakers.security.interfaces.Decryptor;
import de.codemakers.security.interfaces.Encryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.SecureRandom;

public class EasyCryptUtil {
    
    public static final Cryptor cryptorOfCipher(Cipher cipher) {
        return (data) -> cipher.doFinal(data);
    }
    
    public static final Encryptor encryptorOfCipher(Cipher cipher) {
        return (data) -> cipher.doFinal(data);
    }
    
    public static final Encryptor encryptorOfCipher(Cipher cipher, SecretKey key) throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptorOfCipher(cipher);
    }
    
    public static final Encryptor encryptorOfCipher(Cipher cipher, SecretKey key, SecureRandom secureRandom) throws InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);
        return encryptorOfCipher(cipher);
    }
    
    public static final Decryptor decryptorOfCipher(Cipher cipher) {
        return (data) -> cipher.doFinal(data);
    }
    
    public static final Decryptor decryptorOfCipher(Cipher cipher, SecretKey key) throws InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        return decryptorOfCipher(cipher);
    }
    
    public static final Decryptor decryptorOfCipher(Cipher cipher, SecretKey key, SecureRandom secureRandom) throws InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, key, secureRandom);
        return decryptorOfCipher(cipher);
    }
    
}
