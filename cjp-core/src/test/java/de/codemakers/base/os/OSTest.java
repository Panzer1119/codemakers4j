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

package de.codemakers.base.os;

import java.io.File;

public class OSTest {
    
    public static final void main(String[] args) {
        System.out.println(OSUtil.OS_NAME);
        System.out.println(OSUtil.getCurrentOS());
        final File file_absolute = new File("absolute").getAbsoluteFile();
        final File file_relative = new File("relative");
        System.out.println(file_absolute);
        System.out.println(file_relative);
        System.out.println(file_absolute.isAbsolute());
        System.out.println(file_relative.isAbsolute());
        System.out.println(OSUtil.CURRENT_OS_HELPER.isPathAbsolute(file_absolute.getPath()));
        System.out.println(OSUtil.CURRENT_OS_HELPER.isPathAbsolute(file_relative.getPath()));
        System.out.println(OSUtil.WINDOWS_HELPER.isPathAbsolute(file_absolute.getPath()));
        System.out.println(OSUtil.WINDOWS_HELPER.isPathAbsolute(file_relative.getPath()));
    }
    
}
