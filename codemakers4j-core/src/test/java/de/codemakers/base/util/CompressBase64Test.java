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

import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompressBase64Test {
    
    public static final void main(String[] args) {
        final String string_1 = "This is a test string                   00000                   with soooooome multiple occuring Lettters";
        System.out.println("string_1   =" + string_1);
        final byte[] bytes_1 = Base64.getEncoder().encode(string_1.getBytes());
        int i = 0;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 99;
        bytes_1[i++] = 51;
        i = 40;
        bytes_1[i++] = 104;
        bytes_1[i++] = 104;
        bytes_1[i++] = 104;
        bytes_1[i++] = 104;
        bytes_1[i++] = 104;
        bytes_1[i++] = 104;
        bytes_1[i++] = 103;
        System.out.println("bytes_1    =" + Arrays.toString(bytes_1));
        //final String string_1_e = Base64.getEncoder().encodeToString(string_1.getBytes());
        final String string_1_e = new String(bytes_1);
        System.out.println("string_1_e =" + string_1_e);
        final String string_1_d = new String(Base64.getDecoder().decode(string_1_e.getBytes()));
        System.out.println("string_1_d =" + string_1_d);
        final String string_1_c = compressBase64(string_1_e);
        System.out.println("string_1_c =" + string_1_c);
        final String string_1_dc = decompressBase64(string_1_c);
        System.out.println("string_1_dc=" + string_1_dc);
    }
    
    public static String compressBase64(String base64Encoded) {
        final char[] chars = base64Encoded.toCharArray();
        String output = "";
        char c_ = ' ';
        int count = 1;
        int missing = -1;
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            if (c == '=') {
                missing = i;
                break;
            }
            if (c_ != c) {
                if (c_ != ' ') {
                    if (Math.log10(count) + 1 + 2 < count) {
                        output += c_;
                        //if (count > 1) {
                        output += String.format("{%d}", count);
                        //}
                    } else {
                        for (int i_ = 0; i_ < count; i_++) {
                            output += c_;
                        }
                    }
                }
                c_ = c;
                count = 1;
            } else {
                count++;
            }
        }
        if (count >= 1) {
            output += c_;
            if (count > 1) {
                output += String.format("{%d}", count);
            }
        }
        if (missing >= 0) {
            for (int i_ = missing; i_ < chars.length; i_++) {
                output += '=';
            }
        }
        return output;
    }
    
    public static final String REG_EX = "([A-Za-z0-9+/])\\{(\\d+)\\}";
    public static final Pattern PATTERN = Pattern.compile(REG_EX);
    
    public static String decompressBase64(final String base64EncodedCompressed) {
        String temp = base64EncodedCompressed;
        Matcher matcher = PATTERN.matcher(temp);
        while (matcher.find()) {
            final String c = matcher.group(1);
            String expanded = "";
            final int count = Integer.parseInt(matcher.group(2));
            for (int i = 0; i < count; i++) {
                expanded += c;
            }
            temp = temp.substring(0, matcher.start()) + expanded + temp.substring(matcher.end());
            matcher = PATTERN.matcher(temp);
        }
        return temp;
    }
    
}
