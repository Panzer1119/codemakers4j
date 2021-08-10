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

import com.google.gson.*;
import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.base.util.tough.ToughSupplier;
import de.codemakers.io.IOUtil;
import de.codemakers.io.file.AdvancedFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class JsonSettings extends Settings {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final Gson GSON = new GsonBuilder().create();
    public static final Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().create();
    
    protected JsonObject jsonObject;
    protected AdvancedFile advancedFile = null;
    protected boolean autoSave = true;
    protected boolean pretty = true;
    
    public JsonSettings() {
        this(new JsonObject());
    }
    
    public JsonSettings(JsonObject jsonObject) {
        super();
        setJsonObject(jsonObject);
    }
    
    public JsonSettings(AdvancedFile advancedFile) {
        this();
        this.advancedFile = advancedFile;
    }
    
    public JsonSettings(JsonObject jsonObject, AdvancedFile advancedFile) {
        super();
        setJsonObject(jsonObject);
        this.advancedFile = advancedFile;
    }
    
    public JsonObject getJsonObject() {
        return jsonObject;
    }
    
    public JsonSettings setJsonObject(JsonObject jsonObject) {
        this.jsonObject = Objects.requireNonNull(jsonObject);
        return this;
    }
    
    public AdvancedFile getAdvancedFile() {
        return advancedFile;
    }
    
    public JsonSettings setAdvancedFile(AdvancedFile advancedFile) {
        this.advancedFile = advancedFile;
        return this;
    }
    
    public boolean isAutoSave() {
        return autoSave;
    }
    
    public JsonSettings setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
        return this;
    }
    
    public boolean isPretty() {
        return pretty;
    }
    
    public JsonSettings setPretty(boolean pretty) {
        this.pretty = pretty;
        return this;
    }
    
    public JsonSettings getOrCreateSubSettings(String key) {
        if (!jsonObject.has(key)) {
            jsonObject.add(key, new JsonObject());
        }
        if (autoSave) {
            saveSettings();
        }
        return getSubSettings(key);
    }
    
    public JsonSettings getSubSettings(final String key) {
        final JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null) {
            return null;
        }
        if (!(jsonElement instanceof JsonObject)) {
            throw new UnsupportedOperationException();
        }
        return toSubSettings(key, jsonElement.getAsJsonObject());
    }
    
    protected JsonSettings toSubSettings(final String key, JsonObject jsonObject) {
        final JsonSettings jsonSettings = new JsonSettings(jsonObject) {
            @Override
            public JsonSettings setJsonObject(JsonObject jsonObject__) {
                JsonSettings.this.setSubSettings(key, jsonObject__);
                return super.setJsonObject(jsonObject__);
            }
            
            @Override
            public AdvancedFile getAdvancedFile() {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public JsonSettings setAdvancedFile(AdvancedFile advancedFile) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean isAutoSave() {
                return JsonSettings.this.isAutoSave();
            }
            
            @Override
            public JsonSettings setAutoSave(boolean autoSave) {
                return JsonSettings.this.setAutoSave(autoSave);
            }
            
            @Override
            public boolean isPretty() {
                return JsonSettings.this.isPretty();
            }
            
            @Override
            public JsonSettings setPretty(boolean pretty) {
                return JsonSettings.this.setPretty(pretty);
            }
            
            @Override
            public boolean loadSettings() {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean loadSettings(InputStream inputStream) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public boolean saveSettings() {
                return JsonSettings.this.saveSettings();
            }
            
            @Override
            public boolean saveSettings(OutputStream outputStream) {
                return JsonSettings.this.saveSettings(outputStream);
            }
            
            @Override
            public String toString() {
                return "JsonSubSettings{" + "jsonObject=" + getJsonObject() + ", autoSave=" + JsonSettings.this.isAutoSave() + ", pretty=" + JsonSettings.this.isPretty() + '}';
            }
        };
        jsonSettings.setAutoSave(isAutoSave());
        jsonSettings.setPretty(isPretty());
        return jsonSettings;
    }
    
    public JsonSettings setSubSettings(String key, JsonSettings jsonSettings) {
        return setSubSettings(key, jsonSettings.getJsonObject());
    }
    
    protected JsonSettings setSubSettings(String key, JsonObject jsonObject) {
        this.jsonObject.add(key, jsonObject);
        return this; //TODO Or the old value (or null)?
    }
    
    protected <R> R useJsonElementOrNull(String key, ToughFunction<JsonElement, R> toughFunction) {
        return useJsonElementOrNull(key, toughFunction, null);
    }
    
    protected <R> R useJsonElementOrNull(String key, ToughFunction<JsonElement, R> toughFunction, R defaultValue) {
        if (key == null || toughFunction == null) {
            return null;
        }
        final JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null) {
            return defaultValue;
        }
        if (jsonElement instanceof JsonObject) {
            throw new UnsupportedOperationException(String.format("To get %s use %s()", JsonObject.class.getSimpleName(), "getSubSettings"));
        } else if (jsonElement instanceof JsonArray) {
            //throw new UnsupportedOperationException(String.format("To get %s use %s()", JsonArray.class.getSimpleName(), "getSubSettingsList"));
            throw new UnsupportedOperationException();
        }
        final R r = toughFunction.applyWithoutException(jsonElement);
        return r != null ? r : defaultValue;
    }
    
    public List<JsonSettings> getSubSettingsList(final String key) {
        return getPropertyListJsonObjects(key).stream().map((jsonObject) -> toSubSettings(key, jsonObject)).collect(Collectors.toList());
    }
    
    public List<JsonObject> getPropertyListJsonObjects(String key) {
        return getPropertyList(key, JsonElement::getAsJsonObject, ArrayList::new);
    }
    
    public List<String> getPropertyListStrings(String key) {
        return getPropertyList(key, JsonElement::getAsString, ArrayList::new);
    }
    
    public List<JsonElement> getPropertyList(String key) {
        return getPropertyList(key, null, ArrayList::new);
    }
    
    public <R> List<R> getPropertyList(String key, ToughFunction<JsonElement, R> toughFunction, ToughSupplier<List<R>> defaultValueSupplier) {
        if (key == null) {
            return null;
        }
        if (toughFunction == null) {
            toughFunction = (jsonElement) -> (R) jsonElement;
        }
        final JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null) {
            return defaultValueSupplier == null ? null : defaultValueSupplier.getWithoutException();
        }
        if (jsonElement instanceof JsonObject) {
            throw new UnsupportedOperationException(String.format("To get %s use %s()", JsonObject.class.getSimpleName(), "getSubSettings"));
        } else if (jsonElement instanceof JsonPrimitive) {
            throw new UnsupportedOperationException(String.format("To get %s use %s()", JsonPrimitive.class.getSimpleName(), "getProperty"));
        } else if (!(jsonElement instanceof JsonArray)) {
            throw new UnsupportedOperationException("This should never happen");
        }
        return StreamSupport.stream(((JsonArray) jsonElement).spliterator(), false).map(toughFunction::applyWithoutException).collect(Collectors.toList());
    }
    
    public <T> JsonSettings setPropertyList(String key, List<T> list) {
        return setPropertyList(key, null, list);
    }
    
    public <T> JsonSettings setPropertyList(String key, ToughFunction<T, JsonElement> toughFunction, List<T> list) {
        if (key == null) {
            throw new NullPointerException("key may not be null");
        } else if (key.isEmpty()) {
            throw new NullPointerException("key may not be empty"); //TODO This is not really a NullPointerException?
        } else if (list == null) {
            jsonObject.remove(key);
            return this;
        } else if (list.isEmpty()) {
            jsonObject.add(key, new JsonArray());
            return this;
        }
        if (toughFunction == null) {
            toughFunction = (t) -> GSON.toJsonTree(t);
        }
        final JsonArray jsonArray = new JsonArray();
        list.stream().map(toughFunction::applyWithoutException).forEach(jsonArray::add);
        jsonObject.add(key, jsonArray);
        return this;
    }
    
    @Override
    public boolean hasProperty(String key) {
        return jsonObject.has(key);
    }
    
    @Override
    public <T> T getProperty(String key, Class<T> clazz) {
        return (T) getProperty(key);
    }
    
    @Override
    public String getProperty(String key) {
        return useJsonElementOrNull(key, JsonElement::getAsString);
    }
    
    @Override
    public String getProperty(String key, String defaultValue) {
        return useJsonElementOrNull(key, JsonElement::getAsString, defaultValue);
    }
    
    @Override
    public byte getProperty(String key, byte defaultValue) {
        return useJsonElementOrNull(key, JsonElement::getAsByte, defaultValue);
    }
    
    @Override
    public short getProperty(String key, short defaultValue) {
        return useJsonElementOrNull(key, JsonElement::getAsShort, defaultValue);
    }
    
    @Override
    public int getProperty(String key, int defaultValue) {
        return useJsonElementOrNull(key, JsonElement::getAsInt, defaultValue);
    }
    
    @Override
    public long getProperty(String key, long defaultValue) {
        return useJsonElementOrNull(key, JsonElement::getAsLong, defaultValue);
    }
    
    @Override
    public float getProperty(String key, float defaultValue) {
        return useJsonElementOrNull(key, JsonElement::getAsFloat, defaultValue);
    }
    
    @Override
    public double getProperty(String key, double defaultValue) {
        return useJsonElementOrNull(key, JsonElement::getAsDouble, defaultValue);
    }
    
    @Override
    public char getProperty(String key, char defaultValue) {
        return useJsonElementOrNull(key, JsonElement::getAsCharacter, defaultValue);
    }
    
    @Override
    public boolean getProperty(String key, boolean defaultValue) {
        return useJsonElementOrNull(key, JsonElement::getAsBoolean, defaultValue);
    }
    
    @Override
    public boolean removeProperty(String key) {
        try {
            jsonObject.remove(key);
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
        final JsonElement old = jsonObject.remove(key);
        jsonObject.addProperty(key, value);
        if (save) {
            saveSettings();
        }
        if (old == null) {
            return null;
        }
        if (old instanceof JsonObject) {
            return new JsonSettings((JsonObject) old);
        }
        return old.getAsString();
    }
    
    @Override
    public boolean clear() {
        jsonObject = new JsonObject();
        return true;
    }
    
    @Override
    public boolean loadSettings() {
        if (advancedFile == null) {
            return false;
        }
        return loadSettings(advancedFile);
    }
    
    @Override
    public boolean loadSettings(InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        try {
            jsonObject = GSON.fromJson(new InputStreamReader(inputStream), JsonObject.class);
            inputStream.close();
            return true;
        } catch (Exception ex) {
            jsonObject = new JsonObject();
            IOUtil.closeQuietly(inputStream);
            logger.error(ex);
            return false;
        }
    }
    
    @Override
    public boolean saveSettings() {
        if (advancedFile == null) {
            return false;
        }
        return saveSettings(advancedFile);
    }
    
    @Override
    public boolean saveSettings(OutputStream outputStream) {
        Objects.requireNonNull(outputStream);
        try {
            Gson gson = GSON;
            if (pretty) {
                gson = GSON_PRETTY;
            }
            //gson.toJson(jsonObject, new JsonWriter(new OutputStreamWriter(outputStream)));
            outputStream.write(gson.toJson(jsonObject).getBytes());
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
    public Copyable copy() {
        final JsonSettings jsonSettings = new JsonSettings(advancedFile.copy());
        jsonSettings.setJsonObject(getJsonObject().deepCopy());
        jsonSettings.setAutoSave(isAutoSave());
        jsonSettings.setPretty(isPretty());
        return jsonSettings;
    }
    
    @Override
    public void set(Copyable copyable) {
        final JsonSettings jsonSettings = Require.clazz(copyable, JsonSettings.class);
        if (jsonSettings != null) {
            setJsonObject(jsonSettings.getJsonObject().deepCopy());
        }
    }
    
    @Override
    public String toString() {
        return "JsonSettings{" + "jsonObject=" + jsonObject + ", advancedFile=" + advancedFile + ", autoSave=" + autoSave + ", pretty=" + pretty + '}';
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final JsonSettings that = (JsonSettings) other;
        return autoSave == that.autoSave && pretty == that.pretty && Objects.equals(jsonObject, that.jsonObject);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(jsonObject, autoSave, pretty);
    }
    
}
