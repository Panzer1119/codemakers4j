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

package de.codemakers.security.interfaces;

import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Encryptor extends Cryptor {
    
    Logger logger = LogManager.getLogger();
    
    byte[] encrypt(byte[] data, byte[] iv) throws Exception;
    
    @Override
    default byte[] crypt(byte[] data, byte[] iv) throws Exception {
        return encrypt(data, iv);
    }
    
    default byte[] encrypt(byte[] data, byte[] iv, ToughConsumer<Throwable> failure) {
        try {
            return encrypt(data, iv);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] encryptWithoutException(byte[] data, byte[] iv) {
        return encrypt(data, iv, null);
    }
    
    default ReturningAction<byte[]> encryptAction(byte[] data, byte[] iv) {
        return new ReturningAction<>(() -> encrypt(data, iv));
    }
    
    default byte[] encrypt(byte[] data) throws Exception {
        return encrypt(data, (byte[]) null);
    }
    
    @Override
    default byte[] crypt(byte[] data) throws Exception {
        return encrypt(data);
    }
    
    default byte[] encrypt(byte[] data, ToughConsumer<Throwable> failure) {
        try {
            return encrypt(data);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] encryptWithoutException(byte[] data) {
        return encrypt(data, (ToughConsumer<Throwable>) null);
    }
    
    default ReturningAction<byte[]> encryptAction(byte[] data) {
        return new ReturningAction<>(() -> encrypt(data));
    }
    
}
