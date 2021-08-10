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

public class LinuxHelper implements OSHelper {
    
    public static final String FOLDER_AC_STRING = "/sys/class/power_supply";
    public static final File FOLDER_AC = new File(FOLDER_AC_STRING);
    public static final String FILE_UEVENT_NAME = "uevent";
    public static final String POWER_SUPPLY_NAME = "POWER_SUPPLY_NAME";
    public static final String POWER_SUPPLY_STATUS = "POWER_SUPPLY_STATUS";
    public static final String POWER_SUPPLY_PRESENT = "POWER_SUPPLY_PRESENT";
    public static final String POWER_SUPPLY_TECHNOLOGY = "POWER_SUPPLY_TECHNOLOGY";
    public static final String POWER_SUPPLY_CYCLE_COUNT = "POWER_SUPPLY_CYCLE_COUNT";
    public static final String POWER_SUPPLY_VOLTAGE_MIN_DESIGN = "POWER_SUPPLY_VOLTAGE_MIN_DESIGN";
    public static final String POWER_SUPPLY_VOLTAGE_NOW = "POWER_SUPPLY_VOLTAGE_NOW";
    public static final String POWER_SUPPLY_POWER_NOW = "POWER_SUPPLY_POWER_NOW";
    public static final String POWER_SUPPLY_ENERGY_FULL_DESIGN = "POWER_SUPPLY_ENERGY_FULL_DESIGN";
    public static final String POWER_SUPPLY_ENERGY_FULL = "POWER_SUPPLY_ENERGY_FULL";
    public static final String POWER_SUPPLY_ENERGY_NOW = "POWER_SUPPLY_ENERGY_NOW";
    public static final String POWER_SUPPLY_CAPACITY = "POWER_SUPPLY_CAPACITY";
    public static final String POWER_SUPPLY_CAPACITY_LEVEL = "POWER_SUPPLY_CAPACITY_LEVEL";
    public static final String POWER_SUPPLY_MODEL_NAME = "POWER_SUPPLY_MODEL_NAME";
    public static final String POWER_SUPPLY_MANUFACTURER = "POWER_SUPPLY_MANUFACTURER";
    public static final String POWER_SUPPLY_SERIAL_NUMBER = "POWER_SUPPLY_SERIAL_NUMBER";
    AtomicLong LAST_ID = new AtomicLong(-1);
    Map<Long, OSFunction> OS_FUNCTIONS = new ConcurrentHashMap<>();
    
    @Override
    public boolean isPathAbsolute(String path) {
        if (path == null || path.isEmpty()) {
            return false;
        }
        return path.startsWith(getFileSeparator());
    }
    
    @Override
    public String getFileSeparator() {
        return "/";
    }
    
    @Override
    public char getFileSeparatorChar() {
        return '/';
    }
    
    @Override
    public String getFileSeparatorRegex() {
        return "/";
    }
    
    @Override
    public String getPathSeparator() {
        return ":";
    }
    
    @Override
    public char getPathSeparatorChar() {
        return ':';
    }
    
    @Override
    public String getPathSeparatorRegex() {
        return ":";
    }
    
    @Override
    public String getLineSeparator() {
        return "\n";
    }
    
    @Override
    public AdvancedFile getUsersDirectory() {
        return new AdvancedFile("/home");
    }
    
    @Override
    public AdvancedFile getAppDataDirectory() {
        return new AdvancedFile(OSUtil.getUserHomeDirectory());
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
