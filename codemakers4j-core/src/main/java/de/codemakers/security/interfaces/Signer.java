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
import de.codemakers.base.action.RunningAction;
import de.codemakers.base.exceptions.NotSupportedRuntimeException;
import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.io.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import java.io.InputStream;

public interface Signer extends Cryptor {
    
    Logger logger = LogManager.getLogger();
    
    @Override
    default boolean usesIV() {
        return false;
    }
    
    byte[] sign(byte[] data) throws Exception;
    
    @Override
    default byte[] crypt(byte[] data, byte[] iv) throws Exception {
        return sign(data);
    }
    
    default byte[] sign(byte[] data, ToughConsumer<Throwable> failure) {
        try {
            return sign(data);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] signWithoutException(byte[] data) {
        return sign(data, null);
    }
    
    default ReturningAction<byte[]> signAction(byte[] data) {
        return new ReturningAction<>(() -> sign(data));
    }
    
    default byte[] sign(InputStream inputStream) throws Exception {
        IOUtil.processInputStream(inputStream, (buffer, read) -> update(buffer, 0, read));
        return sign();
    }
    
    default byte[] sign(InputStream inputStream, ToughConsumer<Throwable> failure) {
        try {
            return sign(inputStream);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] signWithoutException(InputStream inputStream) {
        return sign(inputStream, null);
    }
    
    default ReturningAction<byte[]> signAction(InputStream inputStream) {
        return new ReturningAction<>(() -> sign(inputStream));
    }
    
    default Cipher getCipher(byte[] iv) {
        throw new NotSupportedRuntimeException("This " + getClass().getSimpleName() + " does not work with a " + Cipher.class.getSimpleName());
    }
    
    byte[] sign() throws Exception;
    
    default byte[] sign(ToughConsumer<Throwable> failure) {
        try {
            return sign();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] signWithoutException() {
        return sign((ToughConsumer<Throwable>) null);
    }
    
    default ReturningAction<byte[]> signAction() {
        return new ReturningAction<>(this::sign);
    }
    
    void update(byte[] data, int offset, int length) throws Exception;
    
    default void update(byte[] data) throws Exception {
        update(data, 0, data.length);
    }
    
    default void update(byte[] data, int offset, int length, ToughConsumer<Throwable> failure) {
        try {
            update(data, offset, length);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
        }
    }
    
    default void update(byte[] data, ToughConsumer<Throwable> failure) {
        update(data, 0, data.length, failure);
    }
    
    default void updateWithoutException(byte[] data, int offset, int length) {
        update(data, offset, length, null);
    }
    
    default void updateWithoutException(byte[] data) {
        update(data, 0, data.length, null);
    }
    
    default RunningAction updateAction(byte[] data, int offset, int length) {
        return new RunningAction(() -> update(data, offset, length));
    }
    
    default RunningAction updateAction(byte[] data) {
        return new RunningAction(() -> update(data, 0, data.length));
    }
    
    int getSignatureLength();
    
}
