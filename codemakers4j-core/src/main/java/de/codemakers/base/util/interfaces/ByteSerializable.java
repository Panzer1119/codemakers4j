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

package de.codemakers.base.util.interfaces;

import de.codemakers.base.util.Require;
import de.codemakers.io.SerializationUtil;

import java.io.Serializable;
import java.util.Optional;

public interface ByteSerializable extends Serializable {
    
    byte NOT_NULL = 64;
    byte NULL = -64;
    
    default Optional<byte[]> toBytes() {
        return SerializationUtil.objectToBytes(this);
    }
    
    default boolean fromBytes(byte[] bytes) {
        if (bytes != null && this instanceof Copyable copyable) {
            final Optional<Serializable> optional = SerializationUtil.bytesToObject(bytes);
            if (optional.isEmpty()) {
                return false;
            }
            final Copyable otherCopyable = Require.clazz(optional.get(), Copyable.class);
            if (otherCopyable != null) {
                copyable.set(otherCopyable);
                return true;
            }
        }
        return false;
    }
    
    default byte resolveNull(Object object) {
        return object == null ? NULL : NOT_NULL;
    }
    
    default boolean isNull(byte b) {
        return b == NULL;
    }
    
    default boolean isNotNull(byte b) {
        return b == NOT_NULL;
    }
    
    default <T> int arrayLength(T[] array) {
        return array == null ? -1 : array.length;
    }
    
    default int arrayLength(byte[] array) {
        return array == null ? -1 : array.length;
    }
    
}
