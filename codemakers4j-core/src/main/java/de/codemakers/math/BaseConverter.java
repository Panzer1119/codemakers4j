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

package de.codemakers.math;

import de.codemakers.base.multiplets.Doublet;

import java.util.Objects;
import java.util.function.Function;

public class BaseConverter {
    
    public static final String CHARSET_BASE_2_STRING = "01";
    public static final char[] CHARSET_BASE_2 = CHARSET_BASE_2_STRING.toCharArray();
    
    public static final String CHARSET_BASE_3_STRING = "012";
    public static final char[] CHARSET_BASE_3 = CHARSET_BASE_3_STRING.toCharArray();
    
    public static final String CHARSET_BASE_4_STRING = "0123";
    public static final char[] CHARSET_BASE_4 = CHARSET_BASE_4_STRING.toCharArray();
    
    public static final String CHARSET_BASE_5_STRING = "01234";
    public static final char[] CHARSET_BASE_5 = CHARSET_BASE_5_STRING.toCharArray();
    
    public static final String CHARSET_BASE_6_STRING = "012345";
    public static final char[] CHARSET_BASE_6 = CHARSET_BASE_6_STRING.toCharArray();
    
    public static final String CHARSET_BASE_7_STRING = "0123456";
    public static final char[] CHARSET_BASE_7 = CHARSET_BASE_7_STRING.toCharArray();
    
    public static final String CHARSET_BASE_8_STRING = "01234567";
    public static final char[] CHARSET_BASE_8 = CHARSET_BASE_8_STRING.toCharArray();
    
    public static final String CHARSET_BASE_9_STRING = "012345678";
    public static final char[] CHARSET_BASE_9 = CHARSET_BASE_9_STRING.toCharArray();
    
    public static final String CHARSET_BASE_10_STRING = "0123456789";
    public static final char[] CHARSET_BASE_10 = CHARSET_BASE_10_STRING.toCharArray();
    
    public static final String CHARSET_BASE_11_STRING = "0123456789A";
    public static final char[] CHARSET_BASE_11 = CHARSET_BASE_11_STRING.toCharArray();
    
    public static final String CHARSET_BASE_12_STRING = "0123456789AB";
    public static final char[] CHARSET_BASE_12 = CHARSET_BASE_12_STRING.toCharArray();
    
    public static final String CHARSET_BASE_13_STRING = "0123456789ABC";
    public static final char[] CHARSET_BASE_13 = CHARSET_BASE_13_STRING.toCharArray();
    
    public static final String CHARSET_BASE_14_STRING = "0123456789ABCD";
    public static final char[] CHARSET_BASE_14 = CHARSET_BASE_14_STRING.toCharArray();
    
    public static final String CHARSET_BASE_15_STRING = "0123456789ABCDE";
    public static final char[] CHARSET_BASE_15 = CHARSET_BASE_15_STRING.toCharArray();
    
    public static final String CHARSET_BASE_16_STRING = "0123456789ABCDEF";
    public static final char[] CHARSET_BASE_16 = CHARSET_BASE_16_STRING.toCharArray();
    
    public static final String CHARSET_BASE_36_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final char[] CHARSET_BASE_36 = CHARSET_BASE_36_STRING.toCharArray();
    
    public static final String CHARSET_BASE_62_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final char[] CHARSET_BASE_62 = CHARSET_BASE_62_STRING.toCharArray();
    
    public static final char[] CHARSET_BASE_94_CUSTOM = generateCharset(94, (i) -> (char) (i + 33));
    
    public static final char[][] CHARSETS = {CHARSET_BASE_2, CHARSET_BASE_3, CHARSET_BASE_4, CHARSET_BASE_5, CHARSET_BASE_6, CHARSET_BASE_7, CHARSET_BASE_8, CHARSET_BASE_9, CHARSET_BASE_10, CHARSET_BASE_11, CHARSET_BASE_12, CHARSET_BASE_13, CHARSET_BASE_14, CHARSET_BASE_15, CHARSET_BASE_16, CHARSET_BASE_94_CUSTOM};
    public static final int[] CHARSET_LENGTHS = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 94};
    
    public static char[] generateCharset(int length, Function<Integer, Character> generator) {
        Objects.requireNonNull(generator);
        final char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = generator.apply(i);
        }
        return chars;
    }
    
    public static char[] getChars(int radix) {
        for (int i = 0; i < CHARSET_LENGTHS.length; i++) {
            if (radix == CHARSET_LENGTHS[i]) {
                return CHARSETS[i];
            }
        }
        return generateCharset(radix, (i) -> (char) (i + 33));
    }
    
    public static String convertDecimalToBase(long number, int radix) {
        if (radix < 2) {
            return null;
        }
        return convertDecimalToBase(number, getChars(radix));
    }
    
    public static String convertDecimalToBase(long number, char[] charset) {
        final int radix = charset.length;
        final boolean negative = number < 0;
        if (negative) {
            number *= -1;
        }
        String output = "";
        while (number > 0) {
            output = charset[(int) (number % radix)] + output;
            number /= radix;
        }
        return (negative ? "-" : "") + output;
    }
    
    public static Doublet<int[], Boolean> convertDecimalToBaseArray(long number, long radix) {
        final boolean negative = number < 0;
        if (negative) {
            number *= -1;
        }
        final int[] digits = new int[(int) (Math.log(number) / Math.log(radix)) + 1];
        int i = digits.length - 1;
        while (number > 0) {
            digits[i--] = (int) (number % radix);
            number /= radix;
        }
        return new Doublet<>(digits, negative);
    }
    
}
