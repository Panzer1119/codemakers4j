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

package de.codemakers.io.streams;

import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.base.util.tough.ToughPredicate;

import java.util.function.Consumer;
import java.util.function.Function;

public class StreamUtil {
    
    public static <T, R> Function<T, R> createMapper(ToughPredicate<T> predicate, ToughFunction<T, R> functionTrue, ToughFunction<T, R> functionFalse) {
        return (t) -> (predicate.testWithoutException(t) ? functionTrue : functionFalse).applyWithoutException(t);
    }
    
    public static <T> Consumer<T> createUser(ToughPredicate<T> predicate, ToughConsumer<T> consumerTrue, ToughConsumer<T> consumerFalse) {
        return (t) -> (predicate.testWithoutException(t) ? consumerTrue : consumerFalse).acceptWithoutException(t);
    }
    
}
