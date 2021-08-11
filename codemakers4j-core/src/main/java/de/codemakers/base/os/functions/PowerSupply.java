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

package de.codemakers.base.os.functions;

import java.util.Objects;

public enum PowerSupply {
    
    BATTERY(new String[] {"battery", "bat", "bata", "batta"}),
    AC_POWER(new String[] {"ac power", "ac", "acpower"}),
    USV(new String[] {"ups", "usv", "external"}),
    UNKNOWN(new String[] {"undefined", null});
    
    private final String[] names;
    
    PowerSupply(String[] names) {
        this.names = names;
    }
    
    public static final PowerSupply of(String state) {
        Objects.requireNonNull(state);
        final String state_lower = state.toLowerCase();
        for (PowerSupply powerSupply : values()) {
            for (String name : powerSupply.names) {
                if (state.equalsIgnoreCase(name) || state_lower.contains(name)) {
                    return powerSupply;
                }
            }
        }
        return null;
    }
    
    public final String[] getNames() {
        return names;
    }
}
