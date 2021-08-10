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

package de.codemakers.base.util;

import de.codemakers.base.multiplets.Doublet;
import de.codemakers.base.util.tough.ToughBiFunction;
import de.codemakers.base.util.tough.ToughPredicate;

import java.util.Arrays;
import java.util.Objects;

public class ArrayUtil {
    
    /**
     * Searches for an Object
     *
     * @param array Array to be searched
     * @param t Object to search for
     * @param <T> Type of the Array and Object
     *
     * @return {@code true} if the Array contains the Object
     */
    public static <T> boolean arrayContains(T[] array, T t) {
        if (array == null || array.length == 0) {
            return false;
        }
        for (T t_ : array) {
            if (Objects.equals(t_, t)) {
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean arrayStartsWith(T[] array, T[] start) {
        if (array == null) {
            return start == null;
        }
        if (array.length == 0) {
            return start.length == 0;
        }
        if (start == null || start.length == 0) {
            return true;
        }
        if (start.length > array.length) {
            return false;
        }
        for (int i = 0; i < start.length; i++) {
            if (!Objects.equals(array[i], start[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> boolean arrayEndsWith(T[] array, T[] end) {
        if (array == null) {
            return end == null;
        }
        if (array.length == 0) {
            return end.length == 0;
        }
        if (end == null || end.length == 0) {
            return true;
        }
        if (end.length > array.length) {
            return false;
        }
        for (int i = end.length - 1; i >= 0; i--) {
            if (!Objects.equals(array[i], end[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> boolean matchAny(T[] array, ToughPredicate<T> predicate) {
        if (array == null || array.length == 0) {
            return false;
        }
        if (predicate == null) {
            return true;
        }
        for (T t : array) {
            if (predicate.testWithoutException(t)) {
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean matchAll(T[] array, ToughPredicate<T> predicate) {
        if (array == null || array.length == 0) {
            return false;
        }
        if (predicate == null) {
            return true;
        }
        for (T t : array) {
            if (!predicate.testWithoutException(t)) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> boolean matchNone(T[] array, ToughPredicate<T> predicate) {
        if (array == null || array.length == 0) {
            return false;
        }
        if (predicate == null) {
            return true;
        }
        for (T t : array) {
            if (predicate.testWithoutException(t)) {
                return false;
            }
        }
        return true;
    }
    
    public static byte[] xorBytes(byte[] bytes_1, byte[] bytes_2) {
        return xorBytes(bytes_1, bytes_2, true);
    }
    
    public static byte[] xorBytes(byte[] bytes_1, byte[] bytes_2, boolean takeLarger) {
        if (bytes_1 == null || bytes_2 == null) {
            return null;
        }
        final byte[] bytes = new byte[takeLarger ? Math.max(bytes_1.length, bytes_2.length) : Math.min(bytes_1.length, bytes_2.length)];
        for (int i = 0; i < bytes.length; i++) {
            if (i >= bytes_2.length) {
                bytes[i] = bytes_1[i];
            } else if (i >= bytes_1.length) {
                bytes[i] = bytes_2[i];
            } else {
                bytes[i] = (byte) (bytes_1[i] ^ bytes_2[i]);
            }
        }
        return bytes;
    }
    
    public static <T> void swapPositions(T[] array, int pos_1, int pos_2) {
        if (array == null || array.length == 0) {
            return;
        }
        if (pos_1 >= array.length || pos_2 >= array.length) {
            throw new IndexOutOfBoundsException();
        }
        if (pos_1 == pos_2) {
            return;
        }
        swapPositionsFast(array, pos_1, pos_2);
    }
    
    public static <T> void swapPositionsFast(T[] array, int pos_1, int pos_2) {
        final T t = array[pos_1];
        array[pos_1] = array[pos_2];
        array[pos_2] = t;
    }
    
    public static <T> void flipArray(T[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        for (int i = 0; i < array.length / 2; i++) {
            swapPositionsFast(array, i, array.length - i - 1);
        }
    }
    
    public static <T, R> R[] convertArray(T[] array, Class<R> clazz, ToughBiFunction<T, Doublet<T[], Integer>, R> converter) {
        return ConvertUtil.convertArray(array, clazz, converter);
    }
    
    public static <T, R> R[] convertArrayFast(T[] array, Class<R> clazz, ToughBiFunction<T[], Integer, R> converter) {
        return ConvertUtil.convertArrayFast(array, clazz, converter);
    }
    
    public static <T> T[] concatArrays(T[]... arrays) {
        int length_max = 0;
        for (T[] array : arrays) {
            length_max += array.length;
        }
        final T[] output = Arrays.copyOf(arrays[0], length_max);
        int index = 0;
        for (T[] array : arrays) {
            System.arraycopy(array, 0, output, index, array.length);
            index += array.length;
        }
        return output;
    }
    
    public static byte[] concatArrays(byte[]... arrays) {
        int length_max = 0;
        for (byte[] array : arrays) {
            length_max += array.length;
        }
        final byte[] output = Arrays.copyOf(arrays[0], length_max);
        int index = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, output, index, array.length);
            index += array.length;
        }
        return output;
    }
    
}
