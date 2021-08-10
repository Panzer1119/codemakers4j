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

public enum BatteryState {
    
    FULL(new String[] {"charged", "full"}),
    CHARGING_HIGH(new String[] {"charging and high"}),
    CHARGING_LOW(new String[] {"charging and low"}),
    CHARGING_CRITICAL(new String[] {"charging and critical"}),
    CHARGING(new String[] {"charging"}),
    PARTIALLY_CHARGED(new String[]{"partially charged", "partially full"}),
    DISCHARGING(new String[] {"discharging", "running"}),
    DISCHARGING_LOW(new String[] {"discharging and low"}),
    DISCHARGING_CRITICAL(new String[] {"discharging and critical"}),
    EMPTY(new String[] {"discharged", "empty"}),
    ERROR(new String[] {"error"}),
    UNKNOWN(new String[] {"undefined", null});
    
    private final String[] names;
    
    BatteryState(String[] names) {
        this.names = names;
    }
    
    public static final BatteryState of(String state) {
        Objects.requireNonNull(state);
        final String state_lower = state.toLowerCase();
        for (BatteryState batteryState : values()) {
            for (String name : batteryState.names) {
                if (state.equalsIgnoreCase(name) || state_lower.contains(name)) {
                    return batteryState;
                }
            }
        }
        return null;
    }
    
    public final String[] getNames() {
        return names;
    }
    
}
