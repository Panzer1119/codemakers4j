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

package de.codemakers.base.logging.format;

import de.codemakers.base.logging.LogLevelStyle;
import de.codemakers.base.util.TimeUtil;
import org.apache.logging.log4j.Level;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LogFormatUtil {
    
    /**
     * Value = {@link java.time.format.DateTimeFormatter#ISO_OFFSET_DATE_TIME}
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER_ISO_OFFSET = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    /**
     * Value = {@link de.codemakers.base.util.TimeUtil#ISO_OFFSET_DATE_TIME_FIXED_LENGTH}
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER_DEFAULT = TimeUtil.ISO_OFFSET_DATE_TIME_FIXED_LENGTH;
    
    public static String formatTimestamp(Instant timestamp) {
        if (timestamp == null) {
            return "";
        }
        return formatTimestamp(ZonedDateTime.ofInstant(timestamp, TimeUtil.ZONE_ID_UTC));
    }
    
    public static String formatTimestamp(ZonedDateTime timestamp) {
        if (timestamp == null) {
            return "";
        }
        return encaseString(timestamp.format(DATE_TIME_FORMATTER_DEFAULT));
    }
    
    public static String formatLevel(Level level) {
        return encaseString(LogLevelStyle.ofLevel(level).getNameCenter());
    }
    
    public static String formatThread(Thread thread) {
        if (thread == null) {
            return "";
        }
        return encaseString(thread.getName());
    }
    
    public static String formatThread(String threadName) {
        if (threadName == null) {
            return "";
        }
        return encaseString(threadName);
    }
    
    public static String encaseString(String text) {
        return "[" + text + "]";
    }
    
}
