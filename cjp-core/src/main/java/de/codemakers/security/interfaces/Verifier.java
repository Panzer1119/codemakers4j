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
import de.codemakers.base.util.tough.ToughBiPredicate;
import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.io.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

public interface Verifier extends ToughBiPredicate<byte[], byte[]> {
    
    Logger logger = LogManager.getLogger();
    
    @Override
    default Boolean test(byte[] data, byte[] data_signature) throws Exception {
        return verify(data, data_signature);
    }
    
    boolean verify(byte[] data, byte[] data_signature) throws Exception;
    
    default boolean verify(byte[] data, byte[] data_signature, ToughConsumer<Throwable> failure) {
        try {
            return verify(data, data_signature);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    default boolean verifyWithoutException(byte[] data, byte[] data_signature) {
        return verify(data, data_signature, null);
    }
    
    default ReturningAction<Boolean> verifyAction(byte[] data, byte[] data_signature) {
        return new ReturningAction<>(() -> verify(data, data_signature));
    }
    
    default boolean verify(InputStream inputStream, byte[] data_signature) throws Exception {
        IOUtil.processInputStream(inputStream, (buffer, read) -> update(buffer, 0, read));
        return verify(data_signature);
    }
    
    default boolean verify(InputStream inputStream, byte[] data_signature, ToughConsumer<Throwable> failure) {
        try {
            return verify(inputStream, data_signature);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    default boolean verifyWithoutException(InputStream inputStream, byte[] data_signature) {
        return verify(inputStream, data_signature, null);
    }
    
    default ReturningAction<Boolean> verifyAction(InputStream inputStream, byte[] data_signature) {
        return new ReturningAction<>(() -> verify(inputStream, data_signature));
    }
    
    boolean verify(byte[] data_signature) throws Exception;
    
    default boolean verify(byte[] data_signature, ToughConsumer<Throwable> failure) {
        try {
            return verify(data_signature);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    default boolean verifyWithoutException(byte[] data_signature) {
        return verify(data_signature, (ToughConsumer<Throwable>) null);
    }
    
    default ReturningAction<Boolean> verifyAction(byte[] data_signature) {
        return new ReturningAction<>(() -> verify(data_signature));
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
