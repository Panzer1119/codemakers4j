/*
 *     Copyright 2018 - 2020 Paul Hagedorn (Panzer1119)
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

package de.codemakers;/*
 *     Copyright 2018 Paul Hagedorn (Panzer1119)
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommandExecTest {
    
    public static final String PATTERN_STRING = "Now drawing from '(.+)' (.+) \\(id=(\\d+)\\)\t(\\d{1,3})%; (.+); (\\d+:\\d{1,2})(?: remaining present: (\\w+))?";
    public static final Pattern PATTERN = Pattern.compile(PATTERN_STRING);

    public static final void main(String[] args) throws Exception {
        final Process process = Runtime.getRuntime().exec("pmset -g ps");
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final String lines = bufferedReader.lines().collect(Collectors.joining());
        System.out.println(lines);
        bufferedReader.close();
        final Matcher matcher = PATTERN.matcher(lines);
        if (matcher.matches()) {
            for (int i = 1; i < 8; i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        } else {
            System.out.println("Didn't match!");
        }
    }

}
