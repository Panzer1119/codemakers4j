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
import de.codemakers.base.util.interfaces.Streamable;
import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public interface Cryptable extends Streamable {
    
    Logger logger = LogManager.getLogger();
    
    byte[] crypt(Cryptor cryptor) throws Exception;
    
    default byte[] crypt(Cryptor cryptor, ToughConsumer<Throwable> failure) {
        try {
            return crypt(cryptor);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] cryptWithoutException(Cryptor cryptor) {
        return crypt(cryptor, null);
    }
    
    default ReturningAction<byte[]> cryptAction(Cryptor cryptor) {
        return new ReturningAction<>(() -> crypt(cryptor));
    }
    
    Cryptable cryptThis(Cryptor cryptor) throws Exception;
    
    default Cryptable cryptThis(Cryptor cryptor, ToughConsumer<Throwable> failure) {
        try {
            return cryptThis(cryptor);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return this;
        }
    }
    
    default Cryptable cryptThisWithoutException(Cryptor cryptor) {
        return cryptThis(cryptor, null);
    }
    
    default ReturningAction<Cryptable> cryptThisAction(Cryptor cryptor) {
        return new ReturningAction<>(() -> cryptThis(cryptor));
    }
    
    default CipherInputStream toCipherInputStream(Cryptor cryptor) {
        return cryptor.toCipherInputStream(toInputStream());
    }
    
    default CipherInputStream toCipherInputStream(Cryptor cryptor, byte[] iv) {
        return cryptor.toCipherInputStream(toInputStream(), iv);
    }
    
    default CipherInputStream toCipherInputStreamWithIV(Cryptor cryptor) {
        return cryptor.toCipherInputStreamWithIV(toInputStream());
    }
    
    default CipherOutputStream toCipherOutputStream(Cryptor cryptor) {
        return cryptor.toCipherOutputStream(toOutputStream());
    }
    
    default CipherOutputStream toCipherOutputStream(Cryptor cryptor, byte[] iv) {
        return cryptor.toCipherOutputStream(toOutputStream(), iv);
    }
    
    default CipherOutputStream toCipherOutputStreamWithIV(Cryptor cryptor, byte[] iv) {
        return cryptor.toCipherOutputStreamWithIV(toOutputStream(), iv);
    }
    
}
