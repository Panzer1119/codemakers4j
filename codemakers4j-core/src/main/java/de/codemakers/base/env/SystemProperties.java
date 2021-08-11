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

package de.codemakers.base.env;

public class SystemProperties {
    
    //OS
    public static final String KEY_OS_NAME = "os.name";
    public static final String KEY_OS_ARCH = "os.arch";
    //Java
    public static final String KEY_JAVA_VERSION = "java.version";
    //User
    public static final String KEY_USER_NAME = "user.name";
    public static final String KEY_USER_DIR = "user.dir";
    public static final String KEY_USER_HOME = "user.home";
    //Misc
    public static final String KEY_LINE_SEPARATOR = "line.separator";
    
    public static String getProperty(String name) {
        return System.getProperty(name);
    }
    
    public static String getProperty(String name, String defaultValue) {
        return System.getProperty(name, defaultValue);
    }
    
    public static String getOSName() {
        return getProperty(KEY_OS_NAME);
    }
    
    public static String getOSArch() {
        return getProperty(KEY_OS_ARCH);
    }
    
    public static String getJavaVersion() {
        return getProperty(KEY_JAVA_VERSION);
    }
    
    public static String getUserName() {
        return getProperty(KEY_USER_NAME);
    }
    
    public static String getUserDir() {
        return getProperty(KEY_USER_DIR);
    }
    
    public static String getUserHome() {
        return getProperty(KEY_USER_HOME);
    }
    
    public static String getLineSeparator() {
        return getProperty(KEY_LINE_SEPARATOR);
    }
    
}
