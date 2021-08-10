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

package de.codemakers.base.util;

import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.io.file.AdvancedFile;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class Settings implements Copyable {
    
    public abstract boolean hasProperty(String key);
    
    public abstract <T> T getProperty(String key, Class<T> clazz);
    
    public abstract String getProperty(String key);
    
    public abstract String getProperty(String key, String defaultValue);
    
    public abstract byte getProperty(String key, byte defaultValue);
    
    public abstract short getProperty(String key, short defaultValue);
    
    public abstract int getProperty(String key, int defaultValue);
    
    public abstract long getProperty(String key, long defaultValue);
    
    public abstract float getProperty(String key, float defaultValue);
    
    public abstract double getProperty(String key, double defaultValue);
    
    public abstract char getProperty(String key, char defaultValue);
    
    public abstract boolean getProperty(String key, boolean defaultValue);
    
    public abstract boolean removeProperty(String key);
    
    public abstract Object setProperty(String key, String value);
    
    public abstract Object setProperty(String key, byte value);
    
    public abstract Object setProperty(String key, short value);
    
    public abstract Object setProperty(String key, int value);
    
    public abstract Object setProperty(String key, long value);
    
    public abstract Object setProperty(String key, float value);
    
    public abstract Object setProperty(String key, double value);
    
    public abstract Object setProperty(String key, char value);
    
    public abstract Object setProperty(String key, boolean value);
    
    public abstract Object setProperty(String key, String value, boolean save);
    
    public abstract boolean clear();
    
    public abstract boolean loadSettings();
    
    public boolean loadSettings(AdvancedFile advancedFile) {
        return loadSettings(advancedFile.createInputStreamWithoutException());
    }
    
    public abstract boolean loadSettings(InputStream inputStream);
    
    public abstract boolean saveSettings();
    
    public boolean saveSettings(AdvancedFile advancedFile) {
        return saveSettings(advancedFile.createOutputStreamWithoutException());
    }
    
    public abstract boolean saveSettings(OutputStream outputStream);
    
}
