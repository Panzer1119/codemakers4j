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

import java.io.File;

public class CurrentOSHelper implements OSHelper {
    
    @Override
    public boolean isPathAbsolute(String path) {
        return new File(path).isAbsolute();
    }
    
    @Override
    public String getFileSeparator() {
        return File.separator;
    }
    
    @Override
    public String getPathSeparator() {
        return File.pathSeparator;
    }
    
    @Override
    public String getLineSeparator() {
        return System.getProperty("line.separator");
    }
    
    @Override
    public String toString() {
        return toStringIntern();
    }
    
}
