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

package de.codemakers.math.numbers;

import de.codemakers.base.util.Require;
import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.math.AbstractNumber;

import java.util.Objects;

public class IntegerNumber extends AbstractNumber {
    
    public static final IntegerNumber MINUS_ONE = new IntegerNumber(-1);
    public static final IntegerNumber ZERO = new IntegerNumber(0);
    public static final IntegerNumber ONE = new IntegerNumber(1);
    
    private Integer value = null;
    
    public IntegerNumber(Integer value) {
        this.value = value;
    }
    
    @Override
    public AbstractNumber add(AbstractNumber number) {
        return null;
    }
    
    @Override
    public AbstractNumber subtract(AbstractNumber number) {
        return null;
    }
    
    @Override
    public AbstractNumber multiply(AbstractNumber number) {
        return null;
    }
    
    @Override
    public AbstractNumber divide(AbstractNumber number) {
        return null;
    }
    
    @Override
    public AbstractNumber pow(AbstractNumber number) {
        return null;
    }
    
    @Override
    public AbstractNumber mod(AbstractNumber number) {
        return null;
    }
    
    @Override
    public AbstractNumber negate() {
        return null;
    }
    
    @Override
    public AbstractNumber inverse() {
        return null;
    }
    
    @Override
    public AbstractNumber sgn() {
        return null;
    }
    
    @Override
    public double toDouble() {
        return value;
    }
    
    @Override
    public float toFloat() {
        return value;
    }
    
    @Override
    public long toLong() {
        return value;
    }
    
    @Override
    public int toInt() {
        return value;
    }
    
    @Override
    public short toShort() {
        return (short) toInt();
    }
    
    @Override
    public byte toByte() {
        return (byte) toInt();
    }
    
    @Override
    public Copyable copy() {
        return new IntegerNumber(value);
    }
    
    @Override
    public void set(Copyable copyable) {
        final IntegerNumber integerNumber = Require.clazz(copyable, IntegerNumber.class);
        if (integerNumber != null) {
            this.value = integerNumber.value;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        final IntegerNumber that = (IntegerNumber) o;
        return Objects.equals(value, that.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return "IntegerNumber{" + "value=" + value + '}';
    }
    
}
