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

import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookup;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    
    public static final String REGEX_ANONYMOUS_CLASS_NAME = "(.+\\.)?(.+)\\$(\\d+)";
    public static final Pattern PATTERN_ANONYMOUS_CLASS_NAME = Pattern.compile(REGEX_ANONYMOUS_CLASS_NAME);
    
    public static final String REG_EX_ESCAPE_NEEDING_CHARS_STRING = "<([{\\^-=$!|]})?*+.>";
    public static final char[] REG_EX_ESCAPE_NEEDING_CHARS = REG_EX_ESCAPE_NEEDING_CHARS_STRING.toCharArray();
    
    public static final boolean isEmpty(String string) {
        return string.isEmpty();
    }
    
    public static final boolean isNotEmpty(String string) {
        return !string.isEmpty();
    }
    
    public static int count(String string, String text) {
        if (string == null) {
            return -1;
        }
        if (string.isEmpty() || !string.contains(text)) {
            return 0;
        }
        int count = 0;
        int lastIndex = -text.length();
        while ((lastIndex = string.indexOf(text, lastIndex + text.length())) != -1) {
            count++;
        }
        return count;
    }
    
    public static String classToSimpleName(Class<?> clazz) {
        if (clazz == null) {
            return "" + null;
        }
        if (clazz.isAnonymousClass()) {
            final Matcher matcher = PATTERN_ANONYMOUS_CLASS_NAME.matcher(clazz.getName());
            if (matcher.matches()) {
                return matcher.group(2);
            }
        }
        return clazz.getSimpleName();
    }
    
    public static String toString(Object object) {
        return "" + object;
    }
    
    public static String toStringOrDefault(Object object, String defaultValue) {
        return object == null ? defaultValue : "" + object;
    }
    
    /**
     * Take a look at {@link java.util.regex.Pattern#quote(String)}
     */
    @Deprecated
    public static String escapePlainStringToRegExMatchString(final String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String output = input;
        for (char c : REG_EX_ESCAPE_NEEDING_CHARS) {
            output = output.replaceAll("(\\" + c + ")", "\\\\$1");
        }
        return output;
    }
    
    /**
     * Take a look at {@link java.util.regex.Matcher#quoteReplacement(String)}
     */
    @Deprecated
    public static String escapePlainStringToRegExReplacementString(final String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String output = input;
        output = output.replaceAll("\\\\", "\\\\\\\\");
        output = output.replaceAll("\\$", "\\\\$");
        return output;
    }
    
    public static abstract class MapLookup<T> implements StringLookup {
        
        private final Map<T, Object> values;
        
        public MapLookup() {
            this(new HashMap<>());
        }
        
        public MapLookup(Map<T, Object> values) {
            this.values = values;
        }
        
        public int size() {
            return values.size();
        }
        
        public boolean isEmpty() {
            return values.isEmpty();
        }
        
        public boolean containsKey(T key) {
            return values.containsKey(key);
        }
        
        public boolean containsValue(Object value) {
            return values.containsValue(value);
        }
        
        public Object get(T key) {
            return values.get(key);
        }
        
        public Object put(T key, Object value) {
            return values.put(key, value);
        }
        
        public Object remove(T key) {
            return values.remove(key);
        }
        
        public void putAll(Map<? extends T, ?> m) {
            values.putAll(m);
        }
        
        public void clear() {
            values.clear();
        }
        
        public Set<T> keySet() {
            return values.keySet();
        }
        
        public Collection<Object> values() {
            return values.values();
        }
        
        public Set<Map.Entry<T, Object>> entrySet() {
            return values.entrySet();
        }
        
        @Override
        public boolean equals(Object o) {
            return values.equals(o);
        }
        
        @Override
        public int hashCode() {
            return values.hashCode();
        }
        
        protected abstract String toString(T t);
        
        protected abstract T fromString(String key);
        
        @Override
        public String lookup(String key) {
            return toStringOrDefault(get(fromString(key)), key);
        }
        
        @Override
        public String toString() {
            return "MapLookup{" + "values=" + values + '}';
        }
        
    }
    
    public static class StringMapLookup extends MapLookup<String> {
        
        public StringMapLookup() {
        }
        
        public StringMapLookup(Map<String, Object> values) {
            super(values);
        }
        
        @Override
        protected String toString(String string) {
            return string;
        }
        
        @Override
        protected String fromString(String key) {
            return key;
        }
        
    }
    
    public static String escapeStringSubstitutorVariableCalls(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.replaceAll(STRING_SUBSTITUTOR_TO_REPLACE, STRING_SUBSTITUTOR_REPLACEMENT);
    }
    
    public static final String STRING_SUBSTITUTOR_TO_REPLACE = "([^\\" + StringSubstitutor.DEFAULT_ESCAPE + "]\\" + StringSubstitutor.DEFAULT_ESCAPE + ")\\" + StringSubstitutor.DEFAULT_VAR_START.substring(1);
    public static final String STRING_SUBSTITUTOR_REPLACEMENT = "$1\\" + StringSubstitutor.DEFAULT_VAR_START;
    
}
