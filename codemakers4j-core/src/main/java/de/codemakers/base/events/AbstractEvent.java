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

package de.codemakers.base.events;

import de.codemakers.base.util.interfaces.Snowflake;
import de.codemakers.security.util.EasyCryptUtil;

import java.util.Objects;
import java.util.Random;

public abstract class AbstractEvent implements Snowflake {
    
    protected static final Random RANDOM_ID_GENERATOR = new Random(EasyCryptUtil.getSecurestRandom().nextLong());
    
    public static long nextId() {
        return RANDOM_ID_GENERATOR.nextLong();
    }
    
    protected final long id;
    protected final long timestamp;
    
    public AbstractEvent() {
        this(System.currentTimeMillis());
    }
    
    public AbstractEvent(long timestamp) {
        this(nextId(), timestamp);
    }
    
    public AbstractEvent(long id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }
    
    @Override
    public final long getId() {
        return id;
    }
    
    @Override
    public final long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "id=" + id + ", timestamp=" + timestamp + '}';
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof AbstractEvent) {
            return id == ((AbstractEvent) object).id;
        }
        return false;
    }
    
}
