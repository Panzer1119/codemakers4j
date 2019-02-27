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

import org.apache.commons.text.StringSubstitutor;

import java.time.Instant;
import java.util.Map;

public abstract class AdvancedLeveledLogger extends AdvancedLogger {
    
    public static final String LOG_FORMAT_LOG_LEVEL = "loglevel"; //FIXME TODO 1242545435
    public static final String LOG_FORMAT_VAR_LOG_LEVEL = StringSubstitutor.DEFAULT_VAR_START + LOG_FORMAT_LOG_LEVEL + StringSubstitutor.DEFAULT_VAR_END; //FIXME TODO 1242545435
    /**
     * Value = "{@link #LOG_FORMAT_VAR_TIMESTAMP}{@link #LOG_FORMAT_VAR_THREAD}{@link #LOG_FORMAT_VAR_LOCATION}{@link #LOG_FORMAT_VAR_LOG_LEVEL}: {@link #LOG_FORMAT_VAR_OBJECT}"
     */
    public static final String DEFAULT_LEVELED_LOG_FORMAT = LOG_FORMAT_VAR_TIMESTAMP + LOG_FORMAT_VAR_THREAD + LOG_FORMAT_VAR_LOCATION + LOG_FORMAT_VAR_LOG_LEVEL + ": " + LOG_FORMAT_VAR_OBJECT;
    
    protected LogLevel minimumLogLevel = LogLevel.INFO;
    
    public AdvancedLeveledLogger() {
        //logFormatter.setFormatString(DEFAULT_LEVELED_LOG_FORMAT); //FIXME TODO 1242545435
        this.logFormat = DEFAULT_LEVELED_LOG_FORMAT;
    }
    
    @Override
    public void log(Object object) {
        log(object, Instant.now(), Thread.currentThread(), cutStackTrace(new Exception().getStackTrace()), LogLevel.INFO);
    }
    
    @Override
    public void log(Object object, Instant timestamp) {
        log(object, timestamp, Thread.currentThread(), cutStackTrace(new Exception().getStackTrace()), LogLevel.INFO);
    }
    
    @Override
    public void log(Object object, Instant timestamp, Thread thread) {
        log(object, timestamp, thread, cutStackTrace(new Exception().getStackTrace()), LogLevel.INFO);
    }
    
    @Override
    public void log(Object object, Instant timestamp, Thread thread, StackTraceElement stackTraceElement) {
        log(object, timestamp, thread, stackTraceElement, LogLevel.INFO);
    }
    
    public void log(Object object, Instant timestamp, Thread thread, StackTraceElement stackTraceElement, LogLevel logLevel) {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
        if (minimumLogLevel.isThisLevelMoreImportant(logLevel)) {
            return;
        }
        logFinal(StringSubstitutor.replace(logFormat, createValueMap(object, timestamp, thread, stackTraceElement, logLevel)));
    }
    
    @Override
    public void logError(Object object, Throwable throwable) {
        logError(object, throwable, Instant.now(), Thread.currentThread(), cutStackTrace(new Exception().getStackTrace()), LogLevel.ERROR);
    }
    
    @Override
    public void logError(Object object, Throwable throwable, Instant timestamp) {
        logError(object, throwable, timestamp, Thread.currentThread(), cutStackTrace(new Exception().getStackTrace()), LogLevel.ERROR);
    }
    
    @Override
    public void logError(Object object, Throwable throwable, Instant timestamp, Thread thread) {
        logError(object, throwable, timestamp, thread, cutStackTrace(new Exception().getStackTrace()), LogLevel.ERROR);
    }
    
    @Override
    public void logError(Object object, Throwable throwable, Instant timestamp, Thread thread, StackTraceElement stackTraceElement) {
        logError(object, throwable, timestamp, thread, stackTraceElement, LogLevel.ERROR);
    }
    
    public void logError(Object object, Throwable throwable, Instant timestamp, Thread thread, StackTraceElement stackTraceElement, LogLevel logLevel) {
        if (timestamp == null) {
            timestamp = Instant.now();
        }
        if (minimumLogLevel.isThisLevelMoreImportant(logLevel)) {
            return;
        }
        //logErrorFinal(logFormatter.toString(), throwable); //FIXME TODO 1242545435
        logErrorFinal(StringSubstitutor.replace(logFormat, createValueMap(object, timestamp, thread, stackTraceElement, logLevel)), throwable);
    }
    
    protected Map<String, Object> createValueMap(Object object, Instant timestamp, Thread thread, StackTraceElement stackTraceElement, LogLevel logLevel) {
        final Map<String, Object> map = createValueMap(object, timestamp, thread, stackTraceElement);
        map.put(LOG_FORMAT_LOG_LEVEL, formatLogLevel(logLevel));
        return map;
    }
    
    protected String formatLogLevel(LogLevel logLevel) {
        if (logLevel == null) {
            return "";
        }
        return "[" + logLevel + "]";
    }
    
    public LogLevel getMinimumLogLevel() {
        return minimumLogLevel;
    }
    
    public AdvancedLeveledLogger setMinimumLogLevel(LogLevel minimumLogLevel) {
        if (minimumLogLevel == null) {
            minimumLogLevel = LogLevel.INFO;
        }
        this.minimumLogLevel = minimumLogLevel;
        return this;
    }
    
    /**
     * Gets the {@link java.lang.String} used to format the log Message
     * <br>
     * Default format is {@link de.codemakers.base.logger.AdvancedLeveledLogger#DEFAULT_LEVELED_LOG_FORMAT}
     *
     * @return Log format
     */
    @Override
    public final String getLogFormat() {
        return super.getLogFormat();
    }
    
    /**
     * Sets the {@link java.lang.String} used to format the log Message
     * <br>
     * Default format is {@link de.codemakers.base.logger.AdvancedLeveledLogger#DEFAULT_LEVELED_LOG_FORMAT}
     *
     * @param logFormat Log format
     *
     * @return A reference to this {@link de.codemakers.base.logger.AdvancedLeveledLogger} object
     */
    @Override
    public AdvancedLeveledLogger setLogFormat(String logFormat) {
        return (AdvancedLeveledLogger) super.setLogFormat(logFormat);
    }
    
}
