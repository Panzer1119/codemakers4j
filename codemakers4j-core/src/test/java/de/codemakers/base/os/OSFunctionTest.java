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

import de.codemakers.base.os.functions.SystemInfo;

public class OSFunctionTest {
    
    public static final void main(String[] args) {
        System.out.println(OSUtil.CURRENT_OS_HELPER.toString());
        System.out.println(OSUtil.LINUX_HELPER.toString());
        System.out.println(OSUtil.MAC_OS_HELPER.toString());
        System.out.println(OSUtil.WINDOWS_HELPER.toString());
        //final double batteryLevel_2 = OSUtil.getFunction(SystemInfo.class).getBatteryInfo().getCharge();
        //System.out.println("Battery Level 2: " + batteryLevel_2);
        System.out.println(OSUtil.getFunction(SystemInfo.class).getPowerInfoWithoutException());
    }
    
}
