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
import de.codemakers.base.util.tough.ToughFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
public interface Formatter<T> extends ToughFunction<T, String> {
    
    Logger logger = LogManager.getLogger();
    
    String format(T input) throws Exception;
    
    default String format(T input, ToughConsumer<Exception> failure) {
        try {
            return format(input);
        } catch (Exception e) {
            if (failure != null) {
                failure.acceptWithoutException(e);
            } else {
                logger.error("Error while formatting", e);
            }
            return null;
        }
    }
    
    default String formatWithoutException(T input) {
        return format(input, null);
    }
    
    default ReturningAction<String> formatAction(T input) {
        return new ReturningAction<>(() -> format(input));
    }
    
    @Override
    default String apply(T t) throws Exception {
        return format(t);
    }
    
    @Override
    default boolean canConsume() {
        return true;
    }
    
    @Override
    default boolean canSupply() {
        return true;
    }
    
}
