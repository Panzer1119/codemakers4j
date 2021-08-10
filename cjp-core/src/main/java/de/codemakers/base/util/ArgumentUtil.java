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

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArgumentUtil {
    
    public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
    public static final char DEFAULT_QUOTE_CHARACTER = '"';
    public static final char DEFAULT_DELIMITER_CHARACTER = ' ';
    
    public static List<String> parseArguments(String arguments_string) {
        return parseArguments(arguments_string, true);
    }
    
    public static List<String> parseArguments(String arguments_string, boolean removeEmpty) {
        return parseArguments(arguments_string, DEFAULT_ESCAPE_CHARACTER, DEFAULT_QUOTE_CHARACTER, DEFAULT_DELIMITER_CHARACTER);
    }
    
    public static List<String> parseArguments(String arguments_string, char escape_char, char quote_char, char delimiter_char) {
        return parseArguments(arguments_string, true, escape_char, quote_char, delimiter_char);
    }
    
    public static List<String> parseArguments(String arguments_string, boolean removeEmpty, char escape_char, char quote_char, char delimiter_char) {
        final List<String> arguments = new ArrayList<>();
        String temp = null;
        boolean inQuote = false;
        final char[] chars = arguments_string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            if (c == escape_char) {
                i++;
                if (i >= chars.length) {
                    throw new IndexOutOfBoundsException("Escape character is not escaping anything");
                }
                temp = addToTemp(temp, chars[i]);
            } else if (c == quote_char) {
                inQuote = !inQuote;
            } else if (c == delimiter_char && !inQuote) {
                arguments.add(temp);
                temp = null;
            } else {
                temp = addToTemp(temp, c);
            }
        }
        if (inQuote) {
            throw new InvalidParameterException("Missing closing quotes");
        }
        if (temp != null) {
            arguments.add(temp);
        }
        if (removeEmpty) {
            arguments.removeIf(Objects::isNull);
        }
        return arguments;
    }
    
    protected static String addToTemp(String temp, char c) {
        return temp == null ? "" + c : temp + c;
    }
    
}
