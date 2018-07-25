/*
 *     Copyright 2018 Paul Hagedorn (Panzer1119)
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

package de.codemakers.base.os;

import de.codemakers.base.os.function.BatteryInfo;
import de.codemakers.base.os.function.OSFunction;
import de.codemakers.base.os.function.SystemInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class WindowsHelper implements OSHelper {
    
    public static final Pattern DRIVER_PATTERN = Pattern.compile("([A-Z]):\\\\(.*)");
    
    private static final AtomicLong LAST_ID = new AtomicLong(-1);
    private static final Map<Long, OSFunction> OS_FUNCTIONS = new ConcurrentHashMap<>();
    
    static {
        OS_FUNCTIONS.put(LAST_ID.incrementAndGet(), new SystemInfo() {
            @Override
            public BatteryInfo getBatteryInfo() {
                return null;
            }
        });
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
    public String getPathSeparator() {
        return ";";
    }
    
    @Override
    public String getLineSeparator() {
        return "\r\n";
    }
    
    @Override
    public List<OSFunction> getOSFunctions() {
        return (List<OSFunction>) OS_FUNCTIONS.values();
    }
    
    @Override
    public <T extends OSFunction> T getOSFunction(long id) {
        return (T) OS_FUNCTIONS.get(id);
    }
    
    @Override
    public long addOSFunction(OSFunction osFunction) {
        Objects.requireNonNull(osFunction);
        final long id = LAST_ID.incrementAndGet();
        OS_FUNCTIONS.put(id, osFunction);
        return id;
    }
    
    @Override
    public boolean removeOSFunction(long id) {
        return OS_FUNCTIONS.remove(id) != null;
    }
    
    @Override
    public String toString() {
        return toStringIntern();
    }
    
}
