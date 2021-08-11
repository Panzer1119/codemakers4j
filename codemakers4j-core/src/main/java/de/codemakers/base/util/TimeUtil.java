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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtil {
    
    public static final DateTimeFormatter ISO_TIME_FIXED_LENGTH = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME_FIXED_LENGTH = new DateTimeFormatterBuilder().parseCaseInsensitive().append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral('T').append(ISO_TIME_FIXED_LENGTH).toFormatter(Locale.getDefault());
    public static final DateTimeFormatter ISO_OFFSET_DATE_TIME_FIXED_LENGTH = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_LOCAL_DATE_TIME_FIXED_LENGTH).appendOffset("+HH:MM:ss", "+00:00").toFormatter(Locale.getDefault());
    public static final DateTimeFormatter ISO_OFFSET_REGION_DATE_TIME_FIXED_LENGTH = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_OFFSET_DATE_TIME_FIXED_LENGTH).appendLiteral('[').appendZoneRegionId().appendLiteral(']').toFormatter(Locale.getDefault());
    
    public static final DateTimeFormatter ISO_TIME_FIXED_LENGTH_FOR_FILES = DateTimeFormatter.ofPattern("HH.mm.ss.SSS");
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME_FIXED_LENGTH_FOR_FILES = new DateTimeFormatterBuilder().parseCaseInsensitive().append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral('T').append(ISO_TIME_FIXED_LENGTH_FOR_FILES).toFormatter(Locale.getDefault());
    public static final DateTimeFormatter ISO_OFFSET_DATE_TIME_FIXED_LENGTH_FOR_FILES = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_LOCAL_DATE_TIME_FIXED_LENGTH_FOR_FILES).appendOffset("+HHMMss", "+0000").toFormatter(Locale.getDefault());
    public static final DateTimeFormatter ISO_OFFSET_REGION_DATE_TIME_FIXED_LENGTH_FOR_FILES = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_OFFSET_DATE_TIME_FIXED_LENGTH_FOR_FILES).appendLiteral('[').appendZoneRegionId().appendLiteral(']').toFormatter(Locale.getDefault());
    
    public static final ZoneOffset ZONE_OFFSET_UTC = ZoneOffset.UTC;
    public static final ZoneId ZONE_ID_UTC = ZoneId.ofOffset("UTC", ZONE_OFFSET_UTC);
    
    public static long getTimeFloored(long timestamp, long every, TimeUnit unit) {
        return getTimeFloored(Instant.ofEpochMilli(timestamp), every, unit).toEpochMilli();
    }
    
    public static Instant getTimeFloored(Instant instant, long every, TimeUnit unit) {
        if (instant == null) {
            return null;
        }
        if (unit == null || every < 0) {
            return instant;
        }
        switch (unit) {
            case NANOSECONDS:
                return instant.minusNanos(instant.getNano() % every);
            case MICROSECONDS:
            case MILLISECONDS:
            case SECONDS:
            case MINUTES:
            case HOURS:
            case DAYS:
                return instant.minusMillis(instant.toEpochMilli() % unit.toMillis(every));
            default:
                return null;
        }
    }
    
    public static long getTimeCeiled(long timestamp, long every, TimeUnit unit) {
        return getTimeCeiled(Instant.ofEpochMilli(timestamp), every, unit).toEpochMilli();
    }
    
    public static Instant getTimeCeiled(Instant instant, long every, TimeUnit unit) {
        if (instant == null) {
            return null;
        }
        if (unit == null || every < 0) {
            return instant;
        }
        switch (unit) {
            case NANOSECONDS:
                return instant.minusNanos(instant.getNano() % every).plusNanos(unit.toNanos(every));
            case MICROSECONDS:
            case MILLISECONDS:
            case SECONDS:
            case MINUTES:
            case HOURS:
            case DAYS:
                return instant.minusMillis(instant.toEpochMilli() % unit.toMillis(every)).plusMillis(unit.toMillis(every));
            default:
                return null;
        }
    }
    
    public static LocalDateTime localDateTimeOfLocalDateAndMidnight(LocalDate localDate) {
        return localDateTimeOfLocalDateAndLocalTime(localDate, LocalTime.MIDNIGHT);
    }
    
    public static LocalDateTime localDateTimeOfLocalDateAndNoon(LocalDate localDate) {
        return localDateTimeOfLocalDateAndLocalTime(localDate, LocalTime.NOON);
    }
    
    public static LocalDateTime localDateTimeOfTodayAndLocalTime(LocalTime localTime) {
        return localDateTimeOfLocalDateAndLocalTime(LocalDate.now(), localTime);
    }
    
    public static LocalDateTime localDateTimeOfLocalDateAndLocalTime(LocalDate localDate, LocalTime localTime) {
        return LocalDateTime.of(localDate, localTime);
    }
    
    public static LocalDateTime localDateTimeOfInstantZoneIdSystemDefault(Instant instant) {
        return localDateTimeOfInstantAndZoneId(instant, systemDefaultZoneId());
    }
    
    public static LocalDateTime localDateTimeOfInstantZoneIdUTC(Instant instant) {
        return localDateTimeOfInstantAndZoneId(instant, ZONE_ID_UTC);
    }
    
    public static LocalDateTime localDateTimeOfInstantAndZoneId(Instant instant, ZoneId originZoneId) {
        return LocalDateTime.ofInstant(instant, originZoneId);
    }
    
    public static LocalDate localDateOfInstantZoneIdSystemDefault(Instant instant) {
        return localDateOfInstantAndZoneId(instant, systemDefaultZoneId());
    }
    
    public static LocalDate localDateOfInstantZoneIdUTC(Instant instant) {
        return localDateOfInstantAndZoneId(instant, ZONE_ID_UTC);
    }
    
    public static LocalDate localDateOfInstantAndZoneId(Instant instant, ZoneId originZoneId) {
        return localDateTimeOfInstantAndZoneId(instant, originZoneId).toLocalDate();
    }
    
    public static LocalTime localTimeOfInstantZoneIdSystemDefault(Instant instant) {
        return localTimeOfInstantAndZoneId(instant, systemDefaultZoneId());
    }
    
    public static LocalTime localTimeOfInstantZoneIdUTC(Instant instant) {
        return localTimeOfInstantAndZoneId(instant, ZONE_ID_UTC);
    }
    
    public static LocalTime localTimeOfInstantAndZoneId(Instant instant, ZoneId originZoneId) {
        return localDateTimeOfInstantAndZoneId(instant, originZoneId).toLocalTime();
    }
    
    public static ZonedDateTime zonedDateTimeOfInstantZoneIdSystemDefault(Instant instant) {
        return zonedDateTimeOfInstantAndZoneId(instant, systemDefaultZoneId());
    }
    
    public static ZonedDateTime zonedDateTimeOfInstantZoneIdUTC(Instant instant) {
        return zonedDateTimeOfInstantAndZoneId(instant, ZONE_ID_UTC);
    }
    
    public static ZonedDateTime zonedDateTimeOfInstantAndZoneId(Instant instant, ZoneId destinationZoneId) {
        return ZonedDateTime.ofInstant(instant, destinationZoneId);
    }
    
    public static ZoneId systemDefaultZoneId() {
        return ZoneId.systemDefault();
    }
    
}
