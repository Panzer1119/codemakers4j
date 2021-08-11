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

package de.codemakers.base.os;

import de.codemakers.base.os.functions.OSFunction;
import de.codemakers.io.file.AdvancedFile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MacOSHelper implements OSHelper {
    
    public static final String LIBRARY = "Library";
    public static final String APPLICATION_SUPPORT = "Application Support";
    
    AtomicLong LAST_ID = new AtomicLong(-1);
    Map<Long, OSFunction> OS_FUNCTIONS = new ConcurrentHashMap<>();
    
    @Override
    public boolean isPathAbsolute(String path) {
        return OSUtil.LINUX_HELPER.isPathAbsolute(path);
    }
    
    @Override
    public String getFileSeparator() {
        return OSUtil.LINUX_HELPER.getFileSeparator();
    }
    
    @Override
    public char getFileSeparatorChar() {
        return OSUtil.LINUX_HELPER.getFileSeparatorChar();
    }
    
    @Override
    public String getFileSeparatorRegex() {
        return OSUtil.LINUX_HELPER.getFileSeparatorRegex();
    }
    
    @Override
    public String getPathSeparator() {
        return OSUtil.LINUX_HELPER.getPathSeparator();
    }
    
    @Override
    public char getPathSeparatorChar() {
        return OSUtil.LINUX_HELPER.getPathSeparatorChar();
    }
    
    @Override
    public String getPathSeparatorRegex() {
        return OSUtil.LINUX_HELPER.getPathSeparatorRegex();
    }
    
    @Override
    public String getLineSeparator() {
        return "\r";
    }
    
    @Override
    public AdvancedFile getUsersDirectory() {
        return new AdvancedFile("/Users");
    }
    
    @Override
    public AdvancedFile getAppDataDirectory() {
        return new AdvancedFile(OSUtil.getUserHomeDirectory(), LIBRARY, APPLICATION_SUPPORT);
    }
    
    @Override
    public AtomicLong getIDCounter() {
        return LAST_ID;
    }
    
    @Override
    public Map<Long, OSFunction> getOSFunctionsMap() {
        return OS_FUNCTIONS;
    }
    
    @Override
    public String toString() {
        return toStringIntern();
    }
    
}
