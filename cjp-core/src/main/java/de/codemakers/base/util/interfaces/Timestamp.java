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

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@FunctionalInterface
public interface Timestamp extends Serializable {
    
    long getTimestamp();
    
    default Instant toInstant() {
        return Instant.ofEpochMilli(getTimestamp());
    }
    
    default ZonedDateTime toLocalZonedDateTime() {
        return toZonedDateTime(ZoneId.systemDefault());
    }
    
    default ZonedDateTime toZonedDateTime(ZoneId zone) {
        return ZonedDateTime.ofInstant(toInstant(), zone);
    }
    
    default LocalDateTime toLocalDateTime() {
        return LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault());
    }
    
    default String toLocalISOZonedDateTime() {
        return toISOZonedDateTime(ZoneId.systemDefault());
    }
    
    default String toISOZonedDateTime(ZoneId zone) {
        return toZonedDateTime(zone).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
    
}
