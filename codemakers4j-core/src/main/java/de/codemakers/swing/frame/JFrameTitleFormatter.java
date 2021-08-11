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

import de.codemakers.base.util.StringUtil;
import de.codemakers.base.util.interfaces.Formatter;
import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public record JFrameTitleFormatter(String format) implements Formatter<JFrameTitle> {
    /**
     * Value = "{@link JFrameTitleFormatterBuilder#FORMAT_VAR_PREFIX}{@link JFrameTitleFormatterBuilder#FORMAT_VAR_NAME}{@link JFrameTitleFormatterBuilder#FORMAT_VAR_VERSION}{@link JFrameTitleFormatterBuilder#FORMAT_VAR_IDE}{@link JFrameTitleFormatterBuilder#FORMAT_VAR_SUFFIX}"
     */
    public static final String DEFAULT_TITLE_FORMAT = JFrameTitleFormatterBuilder.FORMAT_VAR_PREFIX + JFrameTitleFormatterBuilder.FORMAT_VAR_NAME + " V" + JFrameTitleFormatterBuilder.FORMAT_VAR_VERSION + JFrameTitleFormatterBuilder.FORMAT_VAR_IDE + JFrameTitleFormatterBuilder.FORMAT_VAR_SUFFIX;
    public static final String DEFAULT_PREFIX_OR_SUFFIX_DELIMITER = " - ";
    
    @Override
    public String format(JFrameTitle title) throws Exception {
        return StringSubstitutor.replace(format, createValueMap(title));
    }
    
    private Map<String, Object> createValueMap(JFrameTitle title) {
        final Map<String, Object> map = new HashMap<>();
        map.put(JFrameTitleFormatterBuilder.FORMAT_IDE, title.isIDE() ? " IDE" : "");
        map.put(JFrameTitleFormatterBuilder.FORMAT_NAME, title.getName());
        map.put(JFrameTitleFormatterBuilder.FORMAT_VERSION, title.getVersion());
        map.put(JFrameTitleFormatterBuilder.FORMAT_PREFIX, title.getPrefixes()
                .stream()
                .map(StringUtil::toString)
                .collect(Collectors.joining(DEFAULT_PREFIX_OR_SUFFIX_DELIMITER, "", " ")));
        map.put(JFrameTitleFormatterBuilder.FORMAT_SUFFIX, title.getSuffixes()
                .stream()
                .map(StringUtil::toString)
                .collect(Collectors.joining(DEFAULT_PREFIX_OR_SUFFIX_DELIMITER, " ", "")));
        return map;
    }
    
    public static JFrameTitleFormatter createDefault() {
        return new JFrameTitleFormatterBuilder(DEFAULT_TITLE_FORMAT).build();
    }
    
}
