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
public interface Updatable<R, T> {
    
    Logger logger = LogManager.getLogger();
    
    R update(T t) throws Exception;
    
    default R update() throws Exception {
        return update((T) null);
    }
    
    default R update(ToughConsumer<Throwable> failure, T t) {
        try {
            return update(t);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default R update(ToughConsumer<Throwable> failure) {
        try {
            return update();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default R updateWithoutException(T t) {
        return update(null, t);
    }
    
    default R updateWithoutException() {
        return update((ToughConsumer<Throwable>) null);
    }
    
    default ReturningAction<R> updateAction(T t) {
        return new ReturningAction<>(() -> update(t));
    }
    
    default ReturningAction<R> updateAction() {
        return new ReturningAction<>(this::update);
    }
    
}
