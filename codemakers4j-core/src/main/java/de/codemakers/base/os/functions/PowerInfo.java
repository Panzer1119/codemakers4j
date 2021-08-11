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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class PowerInfo {
    
    private final String id;
    private final String name;
    private final double charge;
    private final BatteryState state;
    private final long remaining_time_discharge;
    private final TimeUnit remaining_timeUnit_discharge;
    private final long remaining_time_charge;
    private final TimeUnit remaining_timeUnit_charge;
    private final PowerSupply powerSupply;
    private Object[] extra = null;
    
    public PowerInfo(String id, String name, double charge, BatteryState state, long remaining_time_discharge, TimeUnit remaining_timeUnit_discharge, long remaining_time_charge, TimeUnit remaining_timeUnit_charge, PowerSupply powerSupply, Object... extra) {
        this.id = id;
        this.name = name;
        this.charge = charge;
        this.state = state;
        this.remaining_time_discharge = remaining_time_discharge;
        this.remaining_timeUnit_discharge = remaining_timeUnit_discharge;
        this.remaining_time_charge = remaining_time_charge;
        this.remaining_timeUnit_charge = remaining_timeUnit_charge;
        this.powerSupply = powerSupply;
        this.extra = extra;
    }
    
    public final String getId() {
        return id;
    }
    
    public final String getName() {
        return name;
    }
    
    /**
     * Returns Battery Level in percentage
     *
     * @return Battery Level (0.0 = Empty, 1.0 = Full, -1.0 = No information available)
     */
    public final double getCharge() {
        return charge;
    }
    
    public final BatteryState getState() {
        return state;
    }
    
    public final long getRemainingDischargeTime() {
        return remaining_time_discharge;
    }
    
    public final TimeUnit getRemainingDischargeTimeUnit() {
        return remaining_timeUnit_discharge;
    }
    
    public final long getRemainingChargeTime() {
        return remaining_time_charge;
    }
    
    public final TimeUnit getRemainingChargeTimeUnit() {
        return remaining_timeUnit_charge;
    }
    
    public final PowerSupply getPowerSupply() {
        return powerSupply;
    }
    
    public final Object[] getExtra() {
        return extra;
    }
    
    @Override
    public final String toString() {
        return "PowerInfo{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", charge=" + charge + ", state=" + state + ", remaining_time_discharge=" + remaining_time_discharge + ", remaining_timeUnit_discharge=" + remaining_timeUnit_discharge + ", remaining_time_charge=" + remaining_time_charge + ", remaining_timeUnit_charge=" + remaining_timeUnit_charge + ", powerSupply=" + powerSupply + ", extra=" + Arrays.toString(extra) + '}';
    }
    
}
