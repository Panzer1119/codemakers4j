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

package de.codemakers.security.entities;

import de.codemakers.base.entities.Data;
import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.security.interfaces.*;

import java.util.Objects;

public class SecureData extends Data implements Decryptable, Encryptable {
    
    public SecureData(byte[] data, Decryptor decryptor) {
        this(decryptor.decryptWithoutException(data));
    }
    
    public SecureData(byte[] data, Encryptor encryptor) {
        this(encryptor.encryptWithoutException(data));
    }
    
    public SecureData(byte[] data) {
        super(data);
    }
    
    @Override
    public Copyable copy() {
        return new SecureData(data);
    }
    
    @Override
    public byte[] crypt(Cryptor cryptor) throws Exception {
        Objects.requireNonNull(cryptor);
        return cryptor.crypt(getData());
    }
    
    @Override
    public byte[] decrypt(Decryptor decryptor) throws Exception {
        Objects.requireNonNull(decryptor);
        return decryptor.decrypt(getData());
    }
    
    @Override
    public byte[] encrypt(Encryptor encryptor) throws Exception {
        Objects.requireNonNull(encryptor);
        return encryptor.encrypt(getData());
    }
    
}