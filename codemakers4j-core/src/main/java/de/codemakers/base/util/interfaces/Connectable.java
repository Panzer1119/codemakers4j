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
import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
public interface Connectable {
    
    Logger logger = LogManager.getLogger();
    
    default boolean connect() throws Exception {
        return connect(false);
    }
    
    boolean connect(boolean reconnect) throws Exception;
    
    default boolean connect(ToughConsumer<Throwable> failure) {
        return connect(false, failure);
    }
    
    default boolean connect(boolean reconnect, ToughConsumer<Throwable> failure) {
        try {
            return connect(reconnect);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    default boolean connectWithoutException() {
        return connectWithoutException(false);
    }
    
    default boolean connectWithoutException(boolean reconnect) {
        return connect(reconnect, null);
    }
    
    default ReturningAction<Boolean> connectAction() {
        return connectAction(false);
    }
    
    default ReturningAction<Boolean> connectAction(boolean reconnect) {
        return new ReturningAction<>(() -> connect(reconnect));
    }
    
}
