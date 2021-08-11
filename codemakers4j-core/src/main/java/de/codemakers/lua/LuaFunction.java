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

import java.util.Arrays;
import java.util.Objects;

public abstract class LuaFunction {
    
    private String name;
    private int returnNum;
    private int parameterNum;
    //
    private transient LuaContext luaContext;
    
    public LuaFunction(String name) {
        this(name, -1, -1);
    }
    
    public LuaFunction(String name, int returnNum, int parameterNum) {
        Objects.requireNonNull(name);
        this.name = name;
        this.returnNum = returnNum;
        this.parameterNum = parameterNum;
    }
    
    protected LuaFunction(String name, int returnNum, int parameterNum, LuaContext luaContext) {
        this(name, returnNum, parameterNum);
        this.luaContext = luaContext;
    }
    
    public String getName() {
        return name;
    }
    
    public LuaFunction setName(String name) {
        this.name = name;
        return this;
    }
    
    public int getReturnNum() {
        return returnNum;
    }
    
    public LuaFunction setReturnNum(int returnNum) {
        this.returnNum = Math.max(returnNum, -1);
        return this;
    }
    
    public int getParameterNum() {
        return parameterNum;
    }
    
    public LuaFunction setParameterNum(int parameterNum) {
        this.parameterNum = Math.max(parameterNum, -1);
        return this;
    }
    
    public LuaContext getLuaContext() {
        return luaContext;
    }
    
    protected LuaFunction setLuaContext(LuaContext luaContext) {
        this.luaContext = luaContext;
        return this;
    }
    
    public Object[] call(Object... parameters) {
        if (parameterNum >= 0) {
            parameters = Arrays.copyOf(parameters, parameterNum);
        }
        final Object[] output = callIntern(luaContext, parameters);
        if (returnNum >= 0) {
            return Arrays.copyOf(output, returnNum);
        }
        return output;
    }
    
    protected abstract Object[] callIntern(LuaContext luaContext, Object... parameters);
    
}
