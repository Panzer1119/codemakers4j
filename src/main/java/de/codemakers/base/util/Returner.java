/*
 *     Copyright 2018 - 2020 Paul Hagedorn (Panzer1119)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package de.codemakers.base.util;

import de.codemakers.base.util.tough.ToughSupplier;

public class Returner<T> {
    
    private T value;
    
    public Returner(T value) {
        this.value = value;
    }
    
    public static final Returner<Integer> of(Integer value) {
        return new Returner<>(value);
    }
    
    public static final Returner<Float> of(Float value) {
        return new Returner<>(value);
    }
    
    public static final Returner<Boolean> of(Boolean value) {
        return new Returner<>(value);
    }
    
    public static final Returner<Object> of(Object value) {
        return new Returner<>(value);
    }
    
    public static final Returner<String> of(String value) {
        return new Returner<>(value);
    }
    
    public static final Returner<Short> of(Short value) {
        return new Returner<>(value);
    }
    
    public static final Returner<Long> of(Long value) {
        return new Returner<>(value);
    }
    
    public static final Returner<Character> of(Character value) {
        return new Returner<>(value);
    }
    
    public static final Returner<Byte> of(Byte value) {
        return new Returner<>(value);
    }
    
    public static final Returner<Double> of(Double value) {
        return new Returner<>(value);
    }
    
    public final T getValue() {
        return value;
    }
    
    public final Returner setValue(T value) {
        this.value = value;
        return this;
    }
    
    public final T or(T other) {
        return (value != null) ? value : other;
    }
    
    public final T or(ToughSupplier<T> supplier) {
        return (value != null || supplier == null) ? value : supplier.getWithoutException();
    }
    
}
