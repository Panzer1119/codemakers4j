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

package de.codemakers.lua;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class LuaContext {
    
    private final Map<String, Object> values = new ConcurrentHashMap<>();
    private final BiMap<String, LuaFunction> functions = HashBiMap.create();
    
    public LuaContext() {
        this(null);
    }
    
    public LuaContext(Map<String, Object> values) {
        this(values, null);
    }
    
    public LuaContext(Map<String, Object> values, BiMap<String, LuaFunction> functions) {
        if (values != null) {
            this.values.putAll(values);
        }
        if (functions != null) {
            this.functions.putAll(functions);
        }
    }
    
    public Map<String, Object> getValues() {
        return values;
    }
    
    public <T> T getValue(String name) {
        Objects.requireNonNull(name);
        return (T) values.get(name);
    }
    
    public <R, T> R setValue(String name, T value) {
        Objects.requireNonNull(name);
        return (R) values.put(name, value);
    }
    
    public boolean clearValues() {
        values.clear();
        return values.isEmpty();
    }
    
    public BiMap<String, LuaFunction> getFunctions() {
        return functions;
    }
    
    public LuaFunction getFunction(String name) {
        Objects.requireNonNull(name);
        return functions.get(name);
    }
    
    public LuaFunction setFunction(LuaFunction luaFunction) {
        Objects.requireNonNull(luaFunction);
        return setFunction(luaFunction.getName(), luaFunction);
    }
    
    public LuaFunction setFunction(String name, LuaFunction luaFunction) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(luaFunction);
        return functions.put(name, luaFunction);
    }
    
    public boolean clearFunctions() {
        functions.clear();
        return functions.isEmpty();
    }
    
}
