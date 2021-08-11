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

package de.codemakers.base.os;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WindowsBatteryTest {
    
    public static final File file = new File("win.txt");
    
    public static final Map<String, String> PROPERTIES = new ConcurrentHashMap<>();
    
    public static final void main(String[] args) throws Exception {
        final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        bufferedReader.readLine();
        String line_1 = bufferedReader.readLine();
        String line_2 = bufferedReader.readLine();
        bufferedReader.close();
        process(line_1, line_2);
        System.out.println(PROPERTIES);
        System.out.println(PROPERTIES.keySet());
        PROPERTIES.keySet().forEach((key) -> {
            String key_ = "";
            for (char c : key.toCharArray()) {
                if (!key_.isEmpty() && c >= 'A' && c <= 'Z') {
                    key_ += "_";
                } else if (c >= 'a' && c <= 'z') {
                    c -= 32;
                }
                key_ += c;
            }
            System.out.println(String.format("public static final String %s = \"%s\";", key_, key));
        });
    }
    
    public static final void process(String line_1, String line_2) {
        while (true) {
            if (line_1.isEmpty()) {
                break;
            }
            final int i_1 = line_1.indexOf("  ");
            final int i_2 = line_2.indexOf("  ");
            if (i_1 == -1) {
                PROPERTIES.put(line_1, line_2);
                line_1 = "";
                line_2 = "";
            } else {
                final String key = line_1.substring(0, i_1);
                final String value = line_2.substring(0, i_2 < 0 ? line_2.length() : i_2);
                int i = i_1;
                while (line_1.charAt(i) == ' ') {
                    i++;
                }
                PROPERTIES.put(key, value);
                line_1 = line_1.substring(i);
                line_2 = line_2.substring(Math.min(line_2.length(), i));
            }
        }
    }
    
}
