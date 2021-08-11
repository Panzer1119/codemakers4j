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

package de.codemakers.security.util;

import java.util.Base64;
import java.util.Random;

public class RandomUtil {
    
    public static final String NUMERALS_STRING = "0123456789";
    public static final String ALPHABET_LOWER_CASE_STRING = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALPHABET_UPPER_CASE_STRING = ALPHABET_LOWER_CASE_STRING.toUpperCase();
    public static final String ALPHABET_BOTH_CASES_STRING = ALPHABET_LOWER_CASE_STRING + ALPHABET_UPPER_CASE_STRING;
    public static final String ALPHA_NUMERICALS_STRING = ALPHABET_BOTH_CASES_STRING + NUMERALS_STRING;
    public static final char[] ALPHA_NUMERICALS = ALPHA_NUMERICALS_STRING.toCharArray();
    
    public static final String randomAlphaNumericalString(int length) {
        return new String(randomAlphaNumericalChars(length));
    }
    
    public static final String randomAlphaNumericalString(int length, char[] chars) {
        return new String(randomAlphaNumericalChars(length, chars));
    }
    
    public static final String randomAlphaNumericalString(int length, Random random) {
        return new String(randomAlphaNumericalChars(length, random));
    }
    
    public static final String randomAlphaNumericalString(int length, Random random, char[] chars) {
        return new String(randomAlphaNumericalChars(length, random, chars));
    }
    
    public static final char[] randomAlphaNumericalChars(int length) {
        return randomAlphaNumericalChars(length, EasyCryptUtil.getSecurestRandom());
    }
    
    public static final char[] randomAlphaNumericalChars(int length, char[] chars) {
        return randomAlphaNumericalChars(length, EasyCryptUtil.getSecurestRandom(), chars);
    }
    
    public static final char[] randomAlphaNumericalChars(int length, Random random) {
        return randomAlphaNumericalChars(length, random, ALPHA_NUMERICALS);
    }
    
    public static final char[] randomAlphaNumericalChars(int length, Random random, char[] chars) {
        if (length < 0 || random == null) {
            return null;
        }
        final char[] randomChars = new char[length];
        for (int i = 0; i < length; i++) {
            randomChars[i] = nextRandomChar(chars, random);
        }
        return randomChars;
    }
    
    protected static final char nextRandomChar(char[] chars, Random random) {
        if (chars == null || chars.length == 0 || random == null) {
            return 0;
        }
        return chars[random.nextInt(chars.length)];
    }
    
    public static final byte[] randomBytes(int length) {
        return randomBytes(length, EasyCryptUtil.getSecurestRandom());
    }
    
    public static final byte[] randomBytes(int length, Random random) {
        if (length < 0 || random == null) {
            return null;
        }
        final byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return bytes;
    }
    
    public static final String randomBase64String(int length) {
        return randomBase64String(length, EasyCryptUtil.getSecurestRandom());
    }
    
    public static final String randomBase64String(int length, Random random) {
        if (length < 0 || random == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(randomBytes(length));
    }
    
    public static final String randomUrlBase64String(int length) {
        return randomUrlBase64String(length, EasyCryptUtil.getSecurestRandom());
    }
    
    public static final String randomUrlBase64String(int length, Random random) {
        if (length < 0 || random == null) {
            return null;
        }
        return Base64.getUrlEncoder().encodeToString(randomBytes(length));
    }
    
    public static final String randomMimeBase64String(int length) {
        return randomMimeBase64String(length, EasyCryptUtil.getSecurestRandom());
    }
    
    public static final String randomMimeBase64String(int length, Random random) {
        if (length < 0 || random == null) {
            return null;
        }
        return Base64.getMimeEncoder().encodeToString(randomBytes(length));
    }
    
}
