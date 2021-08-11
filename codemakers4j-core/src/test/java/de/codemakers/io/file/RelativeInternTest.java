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

package de.codemakers.io.file;

import java.net.URL;

public class RelativeInternTest {
    
    public static final void main(String[] args) {
        final String path_absolute = "/de/codemakers/io/file/test.txt";
        final URL url_absolute = RelativeInternTest.class.getResource(path_absolute);
        final String path_relative = "test.txt";
        final URL url_relative = RelativeInternTest.class.getResource(path_relative);
        final Class<?> clazz_relative = RelativeInternTest.class;
        System.out.println("path_absolute  = " + path_absolute);
        System.out.println("url_absolute   = " + url_absolute);
        System.out.println("path_relative  = " + path_relative);
        System.out.println("url_relative   = " + url_relative);
        System.out.println("clazz_relative = " + clazz_relative);
        final URL url_relative_reconstructed = clazz_relative.getResource(path_relative);
        System.out.println("url_relative_reconstructed = " + url_relative_reconstructed);
        System.out.println("###########################################################################################################################");
        System.out.println("clazz_relative                              = " + clazz_relative);
        System.out.println("clazz_relative.getSimpleName()              = " + clazz_relative.getSimpleName());
        System.out.println("clazz_relative.getName()                    = " + clazz_relative.getName());
        System.out.println("clazz_relative.getPackage()                 = " + clazz_relative.getPackage());
        System.out.println("clazz_relative.getPackage().getName()       = " + clazz_relative.getPackage().getName());
    }
    
}
