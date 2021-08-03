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

package de.codemakers.base.util.tough;

import de.codemakers.base.logger.Logger;

public interface Tough<T, R, E extends Exception> {

    R action(T t) throws E;

    default R action(T t, ToughConsumer<E, Exception> failure) {
        try {
            return action(t);
        } catch (Exception ex) {
            handleException(ex, failure);
            return null;
        }
    }

    default R actionWithoutException(T t) {
        return action(t, null);
    }
    
    default void handleException(Exception exception, ToughConsumer<E, Exception> failure) {
        if (failure != null) {
            failure.acceptWithoutException((E) exception);
        } else {
            Logger.handleError(exception);
        }
    }

    boolean canConsume();

    boolean canSupply();

}
