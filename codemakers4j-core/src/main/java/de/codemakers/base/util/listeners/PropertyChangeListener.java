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

package de.codemakers.base.util.listeners;

import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
public interface PropertyChangeListener<T> {
    
    Logger logger = LogManager.getLogger();
    
    boolean onChange(T oldValue, T newValue) throws Exception;
    
    default boolean onChange(T oldValue, T newValue, ToughConsumer<Throwable> failure) {
        try {
            return onChange(oldValue, newValue);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    default boolean onChangeWithoutException(T oldValue, T newValue) {
        return onChange(oldValue, newValue, null);
    }
    
    default ReturningAction<Boolean> onChangeAction(T oldValue, T newValue) {
        return new ReturningAction<>(() -> onChange(oldValue, newValue));
    }
    
}
