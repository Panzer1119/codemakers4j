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

package de.codemakers.base.logging;

import de.codemakers.i18n.I18nUtil;
import org.apache.logging.log4j.Level;

import java.util.Comparator;
import java.util.stream.Stream;

public final class LogLevel {
    
    public static final Level OFF = Level.OFF;
    public static final Level FATAL = Level.FATAL;
    public static final Level ERROR = Level.ERROR;
    public static final Level WARN = Level.WARN;
    public static final Level COMMAND = Level.forName("COMMAND", 340);
    public static final Level INPUT = Level.forName("INPUT", 350);
    public static final Level INFO = Level.INFO;
    public static final Level DEBUG = Level.DEBUG;
    public static final Level FINE = Level.forName("FINE", 540);
    public static final Level FINER = Level.forName("FINER", 550);
    public static final Level FINEST = Level.forName("FINEST", 560);
    public static final Level TRACE = Level.TRACE;
    public static final Level ALL = Level.ALL;
    
    public static final Level[] USED_LEVELS = {FATAL, ERROR, WARN, COMMAND, INPUT, INFO, DEBUG, FINE, FINER, FINEST, TRACE};
    public static final Level MINIMUM_LEVEL = Stream.of(USED_LEVELS).min(Comparator.comparing(Level::intLevel)).orElse(Level.OFF);
    public static final Level MAXIMUM_LEVEL = Stream.of(USED_LEVELS).max(Comparator.comparing(Level::intLevel)).orElse(Level.ALL);
    public static final int MINIMUM_NAME_LENGTH = Stream.of(USED_LEVELS).map(Level::name).map(String::length).min(Integer::compareTo).orElse(Integer.MAX_VALUE);
    public static final int MAXIMUM_NAME_LENGTH = Stream.of(USED_LEVELS).map(Level::name).map(String::length).max(Integer::compareTo).orElse(Integer.MIN_VALUE);
    
    private LogLevel() {
        //Nothing
    }
    
    public static String getLocalizedName(Level level) {
        if (level == null) {
            return I18nUtil.getResourceBundleLogLevel().getString("unknown");
        }
        return I18nUtil.getResourceBundleLogLevel().getString(level.name().toLowerCase());
    }
    
}
