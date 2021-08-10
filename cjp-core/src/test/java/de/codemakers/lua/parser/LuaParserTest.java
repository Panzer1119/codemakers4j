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

package de.codemakers.lua.parser;

import de.codemakers.io.file.AdvancedFile;

import java.util.List;

public class LuaParserTest {
    
    public static final void main(String[] args) throws Exception {
        final LuaParser luaParser = new LuaParser();
        final AdvancedFile advancedFile = new AdvancedFile("intern:test_1.txt");
        System.out.println("advancedFile=" + advancedFile);
        System.out.println("advancedFile.getAbsoluteFile()=" + advancedFile.getAbsoluteFile());
        System.out.println("advancedFile.exists()=" + advancedFile.exists());
        final List<String> strings = luaParser.parseLua(new String(advancedFile.readBytesWithoutException()));
        System.out.println("======================================================");
        strings.forEach(System.out::println);
        System.out.println("======================================================");
        luaParser.createLuaContext(strings);
    }
    
}
