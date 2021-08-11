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

package de.codemakers.base.entities;

import de.codemakers.base.util.interfaces.Snowflake;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractEndpoint implements Serializable, Snowflake {
    
    protected final long id;
    
    public AbstractEndpoint() {
        this.id = generateId();
    }
    
    public AbstractEndpoint(long id) {
        this.id = id;
    }
    
    @Override
    public long getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "AbstractEndpoint{" + "id=" + id + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AbstractEndpoint that = (AbstractEndpoint) o;
        return id == that.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}
