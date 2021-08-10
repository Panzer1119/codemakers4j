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

import de.codemakers.base.env.SystemProperties;
import de.codemakers.base.os.functions.OSFunction;
import de.codemakers.io.file.AdvancedFile;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public interface OSHelper {
    
    boolean isPathAbsolute(String path);
    
    String getFileSeparator();
	
    char getFileSeparatorChar();
	
	String getFileSeparatorRegex();
    
    String getPathSeparator();
	
    char getPathSeparatorChar();
	
	String getPathSeparatorRegex();
    
    String getLineSeparator();
    
    default String getUser() {
        return SystemProperties.getUserName();
    }
    
    AdvancedFile getUsersDirectory();
    
    default AdvancedFile getUserDirectory(String user) {
        return new AdvancedFile(getUsersDirectory(), user);
    }
    
    default AdvancedFile getCurrentUserDirectory() {
        return getUserDirectory(getUser());
    }
    
    default String toStringIntern() {
        String newLine = "";
        for (char c : getLineSeparator().toCharArray()) {
            newLine += ((int) c);
        }
        return getClass().getSimpleName() + ": fileSep = " + getFileSeparator() + ", pathSep = " + getPathSeparator() + ", newLine = " + newLine;
    }
    
    AdvancedFile getAppDataDirectory();
    
    default AdvancedFile getAppDataSubDirectory(String name) {
        return new AdvancedFile(getAppDataDirectory(), name);
    }
    
    AtomicLong getIDCounter();
    
    Map<Long, OSFunction> getOSFunctionsMap();
    
    default List<OSFunction> getOSFunctions() {
        return new ArrayList<>(getOSFunctionsMap().values());
    }
    
    default <T extends OSFunction> List<T> getOSFunctions(Class<T> clazz) {
        Objects.requireNonNull(clazz);
        /*
        final List<OSFunction> osFunctions = getOSFunctions();
        if (osFunctions == null || osFunctions.isEmpty()) {
            return null;
        }
        return osFunctions.stream().filter((osFunction) -> (clazz.isAssignableFrom(osFunction.getClass()))).map((osFunction) -> (T) osFunction).collect(Collectors.toList());
        */
        if (getOSFunctionsMap().isEmpty()) {
            return null;
        }
        return getOSFunctionsMap().entrySet().stream().filter((entry) -> (clazz.isAssignableFrom(entry.getValue().getClass()))).map((entry) -> (T) entry.getValue()).collect(Collectors.toList());
    }
    
    default <T extends OSFunction> T getOSFunction(Class<T> clazz) {
        Objects.requireNonNull(clazz);
        /*
        final List<OSFunction> osFunctions = getOSFunctions();
        if (osFunctions == null || osFunctions.isEmpty()) {
            return null;
        }
        return (T) osFunctions.stream().filter((osFunction) -> (clazz.isAssignableFrom(osFunction.getClass()))).findFirst().orElse(null);
        */
        if (getOSFunctionsMap().isEmpty()) {
            return null;
        }
        return (T) getOSFunctionsMap().entrySet().stream().filter((entry) -> (clazz.isAssignableFrom(entry.getValue().getClass()))).map(AbstractMap.Entry::getValue).findFirst().orElse(null);
    }
    
    default List<OSFunction> getOSFunctions(String name) {
        Objects.requireNonNull(name);
        final List<OSFunction> osFunctions = getOSFunctions();
        if (osFunctions == null || osFunctions.isEmpty()) {
            return null;
        }
        return osFunctions.stream().filter((osFunction) -> (name.equals(osFunction.getName()))).collect(Collectors.toList());
    }
    
    default <T extends OSFunction> T getOSFunction(long id) {
        return (T) getOSFunctionsMap().get(id);
    }
    
    default long addOSFunction(OSFunction osFunction) {
        Objects.requireNonNull(osFunction);
        final long id = getIDCounter().incrementAndGet();
        getOSFunctionsMap().put(id, osFunction);
        return id;
    }
    
    default boolean removeOSFunction(long id) {
        return getOSFunctionsMap().remove(id) != null;
    }
    
}
