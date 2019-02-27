/*
 *     Copyright 2018 - 2019 Paul Hagedorn (Panzer1119)
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

package de.codemakers.base.logger;

import de.codemakers.base.util.StringUtil;
import de.codemakers.base.util.TimeUtil;
import org.apache.commons.text.StringSubstitutor;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class LogFormatBuilder {
    
    protected String format;
    protected boolean checkAndCorrectAppendedText = true;
    
    public LogFormatBuilder() {
        this("");
    }
    
    public LogFormatBuilder(String format) {
        this.format = format;
    }
    
    public LogFormatBuilder appendText(String text) {
        if (checkAndCorrectAppendedText) {
            text = StringUtil.escapeStringSubstitutorVariableCalls(text);
        }
        format += text;
        return this;
    }
    
    public LogFormatBuilder appendTimestamp() {
        format += Logger.LOG_FORMAT_VAR_TIMESTAMP;
        return this;
    }
    
    public LogFormatBuilder appendThread() {
        format += Logger.LOG_FORMAT_VAR_THREAD;
        return this;
    }
    
    public LogFormatBuilder appendLocation() {
        format += Logger.LOG_FORMAT_VAR_LOCATION;
        return this;
    }
    
    public LogFormatBuilder appendLogLevel() {
        format += Logger.LOG_FORMAT_VAR_LOG_LEVEL;
        return this;
    }
    
    public LogFormatBuilder appendObject() {
        format += Logger.LOG_FORMAT_VAR_OBJECT;
        return this;
    }
    
    public String example() {
        return example(null);
    }
    
    public String example(LocationFormatBuilder locationFormatBuilder) {
        final Map<String, Object> map = new HashMap<>();
        map.put(Logger.LOG_FORMAT_TIMESTAMP, StringUtil.escapeStringSubstitutorVariableCalls("[" + ZonedDateTime.now().format(TimeUtil.ISO_OFFSET_DATE_TIME_FIXED_LENGTH) + "]"));
        map.put(Logger.LOG_FORMAT_THREAD, StringUtil.escapeStringSubstitutorVariableCalls("[" + Thread.currentThread().getName() + "]"));
        map.put(Logger.LOG_FORMAT_LOCATION, locationFormatBuilder == null ? StringSubstitutor.DEFAULT_ESCAPE + Logger.LOG_FORMAT_VAR_LOCATION : StringUtil.escapeStringSubstitutorVariableCalls(locationFormatBuilder.example()));
        map.put(Logger.LOG_FORMAT_LOG_LEVEL, "[" + LogLevel.DEBUG.getNameMid() + "]");
        map.put(Logger.LOG_FORMAT_OBJECT, "This is an example message");
        return StringSubstitutor.replace(format, map);
    }
    
    public String toFormat() {
        return format;
    }
    
    public LogFormatBuilder setFormat(String format) {
        this.format = format;
        return this;
    }
    
    public boolean isCheckAndCorrectAppendedText() {
        return checkAndCorrectAppendedText;
    }
    
    public LogFormatBuilder setCheckAndCorrectAppendedText(boolean checkAndCorrectAppendedText) {
        this.checkAndCorrectAppendedText = checkAndCorrectAppendedText;
        return this;
    }
    
    @Override
    public String toString() {
        return "LogFormatBuilder{" + "format='" + format + '\'' + ", checkAndCorrectAppendedText=" + checkAndCorrectAppendedText + '}';
    }
    
}
