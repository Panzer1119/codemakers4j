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

public class DoubleNumber extends AbstractNumber {
    
    private Double value = null;
    
    public DoubleNumber(Double value) {
        this.value = value;
    }
    
    @Override
    public AbstractNumber add(AbstractNumber number) {
        return new DoubleNumber(value + number.toDouble());
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
        return new DoubleNumber(-value);
    }
    
    @Override
    public AbstractNumber inverse() {
        return new DoubleNumber(1.0 / value);
    }
    
    @Override
    public AbstractNumber sgn() {
        return value > 0 ? IntegerNumber.ONE : value == 0 ? IntegerNumber.ZERO : IntegerNumber.MINUS_ONE;
    }
    
    @Override
    public double toDouble() {
        return value;
    }
    
    @Override
    public float toFloat() {
        return (float) toDouble();
    }
    
    @Override
    public long toLong() {
        return (long) toDouble();
    }
    
    @Override
    public int toInt() {
        return (int) toDouble();
    }
    
    @Override
    public short toShort() {
        return (short) toDouble();
    }
    
    @Override
    public byte toByte() {
        return (byte) toDouble();
    }
    
    @Override
    public DoubleNumber copy() {
        return new DoubleNumber(value);
    }
    
    @Override
    public void set(Copyable copyable) {
        final DoubleNumber doubleNumber = Require.clazz(copyable, DoubleNumber.class);
        if (doubleNumber != null) {
            this.value = doubleNumber.value;
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
        final DoubleNumber that = (DoubleNumber) o;
        return Objects.equals(value, that.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return "DoubleNumber{" + "value=" + value + '}';
    }
    
}
