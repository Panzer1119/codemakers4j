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

import de.codemakers.base.util.interfaces.Formatter;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;

public record SourceFormatter(String format) implements Formatter<StackTraceElement> {
    
    /**
     * Value = "{@link SourceFormatterBuilder#FORMAT_VAR_CLASS}.{@link SourceFormatterBuilder#FORMAT_VAR_METHOD}({@link SourceFormatterBuilder#FORMAT_VAR_FILE}:{@link SourceFormatterBuilder#FORMAT_VAR_LINE})"
     */
    public static final String DEFAULT_FORMAT = SourceFormatterBuilder.FORMAT_VAR_CLASS + "." + SourceFormatterBuilder.FORMAT_VAR_METHOD + "(" + SourceFormatterBuilder.FORMAT_VAR_FILE + ":" + SourceFormatterBuilder.FORMAT_VAR_LINE + ")";
    
    @Override
    public String format(StackTraceElement stackTraceElement) throws Exception {
        return LogFormatUtil.encaseString(StringSubstitutor.replace(format, createValueMap(stackTraceElement)));
    }
    
    private Map<String, Object> createValueMap(StackTraceElement stackTraceElement) {
        final Map<String, Object> map = new HashMap<>();
        map.put(SourceFormatterBuilder.FORMAT_CLASS, stackTraceElement.getClassName());
        map.put(SourceFormatterBuilder.FORMAT_METHOD, stackTraceElement.getMethodName());
        map.put(SourceFormatterBuilder.FORMAT_FILE, stackTraceElement.getFileName());
        map.put(SourceFormatterBuilder.FORMAT_LINE, stackTraceElement.getLineNumber());
        return map;
    }
    
    @Override
    public String toString() {
        return "SourceFormatter{" + "format='" + format + '\'' + '}';
    }
    
    public static SourceFormatter createDefault() {
        return new SourceFormatter(DEFAULT_FORMAT);
    }
    
}
