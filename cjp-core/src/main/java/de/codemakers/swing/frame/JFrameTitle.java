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

import de.codemakers.base.util.interfaces.Formattable;

import java.util.LinkedList;
import java.util.Queue;

public class JFrameTitle implements Formattable<JFrameTitleFormatter> {
    
    private final boolean isIDE;
    private String Name;
    private String version;
    private final Queue<Object> prefixes = new LinkedList<>();
    private final Queue<Object> suffixes = new LinkedList<>();
    
    public JFrameTitle(boolean isIDE, String Name, String version) {
        this.isIDE = isIDE;
        this.Name = Name;
        this.version = version;
    }
    
    public boolean isIDE() {
        return isIDE;
    }
    
    public String getName() {
        return Name;
    }
    
    public JFrameTitle setName(String name) {
        Name = name;
        return this;
    }
    
    public String getVersion() {
        return version;
    }
    
    public JFrameTitle setVersion(String version) {
        this.version = version;
        return this;
    }
    
    public Queue<Object> getPrefixes() {
        return prefixes;
    }
    
    public Queue<Object> getSuffixes() {
        return suffixes;
    }
    
    @Override
    public String format(JFrameTitleFormatter format) throws Exception {
        return format.format(this);
    }
    
    @Override
    public String toString() {
        return "JFrameTitle{" + "isIDE=" + isIDE + ", Name='" + Name + '\'' + ", version='" + version + '\'' + ", prefixes=" + prefixes + ", suffixes=" + suffixes + '}';
    }
    
}
