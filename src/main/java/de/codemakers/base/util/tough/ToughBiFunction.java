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

@FunctionalInterface
public interface ToughBiFunction<T, U, R, E extends Exception> extends Tough<T, R, E> {
    
    R apply(T t, U u) throws E;
    
    default R apply(T t, U u, ToughConsumer<E, Exception> failure) {
        try {
            return apply(t, u);
        } catch (Exception ex) {
            handleException(ex, failure);
            return null;
        }
    }
    
    default R applyWithoutException(T t, U u) {
        return apply(t, u, null);
    }
    
    @Override
    default R action(T t) throws E {
        return apply(t, null);
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
