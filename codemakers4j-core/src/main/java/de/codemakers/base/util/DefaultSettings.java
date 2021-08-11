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
import de.codemakers.io.IOUtil;
import de.codemakers.io.file.AdvancedFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;

public class DefaultSettings extends Settings {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final String DEFAULT_COMMENT = "Changed on:";
    
    protected final Properties properties;
    protected AdvancedFile advancedFile = null;
    protected boolean autoSave = true;
    
    public DefaultSettings() {
        this(new Properties());
    }
    
    public DefaultSettings(Properties properties) {
        super();
        this.properties = properties;
    }
    
    public DefaultSettings(AdvancedFile advancedFile) {
        this();
        this.advancedFile = advancedFile;
    }
    
    private DefaultSettings(Properties properties, AdvancedFile advancedFile) {
        super();
        this.properties = properties;
        this.advancedFile = advancedFile;
    }
    
    public Properties getProperties() {
        return properties;
    }
    
    public DefaultSettings setProperties(Properties properties) {
        this.properties.clear();
        this.properties.putAll(properties);
        return this;
    }
    
    public AdvancedFile getAdvancedFile() {
        return advancedFile;
    }
    
    public DefaultSettings setAdvancedFile(AdvancedFile advancedFile) {
        this.advancedFile = advancedFile;
        return this;
    }
    
    public boolean isAutoSave() {
        return autoSave;
    }
    
    public DefaultSettings setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
        return this;
    }
    
    @Override
    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }
    
    @Override
    public <T> T getProperty(String key, Class<T> clazz) {
        return (T) getProperty(key);
    }
    
    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    @Override
    public String getProperty(String key, String defaultValue) {
        final String value = properties.getProperty(key);
        if (value == null) {
            /* //TODO
            if (autoAddProperties) {
                setProperty(key, defaultValue, autoSave);
            }
            */
            return defaultValue;
        }
        return value;
    }
    
    @Override
    public byte getProperty(String key, byte defaultValue) {
        try {
            return Byte.parseByte(getProperty(key, "" + defaultValue));
        } catch (Exception ex) {
            return defaultValue;
        }
    }
    
    @Override
    public short getProperty(String key, short defaultValue) {
        try {
            return Short.parseShort(getProperty(key, "" + defaultValue));
        } catch (Exception ex) {
            return defaultValue;
        }
    }
    
    @Override
    public int getProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(key, "" + defaultValue));
        } catch (Exception ex) {
            return defaultValue;
        }
    }
    
    @Override
    public long getProperty(String key, long defaultValue) {
        try {
            return Long.parseLong(getProperty(key, "" + defaultValue));
        } catch (Exception ex) {
            return defaultValue;
        }
    }
    
    @Override
    public float getProperty(String key, float defaultValue) {
        try {
            return Float.parseFloat(getProperty(key, "" + defaultValue));
        } catch (Exception ex) {
            return defaultValue;
        }
    }
    
    @Override
    public double getProperty(String key, double defaultValue) {
        try {
            return Double.parseDouble(getProperty(key, "" + defaultValue));
        } catch (Exception ex) {
            return defaultValue;
        }
    }
    
    @Override
    public char getProperty(String key, char defaultValue) {
        try {
            return getProperty(key, "" + defaultValue).charAt(0);
        } catch (Exception ex) {
            return defaultValue;
        }
    }
    
    @Override
    public boolean getProperty(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(getProperty(key, "" + defaultValue));
        } catch (Exception ex) {
            return defaultValue;
        }
    }
    
    @Override
    public boolean removeProperty(String key) {
        try {
            properties.remove(key);
            if (autoSave) {
                saveSettings();
            }
            return getProperty(key, (String) null) == null;
        } catch (Exception ex) {
            return false;
        }
    }
    
    @Override
    public Object setProperty(String key, String value) {
        return setProperty(key, value, autoSave);
    }
    
    @Override
    public Object setProperty(String key, byte value) {
        return setProperty(key, "" + value, autoSave);
    }
    
    @Override
    public Object setProperty(String key, short value) {
        return setProperty(key, "" + value, autoSave);
    }
    
    @Override
    public Object setProperty(String key, int value) {
        return setProperty(key, "" + value, autoSave);
    }
    
    @Override
    public Object setProperty(String key, long value) {
        return setProperty(key, "" + value, autoSave);
    }
    
    @Override
    public Object setProperty(String key, float value) {
        return setProperty(key, "" + value, autoSave);
    }
    
    @Override
    public Object setProperty(String key, double value) {
        return setProperty(key, "" + value, autoSave);
    }
    
    @Override
    public Object setProperty(String key, char value) {
        return setProperty(key, "" + value, autoSave);
    }
    
    @Override
    public Object setProperty(String key, boolean value) {
        return setProperty(key, "" + value, autoSave);
    }
    
    @Override
    public Object setProperty(String key, String value, boolean save) {
        final Object old = properties.setProperty(key, value);
        if (save) {
            saveSettings();
        }
        return old;
    }
    
    @Override
    public boolean clear() {
        properties.clear();
        return properties.isEmpty();
    }
    
    @Override
    public boolean loadSettings() {
        return loadSettings(advancedFile);
    }
    
    @Override
    public boolean loadSettings(InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        try {
            properties.clear();
            properties.load(inputStream);
            inputStream.close();
            return true;
        } catch (Exception ex) {
            IOUtil.closeQuietly(inputStream);
            logger.error(ex);
            return false;
        }
    }
    
    @Override
    public boolean saveSettings() {
        return saveSettings(advancedFile);
    }
    
    @Override
    public boolean saveSettings(OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        try {
            properties.store(outputStream, DEFAULT_COMMENT);
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception ex) {
            IOUtil.closeQuietly(outputStream);
            logger.error(ex);
            return false;
        }
    }
    
    @Override
    public DefaultSettings copy() {
        final DefaultSettings defaultSettings = new DefaultSettings(advancedFile.copy());
        defaultSettings.setProperties(properties);
        defaultSettings.autoSave = autoSave;
        //defaultSettings.autoAddProperties = autoAddProperties; //TODO
        return defaultSettings;
    }
    
    @Override
    public void set(Copyable copyable) {
        final DefaultSettings defaultSettings = Require.clazz(copyable, DefaultSettings.class);
        if (defaultSettings != null) {
            setProperties(defaultSettings.properties);
        }
    }
    
    @Override
    public String toString() {
        return "DefaultSettings{" + "properties=" + properties + ", advancedFile=" + advancedFile + ", autoSave=" + autoSave + '}';
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final DefaultSettings that = (DefaultSettings) other;
        return autoSave == that.autoSave && Objects.equals(properties, that.properties);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(properties, autoSave);
    }
    
}
