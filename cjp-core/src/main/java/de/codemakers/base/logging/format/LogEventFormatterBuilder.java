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
import de.codemakers.base.util.AbstractFormatBuilder;
import de.codemakers.base.util.StringUtil;
import de.codemakers.base.util.TimeUtil;
import de.codemakers.base.util.interfaces.Formatter;
import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.message.Message;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class LogEventFormatterBuilder extends AbstractFormatBuilder<LogEventFormatterBuilder, LogEventFormatter> {
    
    public static final String FORMAT_TIMESTAMP = "timestamp";
    public static final String FORMAT_VAR_TIMESTAMP = StringSubstitutor.DEFAULT_VAR_START + FORMAT_TIMESTAMP + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_LEVEL = "level";
    public static final String FORMAT_VAR_LEVEL = StringSubstitutor.DEFAULT_VAR_START + FORMAT_LEVEL + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_THREAD = "thread";
    public static final String FORMAT_VAR_THREAD = StringSubstitutor.DEFAULT_VAR_START + FORMAT_THREAD + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_SOURCE = "source";
    public static final String FORMAT_VAR_SOURCE = StringSubstitutor.DEFAULT_VAR_START + FORMAT_SOURCE + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_OBJECT = "object";
    public static final String FORMAT_VAR_OBJECT = StringSubstitutor.DEFAULT_VAR_START + FORMAT_OBJECT + StringSubstitutor.DEFAULT_VAR_END;
    
    private Formatter<Instant> timestampFormatter = LogFormatUtil::formatTimestamp;
    private Formatter<Level> levelFormatter = LogFormatUtil::formatLevel;
    private Formatter<String> threadFormatter = LogFormatUtil::formatThread;
    private SourceFormatter sourceFormatter = SourceFormatter.createDefault();
    private Formatter<Message> messageFormatter = Message::getFormattedMessage;
    
    public LogEventFormatterBuilder() {
        super();
    }
    
    public LogEventFormatterBuilder(String format) {
        super(format);
    }
    
    public LogEventFormatterBuilder appendTimestamp() {
        format.append(FORMAT_VAR_TIMESTAMP);
        return this;
    }
    
    public LogEventFormatterBuilder appendLogLevel() {
        format.append(FORMAT_VAR_LEVEL);
        return this;
    }
    
    public LogEventFormatterBuilder appendThread() {
        format.append(FORMAT_VAR_THREAD);
        return this;
    }
    
    public LogEventFormatterBuilder appendSource() {
        format.append(FORMAT_VAR_SOURCE);
        return this;
    }
    
    public LogEventFormatterBuilder appendObject() {
        format.append(FORMAT_VAR_OBJECT);
        return this;
    }
    
    public Formatter<Instant> getTimestampFormatter() {
        return timestampFormatter;
    }
    
    public LogEventFormatterBuilder setTimestampFormatter(Formatter<Instant> timestampFormatter) {
        this.timestampFormatter = timestampFormatter;
        return this;
    }
    
    public Formatter<Level> getLevelFormatter() {
        return levelFormatter;
    }
    
    public LogEventFormatterBuilder setLevelFormatter(Formatter<Level> levelFormatter) {
        this.levelFormatter = levelFormatter;
        return this;
    }
    
    public Formatter<String> getThreadFormatter() {
        return threadFormatter;
    }
    
    public LogEventFormatterBuilder setThreadFormatter(Formatter<String> threadFormatter) {
        this.threadFormatter = threadFormatter;
        return this;
    }
    
    public SourceFormatter getSourceFormatter() {
        return sourceFormatter;
    }
    
    public LogEventFormatterBuilder setSourceFormatter(SourceFormatter sourceFormatter) {
        this.sourceFormatter = sourceFormatter;
        return this;
    }
    
    public Formatter<Message> getMessageFormatter() {
        return messageFormatter;
    }
    
    public LogEventFormatterBuilder setMessageFormatter(Formatter<Message> messageFormatter) {
        this.messageFormatter = messageFormatter;
        return this;
    }
    
    @Override
    protected String checkAndCorrectText(String text) {
        return StringUtil.escapeStringSubstitutorVariableCalls(text);
    }
    
    @Override
    public LogEventFormatter build() {
        return new LogEventFormatter(format.toString(), timestampFormatter, levelFormatter, threadFormatter, sourceFormatter, messageFormatter);
    }
    
    @Override
    public String example() {
        return example(null);
    }
    
    @Deprecated
    private String example(SourceFormatterBuilder sourceFormatterBuilder) {
        final Map<String, Object> map = new HashMap<>();
        map.put(FORMAT_TIMESTAMP, StringUtil.escapeStringSubstitutorVariableCalls("[" + ZonedDateTime.now()
                .format(TimeUtil.ISO_OFFSET_DATE_TIME_FIXED_LENGTH) + "]"));
        map.put(FORMAT_THREAD, StringUtil.escapeStringSubstitutorVariableCalls("[" + Thread.currentThread().getName() + "]"));
        map.put(FORMAT_SOURCE, sourceFormatterBuilder == null ? StringSubstitutor.DEFAULT_ESCAPE + FORMAT_VAR_SOURCE : StringUtil.escapeStringSubstitutorVariableCalls(sourceFormatterBuilder
                .example()));
        map.put(FORMAT_LEVEL, "[" + LogLevelStyle.DEBUG.getNameCenter() + "]");
        map.put(FORMAT_OBJECT, "This is an example message");
        return StringSubstitutor.replace(format, map);
    }
    
    @Override
    public String toString() {
        return "LogEventFormatterBuilder{" + "timestampFormatter=" + timestampFormatter + ", levelFormatter=" + levelFormatter + ", threadFormatter=" + threadFormatter + ", sourceFormatter=" + sourceFormatter + ", messageFormatter=" + messageFormatter + ", format=" + format + ", checkAndCorrectAppendedText=" + checkAndCorrectAppendedText + '}';
    }
    
}
