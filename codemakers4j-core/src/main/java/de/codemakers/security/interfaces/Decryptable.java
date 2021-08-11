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

public interface Decryptable extends Cryptable {
    
    Logger logger = LogManager.getLogger();
    
    byte[] decrypt(Decryptor decryptor) throws Exception;
    
    @Override
    default byte[] crypt(Cryptor cryptor) throws Exception {
        return decrypt((Decryptor) cryptor);
    }
    
    default byte[] decrypt(Decryptor decryptor, ToughConsumer<Throwable> failure) {
        try {
            return decrypt(decryptor);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] decryptWithoutException(Decryptor decryptor) {
        return decrypt(decryptor, null);
    }
    
    default ReturningAction<byte[]> decryptAction(Decryptor decryptor) {
        return new ReturningAction<>(() -> decrypt(decryptor));
    }
    
    Decryptable decryptThis(Decryptor decryptor) throws Exception;
    
    @Override
    default Cryptable cryptThis(Cryptor cryptor) throws Exception {
        return decryptThis((Decryptor) cryptor);
    }
    
    default Decryptable decryptThis(Decryptor decryptor, ToughConsumer<Throwable> failure) {
        try {
            return decryptThis(decryptor);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return this;
        }
    }
    
    default Decryptable decryptThisWithoutException(Decryptor decryptor) {
        return decryptThis(decryptor, null);
    }
    
    default ReturningAction<Decryptable> decryptThisAction(Decryptor decryptor) {
        return new ReturningAction<>(() -> decryptThis(decryptor));
    }
    
}
