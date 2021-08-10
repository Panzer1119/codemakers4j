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

package de.codemakers.base.util.interfaces;

import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.action.RunningAction;
import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Hasher {
    
    Logger logger = LogManager.getLogger();
    
    byte[] hash(byte[] data, int offset, int length) throws Exception;
    
    default byte[] hash(byte[] data) throws Exception {
        return hash(data, 0, data.length);
    }
    
    default byte[] hash(byte[] data, int offset, int length, ToughConsumer<Throwable> failure) {
        try {
            return hash(data, offset, length);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] hash(byte[] data, ToughConsumer<Throwable> failure) {
        return hash(data, 0, data.length, failure);
    }
    
    default byte[] hashWithoutException(byte[] data, int offset, int length) {
        return hash(data, offset, length, null);
    }
    
    default byte[] hashWithoutException(byte[] data) {
        return hashWithoutException(data, 0, data.length);
    }
    
    default ReturningAction<byte[]> hashAction(byte[] data, int offset, int length) {
        return new ReturningAction<>(() -> hash(data, offset, length));
    }
    
    default ReturningAction<byte[]> hashAction(byte[] data) {
        return hashAction(data, 0, data.length);
    }
    
    byte[] hash() throws Exception;
    
    default byte[] hash(ToughConsumer<Throwable> failure) {
        try {
            return hash();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] hashWithoutException() {
        return hash((ToughConsumer<Throwable>) null);
    }
    
    default ReturningAction<byte[]> hashAction() {
        return new ReturningAction<>(this::hash);
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
    
    int getHashLength();
    
}
