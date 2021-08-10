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

public class Require {
    
    public static final <T> T clazz(Object object, Class<T> clazz) {
        return clazz(object, clazz, object + " is not an instance of " + clazz);
    }
    
    public static final <T> T clazz(Object object, Class<T> clazz, String message) {
        if (clazz == null || object == null) {
            return null;
        }
        final boolean match = clazz.isAssignableFrom(object.getClass());
        if (!match) {
            throw new ClassCastException(message);
        }
        return (T) object;
    }
    
    public static final <T> T clazzOrNull(Object object, Class<T> clazz) {
        if (clazz == null || object == null) {
            return null;
        }
        return clazz.isAssignableFrom(object.getClass()) ? (T) object : null;
    }
    
}
