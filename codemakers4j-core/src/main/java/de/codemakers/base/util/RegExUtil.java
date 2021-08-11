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

import de.codemakers.base.util.tough.ToughPredicate;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegExUtil {
    
    public static final char ESCAPE_CHARACTER = '\\';
    
    public static final String SPECIAL_REG_EX_CHARACTERS_STRING = "<([{\\^-=$!|]})?*+.>";
    public static final char[] SPECIAL_REG_EX_CHARACTERS = SPECIAL_REG_EX_CHARACTERS_STRING.toCharArray();
    public static final String SPECIAL_REG_EX_CHARACTERS_REPLACE_REG_EX = "(\\<)|(\\()|(\\[)|(\\{)|(\\\\)|(\\^)|(\\-)|(\\=)|(\\$)|(\\!)|(\\|)|(\\])|(\\})|(\\))|(\\?)|(\\*)|(\\+)|(\\.)|(\\>)";
    public static final Pattern SPECIAL_REG_EX_CHARACTERS_REPLACE_PATTERN = Pattern.compile(SPECIAL_REG_EX_CHARACTERS_REPLACE_REG_EX);
    public static final String REPLACEMENT_STRING = "\\\\$0";
    
    public static String escapeToRegEx(String text) {
        if (text == null) {
            return null;
        }
        return SPECIAL_REG_EX_CHARACTERS_REPLACE_PATTERN.matcher(text).replaceAll(REPLACEMENT_STRING);
    }
    
    public static ToughPredicate<String> regExMatchToPredicate(String regEx) {
        if (regEx == null) {
            return Objects::nonNull;
        }
        return patternMatchToPredicate(regEx.isEmpty() ? null : Pattern.compile(regEx));
    }
    
    public static ToughPredicate<String> patternMatchToPredicate(Pattern pattern) {
        if (pattern == null) {
            return Objects::nonNull;
        }
        return (string) -> string != null && pattern.matcher(string).matches();
    }
    
    public static ToughPredicate<String> regExFindToPredicate(String regEx) {
        if (regEx == null) {
            return Objects::nonNull;
        }
        return patternFindToPredicate(regEx.isEmpty() ? null : Pattern.compile(regEx));
    }
    
    public static ToughPredicate<String> patternFindToPredicate(Pattern pattern) {
        if (pattern == null) {
            return Objects::nonNull;
        }
        return (string) -> string != null && pattern.matcher(string).find();
    }
    
}
