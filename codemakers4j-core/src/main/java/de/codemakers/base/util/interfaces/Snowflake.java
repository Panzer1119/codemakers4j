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

import de.codemakers.base.util.IDTimeUtil;

@FunctionalInterface
public interface Snowflake extends Timestamp {
    
    long getId();
    
    default String getIdString() {
        return Long.toUnsignedString(getId());
    }
    
    @Override
    default long getTimestamp() {
        return IDTimeUtil.getTimestamp(getId());
    }
    
    default long generateId() {
        return generateId(System.currentTimeMillis());
    }
    
    default long generateId(long timestamp) {
        return IDTimeUtil.createId(timestamp);
    }
    
}
