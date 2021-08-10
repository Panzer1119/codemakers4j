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
import de.codemakers.base.util.HashUtil;
import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
public interface Hashable {
    
    Logger logger = LogManager.getLogger();
    
    byte[] hash(Hasher hasher) throws Exception;
    
    default byte[] hash(Hasher hasher, ToughConsumer<Throwable> failure) {
        try {
            return hash(hasher);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] hashWithoutException(Hasher hasher) {
        return hash(hasher, null);
    }
    
    default ReturningAction<byte[]> hashAction(Hasher hasher) {
        return new ReturningAction<>(() -> hash(hasher));
    }
    
    default byte[] hash() throws Exception {
        return hash(HashUtil.HASHER_64_XX);
    }
    
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
    
}
