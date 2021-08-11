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

import de.codemakers.base.util.AbstractFormatBuilder;
import de.codemakers.base.util.StringUtil;
import org.apache.commons.text.StringSubstitutor;

public class SourceFormatterBuilder extends AbstractFormatBuilder<SourceFormatterBuilder, SourceFormatter> {
    
    public static final String FORMAT_CLASS = "class";
    public static final String FORMAT_VAR_CLASS = StringSubstitutor.DEFAULT_VAR_START + FORMAT_CLASS + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_METHOD = "method";
    public static final String FORMAT_VAR_METHOD = StringSubstitutor.DEFAULT_VAR_START + FORMAT_METHOD + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_FILE = "file";
    public static final String FORMAT_VAR_FILE = StringSubstitutor.DEFAULT_VAR_START + FORMAT_FILE + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_LINE = "line";
    public static final String FORMAT_VAR_LINE = StringSubstitutor.DEFAULT_VAR_START + FORMAT_LINE + StringSubstitutor.DEFAULT_VAR_END;
    
    public SourceFormatterBuilder() {
        super("");
    }
    
    public SourceFormatterBuilder(String format) {
        super(format);
    }
    
    public SourceFormatterBuilder appendClassName() {
        format.append(FORMAT_VAR_CLASS);
        return this;
    }
    
    public SourceFormatterBuilder appendMethodName() {
        format.append(FORMAT_VAR_METHOD);
        return this;
    }
    
    public SourceFormatterBuilder appendFileName() {
        format.append(FORMAT_VAR_FILE);
        return this;
    }
    
    public SourceFormatterBuilder appendLineNumber() {
        format.append(FORMAT_VAR_LINE);
        return this;
    }
    
    @Override
    protected String checkAndCorrectText(String text) {
        return StringUtil.escapeStringSubstitutorVariableCalls(text);
    }
    
    @Override
    public SourceFormatter build() {
        return new SourceFormatter(format.toString());
    }
    
    @Override
    public String example() {
        final StackTraceElement stackTraceElement = new Exception().getStackTrace()[2]; //TODO Verify this
        return build().formatWithoutException(stackTraceElement);
    }
    
    @Override
    public String toString() {
        return "SourceFormatterBuilder{" + "format='" + format + '\'' + ", checkAndCorrectAppendedText=" + checkAndCorrectAppendedText + '}';
    }
    
}
