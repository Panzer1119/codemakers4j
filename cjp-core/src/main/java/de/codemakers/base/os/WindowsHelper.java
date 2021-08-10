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

import de.codemakers.base.env.SystemEnvironment;
import de.codemakers.base.os.functions.OSFunction;
import de.codemakers.io.file.AdvancedFile;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class WindowsHelper implements OSHelper {
    
    public static final Pattern DRIVER_PATTERN = Pattern.compile("([A-Z]):\\\\(.*)");
    public static final String APP_DATA = "AppData";
    AtomicLong LAST_ID = new AtomicLong(-1);
    Map<Long, OSFunction> OS_FUNCTIONS = new ConcurrentHashMap<>();
    
    public static final void process(String line_1, String line_2, Properties properties) {
        while (true) {
            if (line_1.isEmpty()) {
                break;
            }
            final int i_1 = line_1.indexOf("  ");
            final int i_2 = line_2.indexOf("  ");
            if (i_1 == -1) {
                properties.setProperty(line_1, line_2);
                line_1 = "";
                line_2 = "";
            } else {
                final String key = line_1.substring(0, i_1);
                final String value = line_2.substring(0, i_2 < 0 ? line_2.length() : i_2);
                int i = i_1;
                while (i < line_1.length() && line_1.charAt(i) == ' ') {
                    i++;
                }
                properties.setProperty(key, value);
                line_1 = line_1.substring(i);
                line_2 = line_2.substring(Math.min(line_2.length(), i));
            }
        }
    }
    
    public static String getSystemDrive() {
        return SystemEnvironment.getSystemDrive();
    }
    
    public static AdvancedFile getSystemDriveDirectory() {
        return new AdvancedFile(getSystemDrive());
    }
    
    @Override
    public boolean isPathAbsolute(String path) {
        if (path == null || path.isEmpty()) {
            return false;
        }
        return DRIVER_PATTERN.matcher(path).matches() || path.startsWith(getFileSeparator() + getFileSeparator());
    }
    
    @Override
    public String getFileSeparator() {
        return "\\";
    }
    
    @Override
    public char getFileSeparatorChar() {
        return '\\';
    }
    
    @Override
    public String getFileSeparatorRegex() {
        return "\\\\";
    }
    
    @Override
    public String getPathSeparator() {
        return ";";
    }
    
    @Override
    public char getPathSeparatorChar() {
        return ';';
    }
    
    @Override
    public String getPathSeparatorRegex() {
        return ";";
    }
    
    @Override
    public String getLineSeparator() {
        return "\r\n";
    }
    
    @Override
    public AdvancedFile getUsersDirectory() {
        return new AdvancedFile(getSystemDrive(), "Users");
    }
    
    @Override
    public AdvancedFile getAppDataDirectory() {
        return new AdvancedFile(OSUtil.getUserHomeDirectory(), APP_DATA, "Roaming");
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
