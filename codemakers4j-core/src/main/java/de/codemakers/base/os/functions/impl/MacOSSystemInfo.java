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

package de.codemakers.base.os.functions.impl;

import de.codemakers.base.os.OS;
import de.codemakers.base.os.functions.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RegisterOSFunction(supported = {OS.MACOS})
public class MacOSSystemInfo extends SystemInfo {
    
    public static final String COMMAND_POWER_INFO = "pmset -g ps";
    public static final String PATTERN_POWER_INFO_STRING = "Now drawing from '(.+)' (.+) \\(id=(\\d+)\\)\t(\\d{1,3})%; (.+); (\\d+:\\d{1,2})(?: remaining present: (\\w+))?";
    public static final Pattern PATTERN_POWER_INFO = Pattern.compile(PATTERN_POWER_INFO_STRING);
    
    @Override
    public PowerInfo getPowerInfo() throws Exception {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(COMMAND_POWER_INFO).getInputStream()));
        final Matcher matcher = PATTERN_POWER_INFO.matcher(bufferedReader.lines().collect(Collectors.joining()));
        bufferedReader.close();
        if (matcher.matches()) {
            return new PowerInfo(matcher.group(3), matcher.group(2), Double.parseDouble(matcher.group(4)) / 100.0, BatteryState.of(matcher.group(5)), -1, null, (long) (Double.parseDouble(matcher.group(6).replace(':', '.')) * 60.0), TimeUnit.MINUTES, PowerSupply.of(matcher.group(1)));
        }
        return null;
    }
    
}
