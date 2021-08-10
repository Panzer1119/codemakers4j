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

package de.codemakers.lua.parser;

import de.codemakers.base.exceptions.CJPRuntimeException;
import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.base.os.OSUtil;
import de.codemakers.base.util.ArrayUtil;
import de.codemakers.lua.LuaContext;
import de.codemakers.lua.LuaFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LuaParser {
    
    public static final String NEW_LINE_SEPARATOR_REG_EX = "\n";
    public static final char NEW_LINE_SEPARATOR_CHARACTER = '\n';
    public static final char ESCAPE_CHARACTER = '\\';
    public static final char IN_STRING_CHARACTER = '"';
    public static final Character[] NON_ESCAPABLE_CHARACTERS = new Character[] {'n', 't'};
    
    public static final String LUA_FUNCTION_HEAD_REG_EX = "function\\s*([a-zA-Z_][a-zA-Z0-9_-]*)\\s*\\(((?:[a-zA-Z_][a-zA-Z0-9_-]*(?:,\\s*(?:[a-zA-Z_][a-zA-Z0-9_-]*))*)?)\\)";
    public static final Pattern LUA_FUNCTION_HEAD_PATTERN = Pattern.compile(LUA_FUNCTION_HEAD_REG_EX);
    
    public List<String> parseLua(String lua) {
        lua = lua.replaceAll(OSUtil.WINDOWS_HELPER.getLineSeparator(), NEW_LINE_SEPARATOR_REG_EX);
        final char[] chars = lua.toCharArray();
        final List<String> strings = new ArrayList<>();
        boolean inString = false;
        String temp = "";
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            if (c == ESCAPE_CHARACTER) {
                final Character c_2 = chars[i + 1];
                if (ArrayUtil.arrayContains(NON_ESCAPABLE_CHARACTERS, c_2)) {
                    temp += c;
                }
                temp += c_2;
                i++;
                continue;
            }
            if (inString) {
                temp += c;
                if (c == IN_STRING_CHARACTER) {
                    inString = false;
                }
            } else {
                if (c == NEW_LINE_SEPARATOR_CHARACTER) {
                    strings.add(temp);
                    temp = "";
                } else {
                    temp += c;
                }
                if (c == IN_STRING_CHARACTER) {
                    inString = true;
                }
            }
        }
        if (!temp.isEmpty()) {
            strings.add(temp);
            temp = "";
        }
        return strings;
    }
    
    public LuaContext createLuaContext(List<String> strings) {
        Objects.requireNonNull(strings);
        final LuaContext luaContext = new LuaContext();
        String name = null;
        String[] parameters = null;
        LuaFunction luaFunction = null;
        String temp = null;
        boolean isInFunction = false;
        int depth_end = 0;
        for (String string : strings) {
            string = string.trim();
            final Matcher matcher_function = LUA_FUNCTION_HEAD_PATTERN.matcher(string);
            if (matcher_function.matches()) {
                if (isInFunction) {
                    throw new NotYetImplementedRuntimeException("You may not create a function in another function");
                }
                isInFunction = true;
                depth_end--;
                name = matcher_function.group(1);
                parameters = matcher_function.group(2).split(",");
                for (int i = 0; i < parameters.length; i++) {
                    parameters[i] = parameters[i].trim();
                }
                System.out.println("function.name=" + name);
                System.out.println("function.parameters=" + Arrays.toString(parameters));
                /*
                string = string.substring("function".length());
                string = string.trim();
                final int i_1 = string.indexOf(" ");
                final int i_2 = string.indexOf("(");
                final int i_3 = Math.min(i_1, i_2);
                string = string.substring(0, i_3);
                System.out.println("function.name=" + string);
                name = string;
                */
                /*
                luaFunction = new LuaFunction(name) {
                    @Override
                    protected Object[] callIntern(LuaContext luaContext, Object... parameters) {
                        return new Object[0];
                    }
                };
                */
            } else if (string.startsWith("if")) {
                depth_end--;
            } else if (string.startsWith("for")) {
                depth_end--;
            } else if (string.startsWith("while")) {
                depth_end--;
            }
            if (string.equals("end")) {
                if (!isInFunction && depth_end == 0) {
                    throw new CJPRuntimeException("You may not end no block");
                }
                isInFunction = false;
                depth_end++;
            }
        }
        return luaContext;
    }
    
}
