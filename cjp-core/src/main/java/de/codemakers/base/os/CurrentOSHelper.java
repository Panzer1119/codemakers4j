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

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CurrentOSHelper implements OSHelper {
    
    private static final AtomicLong LAST_ID = new AtomicLong(-1);
    private static final Map<Long, OSFunction> OS_FUNCTIONS = new ConcurrentHashMap<>();
    
    @Override
    public boolean isPathAbsolute(String path) {
        return new File(path).isAbsolute();
    }
    
    @Override
    public String getFileSeparator() {
        return File.separator;
    }
    
    @Override
    public char getFileSeparatorChar() {
        return File.separatorChar;
    }
    
    @Override
    public String getFileSeparatorRegex() {
        return File.separatorChar == '\\' ? File.separator + File.separator : File.separator;
    }
    
    @Override
    public String getPathSeparator() {
        return File.pathSeparator;
    }
    
    @Override
    public char getPathSeparatorChar() {
        return File.pathSeparatorChar;
    }
    
    @Override
    public String getPathSeparatorRegex() {
        return File.pathSeparatorChar == '\\' ? File.pathSeparator + File.pathSeparator : File.pathSeparator;
    }
    
    @Override
    public String getLineSeparator() {
        return System.getProperty("line.separator");
    }
    
    @Override
    public AdvancedFile getUsersDirectory() {
        return OSUtil.getUsersDirectory();
    }
    
    @Override
    public AdvancedFile getAppDataDirectory() {
        return OSUtil.getAppDataDirectory();
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
