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
public interface Convertable<T> {
    
    Logger logger = LogManager.getLogger();
    
    default T convert() throws Exception {
        return convert((Class<T>) null);
    }
    
    T convert(Class<T> clazz) throws Exception;
    
    default T convert(ToughConsumer<Throwable> failure) {
        return convert(null, failure);
    }
    
    default T convert(Class<T> clazz, ToughConsumer<Throwable> failure) {
        try {
            return convert(clazz);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default T convertWithoutException() {
        return convertWithoutException(null);
    }
    
    default T convertWithoutException(Class<T> clazz) {
        return convert(clazz, null);
    }
    
    default ReturningAction<T> convertAction() {
        return convertAction(null);
    }
    
    default ReturningAction<T> convertAction(Class<T> clazz) {
        return new ReturningAction<>(() -> convert(clazz));
    }
    
}
