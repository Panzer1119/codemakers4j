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

package de.codemakers.base.entities;

import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.base.util.interfaces.Snowflake;

import java.io.Serializable;
import java.util.Objects;

public class ObjectHolder<T> implements Copyable, Serializable, Snowflake {
    
    private final long id;
    private final T object;
    
    public ObjectHolder(T object) {
        this.id = generateId();
        this.object = object;
    }
    
    public ObjectHolder(long id, T object) {
        this.id = id;
        this.object = object;
    }
    
    @Override
    public long getId() {
        return id;
    }
    
    public T getObject() {
        return object;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ObjectHolder<?> objectHolder = (ObjectHolder<?>) o;
        return id == objectHolder.id && Objects.equals(object, objectHolder.object);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, object);
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "id=" + id + ", object=" + object + '}';
    }
    
    @Override
    public ObjectHolder<T> copy() {
        return new ObjectHolder<>(id, object);
    }
    
    @Override
    public void set(Copyable copyable) {
        throw new UnsupportedOperationException();
    }
    
}
