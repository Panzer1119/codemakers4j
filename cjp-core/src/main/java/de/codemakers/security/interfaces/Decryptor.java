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

public interface Decryptor extends Cryptor {
    
    Logger logger = LogManager.getLogger();
    
    byte[] decrypt(byte[] data, byte[] iv) throws Exception;
    
    @Override
    default byte[] crypt(byte[] data, byte[] iv) throws Exception {
        return decrypt(data, iv);
    }
    
    default byte[] decrypt(byte[] data, byte[] iv, ToughConsumer<Throwable> failure) {
        try {
            return decrypt(data, iv);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] decryptWithoutException(byte[] data, byte[] iv) {
        return decrypt(data, iv, null);
    }
    
    default ReturningAction<byte[]> decryptAction(byte[] data, byte[] iv) {
        return new ReturningAction<>(() -> decrypt(data, iv));
    }
    
    default byte[] decrypt(byte[] data) throws Exception {
        return decrypt(data, (byte[]) null);
    }
    
    @Override
    default byte[] crypt(byte[] data) throws Exception {
        return decrypt(data);
    }
    
    default byte[] decrypt(byte[] data, ToughConsumer<Throwable> failure) {
        try {
            return decrypt(data);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] decryptWithoutException(byte[] data) {
        return decrypt(data, (ToughConsumer<Throwable>) null);
    }
    
    default ReturningAction<byte[]> decryptAction(byte[] data) {
        return new ReturningAction<>(() -> decrypt(data));
    }
    
}
