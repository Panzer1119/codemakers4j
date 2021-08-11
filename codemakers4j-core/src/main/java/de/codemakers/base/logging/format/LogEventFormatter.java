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

import de.codemakers.base.util.StringUtil;
import de.codemakers.base.util.interfaces.Formatter;
import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public record LogEventFormatter(String format, Formatter<Instant> timestampFormatter, Formatter<Level> levelFormatter, Formatter<String> threadFormatter,
                                SourceFormatter sourceFormatter, Formatter<Message> messageFormatter) implements Formatter<LogEvent> {
    
    /**
     * Value = "{@link LogEventFormatterBuilder#FORMAT_VAR_TIMESTAMP}{@link LogEventFormatterBuilder#FORMAT_VAR_LEVEL}{@link LogEventFormatterBuilder#FORMAT_VAR_THREAD}: {@link LogEventFormatterBuilder#FORMAT_VAR_OBJECT}"
     */
    public static final String DEFAULT_FORMAT = LogEventFormatterBuilder.FORMAT_VAR_TIMESTAMP + LogEventFormatterBuilder.FORMAT_VAR_LEVEL + LogEventFormatterBuilder.FORMAT_VAR_THREAD + ": " + LogEventFormatterBuilder.FORMAT_VAR_OBJECT;
    /**
     * Value = "{@link LogEventFormatterBuilder#FORMAT_VAR_TIMESTAMP}{@link LogEventFormatterBuilder#FORMAT_VAR_LEVEL}{@link LogEventFormatterBuilder#FORMAT_VAR_THREAD}{@link LogEventFormatterBuilder#FORMAT_VAR_SOURCE}: {@link LogEventFormatterBuilder#FORMAT_VAR_OBJECT}"
     */
    public static final String DEFAULT_FORMAT_WITH_SOURCE = LogEventFormatterBuilder.FORMAT_VAR_TIMESTAMP + LogEventFormatterBuilder.FORMAT_VAR_LEVEL + LogEventFormatterBuilder.FORMAT_VAR_THREAD + LogEventFormatterBuilder.FORMAT_VAR_SOURCE + ": " + LogEventFormatterBuilder.FORMAT_VAR_OBJECT;
    
    @Override
    public String format(LogEvent logEvent) throws Exception {
        return StringSubstitutor.replace(format, createValueMap(logEvent));
    }
    
    private Map<String, Object> createValueMap(LogEvent logEvent) { //FIXME Clone/Copy LogEvent before saving it in the Console
        final Map<String, Object> map = new HashMap<>();
        final Instant timestamp = Instant.ofEpochMilli(logEvent.getInstant().getEpochMillisecond());
        map.put(LogEventFormatterBuilder.FORMAT_TIMESTAMP, formatTimestamp(timestamp));
        map.put(LogEventFormatterBuilder.FORMAT_LEVEL, formatLevel(logEvent.getLevel()));
        map.put(LogEventFormatterBuilder.FORMAT_THREAD, formatThread(logEvent.getThreadName()));
        map.put(LogEventFormatterBuilder.FORMAT_SOURCE, formatStackTraceElement(logEvent.getSource()));
        map.put(LogEventFormatterBuilder.FORMAT_OBJECT, formatMessage(logEvent.getMessage()));
        return map;
    }
    
    private String formatTimestamp(Instant timestamp) {
        return StringUtil.escapeStringSubstitutorVariableCalls(timestampFormatter.formatWithoutException(timestamp));
    }
    
    private String formatThread(String threadName) {
        return StringUtil.escapeStringSubstitutorVariableCalls(threadFormatter.formatWithoutException(threadName));
    }
    
    private String formatStackTraceElement(StackTraceElement stackTraceElement) {
        return StringUtil.escapeStringSubstitutorVariableCalls(sourceFormatter.formatWithoutException(stackTraceElement));
    }
    
    private String formatLevel(Level level) {
        return StringUtil.escapeStringSubstitutorVariableCalls(levelFormatter.formatWithoutException(level));
    }
    
    private String formatMessage(Message message) {
        return StringUtil.escapeStringSubstitutorVariableCalls(messageFormatter.formatWithoutException(message));
    }
    
    @Override
    public String toString() {
        return "LogEventFormatter{" + "format='" + format + '\'' + '}';
    }
    
    public static LogEventFormatter createDefault() {
        return new LogEventFormatterBuilder(DEFAULT_FORMAT).build();
    }
    
    public static LogEventFormatter createDefaultWithSource() {
        return new LogEventFormatterBuilder(DEFAULT_FORMAT_WITH_SOURCE).build();
    }
    
}
