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

package de.codemakers.base.entities;

import de.codemakers.base.exceptions.NotImplementedRuntimeException;
import de.codemakers.base.multiplets.*;
import de.codemakers.base.util.tough.ToughBiFunction;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.IntFunction;

public class LongArray<T> {
    
    private final T[][] array;
    private final T[][] array_2;
    
    public LongArray(long length, IntFunction<T[][]> outerArrayGenerator, IntFunction<T[]> innerArrayGenerator) {
        final long outerLength = ((length - 1) / Integer.MAX_VALUE) + 1;
        final long left = length % Integer.MAX_VALUE;
        System.out.println("length=" + length + ", outerLength=" + outerLength + ", left=" + left);
        this.array = outerArrayGenerator.apply((int) outerLength);
        for (long l = 0; l < outerLength - 1; l++) {
            this.array[(int) l] = innerArrayGenerator.apply(Integer.MAX_VALUE);
        }
        this.array[(int) (outerLength - 1)] = innerArrayGenerator.apply((int) left);
        this.array_2 = null;
        /*
        final long length_1 = length / Integer.MAX_VALUE + 1;
        this.array = arrayGenerator.apply((int) Math.min(length_1, Integer.MAX_VALUE));
        this.array_2 = length_1 > Integer.MAX_VALUE ? arrayGenerator.apply((int) (length_1 - Integer.MAX_VALUE)) : null;
        */
        //getClass().getMethod().invoke();
    }
    
    public static final <T extends Singlet, R> ToughBiFunction<Object, T, R> methodToFunction(Class<?> clazz, String name, Class<?>... parameters) throws Exception {
        if (parameters.length > 10) {
            throw new NotImplementedRuntimeException();
        }
        final Method method = clazz.getMethod(name, parameters);
        switch (parameters.length) {
            case 0:
                return (object, t) -> (R) method.invoke(object);
            case 1:
                return (object, t) -> (R) method.invoke(object, t.getA());
            case 2:
                return (object, t) -> {
                    final Doublet<?, ?> doublet = (Doublet<?, ?>) t;
                    return (R) method.invoke(object, doublet.getA(), doublet.getB());
                };
            case 3:
                return (object, t) -> {
                    final Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) t;
                    return (R) method.invoke(object, triplet.getA(), triplet.getB(), triplet.getC());
                };
            case 4:
                return (object, t) -> {
                    final Quadruplet<?, ?, ?, ?> quadruplet = (Quadruplet<?, ?, ?, ?>) t;
                    return (R) method.invoke(object, quadruplet.getA(), quadruplet.getB(), quadruplet.getC(), quadruplet.getD());
                };
            case 5:
                return (object, t) -> {
                    final Quintuplet<?, ?, ?, ?, ?> quintuplet = (Quintuplet<?, ?, ?, ?, ?>) t;
                    return (R) method.invoke(object, quintuplet.getA(), quintuplet.getB(), quintuplet.getC(), quintuplet.getD(), quintuplet.getE());
                };
            case 6:
                return (object, t) -> {
                    final Sextuplet<?, ?, ?, ?, ?, ?> sextuplet = (Sextuplet<?, ?, ?, ?, ?, ?>) t;
                    return (R) method.invoke(object, sextuplet.getA(), sextuplet.getB(), sextuplet.getC(), sextuplet.getD(), sextuplet.getE(), sextuplet.getF());
                };
            case 7:
                return (object, t) -> {
                    final Septuplet<?, ?, ?, ?, ?, ?, ?> septuplet = (Septuplet<?, ?, ?, ?, ?, ?, ?>) t;
                    return (R) method.invoke(object, septuplet.getA(), septuplet.getB(), septuplet.getC(), septuplet.getD(), septuplet.getE(), septuplet.getF(), septuplet.getG());
                };
            case 8:
                return (object, t) -> {
                    final Octuplet<?, ?, ?, ?, ?, ?, ?, ?> octuplet = (Octuplet<?, ?, ?, ?, ?, ?, ?, ?>) t;
                    return (R) method.invoke(object, octuplet.getA(), octuplet.getB(), octuplet.getC(), octuplet.getD(), octuplet.getE(), octuplet.getF(), octuplet.getG(), octuplet.getH());
                };
            case 9:
                return (object, t) -> {
                    final Nonuplet<?, ?, ?, ?, ?, ?, ?, ?, ?> nonuplet = (Nonuplet<?, ?, ?, ?, ?, ?, ?, ?, ?>) t;
                    return (R) method.invoke(object, nonuplet.getA(), nonuplet.getB(), nonuplet.getC(), nonuplet.getD(), nonuplet.getE(), nonuplet.getF(), nonuplet.getG(), nonuplet.getH(), nonuplet.getI());
                };
            case 10:
                return (object, t) -> {
                    final Decuplet<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> decuplet = (Decuplet<?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) t;
                    return (R) method.invoke(object, decuplet.getA(), decuplet.getB(), decuplet.getC(), decuplet.getD(), decuplet.getE(), decuplet.getF(), decuplet.getG(), decuplet.getH(), decuplet.getI(), decuplet.getJ());
                };
            default:
                throw new NotImplementedRuntimeException();
        }
    }
    
    public static final void main(String[] args) throws Exception {
        System.out.println("Test");
        System.out.println("Integer.MAX_VALUE               =" + Integer.MAX_VALUE);
        System.out.println("Math.pow(2, 31)                 =" + ((long) Math.pow(2, 31)));
        System.out.println("Math.pow(2, 31)                 =" + ((long) Math.pow(2, 31) - 1));
        System.out.println("Math.pow(2, 31)                 =" + Math.pow(2, 31));
        System.out.println("Long.MAX_VALUE                  =" + Long.MAX_VALUE);
        System.out.println("Math.pow(2, 63)                 =" + ((long) Math.pow(2, 63)));
        System.out.println("Math.pow(2, 63)                 =" + ((long) (Math.pow(2, 63) - 1)));
        System.out.println("Math.pow(2, 63)                 =" + Math.pow(2, 63));
        System.out.println("Long.MAX_VALUE/Integer.MAX_VALUE=" + (Long.MAX_VALUE / Integer.MAX_VALUE));
        System.out.println("Long.MAX_VALUE%Integer.MAX_VALUE=" + (Long.MAX_VALUE % Integer.MAX_VALUE));
        //final LongArray<Object> longArray = new LongArray<>(((long) Integer.MAX_VALUE) * 2 + 1, (i) -> new Object[i][0], (i) -> new Object[i]);
        //System.out.println(longArray);
        final ToughBiFunction<Object, Doublet<Integer, Long>, String> function = methodToFunction(LongArray.class, "aAndB", Integer.class, Long.class);
        final String output = function.apply(null, new Doublet<>(45, ((long) Integer.MAX_VALUE) + 345));
        System.out.println("function.apply=\"" + output + "\"");
    }
    
    public static String aAndB(Integer a, Long b) {
        return a + " + " + b + " = " + (((long) a) + b);
    }
    
    @Override
    public String toString() {
        return String.format("%s{array=%s%s, array_2=%s%s}", getClass().getSimpleName(), Arrays.toString(array), array == null ? "" : String.format(" (array.length=%d)", array.length), Arrays.toString(array_2), array_2 == null ? "" : String.format(" (array_2.length=%d)", array_2.length));
    }
    
}
