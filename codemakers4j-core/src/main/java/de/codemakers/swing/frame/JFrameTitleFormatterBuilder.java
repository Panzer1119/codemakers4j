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

package de.codemakers.swing.frame;

import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.base.util.AbstractFormatBuilder;
import de.codemakers.base.util.StringUtil;
import org.apache.commons.text.StringSubstitutor;

public class JFrameTitleFormatterBuilder extends AbstractFormatBuilder<JFrameTitleFormatterBuilder, JFrameTitleFormatter> {
    
    public static final String FORMAT_NAME = "name";
    public static final String FORMAT_VAR_NAME = StringSubstitutor.DEFAULT_VAR_START + FORMAT_NAME + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_VERSION = "version";
    public static final String FORMAT_VAR_VERSION = StringSubstitutor.DEFAULT_VAR_START + FORMAT_VERSION + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_IDE = "ide"; //TODO Rename this to "ide_state" or something similar?
    public static final String FORMAT_VAR_IDE = StringSubstitutor.DEFAULT_VAR_START + FORMAT_IDE + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_PREFIX = "prefix";
    public static final String FORMAT_VAR_PREFIX = StringSubstitutor.DEFAULT_VAR_START + FORMAT_PREFIX + StringSubstitutor.DEFAULT_VAR_END;
    public static final String FORMAT_SUFFIX = "suffix";
    public static final String FORMAT_VAR_SUFFIX = StringSubstitutor.DEFAULT_VAR_START + FORMAT_SUFFIX + StringSubstitutor.DEFAULT_VAR_END;
    
    public JFrameTitleFormatterBuilder() {
        super("");
    }
    
    public JFrameTitleFormatterBuilder(String format) {
        super(format);
    }
    
    public JFrameTitleFormatterBuilder appendName() {
        format.append(FORMAT_VAR_NAME);
        return this;
    }
    
    public JFrameTitleFormatterBuilder appendVersion() {
        format.append(FORMAT_VAR_VERSION);
        return this;
    }
    
    public JFrameTitleFormatterBuilder appendIDE() {
        format.append(FORMAT_VAR_IDE);
        return this;
    }
    
    public JFrameTitleFormatterBuilder appendPrefix() {
        format.append(FORMAT_VAR_PREFIX);
        return this;
    }
    
    public JFrameTitleFormatterBuilder appendSuffix() {
        format.append(FORMAT_VAR_SUFFIX);
        return this;
    }
    
    @Override
    protected String checkAndCorrectText(String text) {
        return StringUtil.escapeStringSubstitutorVariableCalls(text);
    }
    
    @Override
    public String example() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public JFrameTitleFormatter build() {
        return new JFrameTitleFormatter(format.toString());
    }
    
    @Override
    public String toString() {
        return "JFrameTitleFormatterBuilder{" + "format='" + format + '\'' + ", checkAndCorrectAppendedText=" + checkAndCorrectAppendedText + '}';
    }
    
}
