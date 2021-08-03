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
public interface ToughBiPredicate<T, U, E extends Exception> extends Tough<T, Boolean, E> {
    
    Boolean test(T t, U u) throws E;
    
    default Boolean test(T t, U u, ToughConsumer<E, Exception> failure) {
        return test(t, u, false, failure);
    }
    
    default Boolean test(T t, U u, boolean onError, ToughConsumer<E, Exception> failure) {
        try {
            return test(t, u);
        } catch (Exception ex) {
            handleException(ex, failure);
            return onError;
        }
    }
    
    default ToughBiPredicate<T, U, E> negate() {
        return (t, u) -> !test(t, u);
    }
    
    default Boolean testWithoutException(T t, U u) {
        return test(t, u, null);
    }
    
    default Boolean testWithoutException(T t, U u, boolean onError) {
        return test(t, u, onError, null);
    }
    
    @Override
    default Boolean action(T t) throws E {
        return test(t, null);
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
