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

import de.codemakers.base.os.LinuxHelper;
import de.codemakers.base.os.OS;
import de.codemakers.base.os.OSUtil;

import java.io.File;
import java.io.FileReader;
import java.util.Objects;
import java.util.Properties;

@RegisterOSFunction(supported = {OS.LINUX})
public class LinuxSystemInfo extends SystemInfo {
    
    @Override
    public PowerInfo getPowerInfo() throws Exception {
        for (File file : Objects.requireNonNull(LinuxHelper.FOLDER_AC.listFiles((pathname) -> {
            if (!pathname.isDirectory()) {
                return false;
            }
            for (File file : pathname.listFiles()) {
                if (file.getName().equalsIgnoreCase(LinuxHelper.FILE_UEVENT_NAME)) {
                    return true;
                }
            }
            return false;
        }))) {
            final File file_uevent = new File(file.getAbsolutePath() + OSUtil.getCurrentHelper().getFileSeparator() + LinuxHelper.FILE_UEVENT_NAME);
            if (file_uevent.exists()) {
                final PowerSupply powerSupply = PowerSupply.of(file.getName());
                if (powerSupply != PowerSupply.BATTERY) {
                    continue;
                }
                final Properties properties = new Properties();
                try (final FileReader fileReader = new FileReader(file_uevent)) {
                    properties.load(fileReader);
                }
                return new PowerInfo(properties.getProperty(LinuxHelper.POWER_SUPPLY_SERIAL_NUMBER, LinuxHelper.POWER_SUPPLY_SERIAL_NUMBER), properties.getProperty(LinuxHelper.POWER_SUPPLY_NAME, LinuxHelper.POWER_SUPPLY_NAME), (Long.parseLong(properties.getProperty(LinuxHelper.POWER_SUPPLY_ENERGY_NOW, "0")) * 1.0 / (Long.parseLong(properties.getProperty(LinuxHelper.POWER_SUPPLY_ENERGY_FULL, "0")) * 1.0)), BatteryState.of(properties.getProperty(LinuxHelper.POWER_SUPPLY_STATUS)), -1, null, -1, null, powerSupply, properties);
            }
        }
        return null;
    }
    
}
