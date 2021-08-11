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
import de.codemakers.base.os.WindowsHelper;
import de.codemakers.base.os.functions.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@RegisterOSFunction(supported = {OS.WINDOWS})
public class WindowsSystemInfo extends SystemInfo {
    
    public static final String COMMAND_POWER_INFO = "WMIC path Win32_Battery";
    
    public static final String POWER_INFO_BATTERY_DESIGN_CAPACITY = "DesignCapacity";
    public static final String POWER_INFO_BATTERY_DESCRIPTION = "Description";
    public static final String POWER_INFO_BATTERY_SYSTEM_CREATION_CLASS_NAME = "SystemCreationClassName";
    public static final String POWER_INFO_BATTERY_CONFIG_MANAGER_USER_CONFIG = "ConfigManagerUserConfig";
    public static final String POWER_INFO_BATTERY_ERROR_CLEARED = "ErrorCleared";
    public static final String POWER_INFO_BATTERY_BATTERY_RECHARGE_TIME = "BatteryRechargeTime";
    public static final String POWER_INFO_BATTERY_ESTIMATED_CHARGE_REMAINING = "EstimatedChargeRemaining";
    public static final String POWER_INFO_BATTERY_NAME = "Name";
    public static final String POWER_INFO_BATTERY_SMART_BATTERY_VERSION = "SmartBatteryVersion";
    public static final String POWER_INFO_BATTERY_TIME_ON_BATTERY = "TimeOnBattery";
    public static final String POWER_INFO_BATTERY_PNP_DEVICE_ID = "PNPDeviceID";
    public static final String POWER_INFO_BATTERY_DESIGN_VOLTAGE = "DesignVoltage";
    public static final String POWER_INFO_BATTERY_POWER_MANAGEMENT_SUPPORTED = "PowerManagementSupported";
    public static final String POWER_INFO_BATTERY_SYSTEM_NAME = "SystemName";
    public static final String POWER_INFO_BATTERY_AVAILABILITY = "Availability";
    public static final String POWER_INFO_BATTERY_ERROR_DESCRIPTION = "ErrorDescription";
    public static final String POWER_INFO_BATTERY_STATUS = "Status";
    public static final String POWER_INFO_BATTERY_MAX_RECHARGE_TIME = "MaxRechargeTime";
    public static final String POWER_INFO_BATTERY_DEVICE_ID = "DeviceID";
    public static final String POWER_INFO_BATTERY_EXPECTED_LIFE = "ExpectedLife";
    public static final String POWER_INFO_BATTERY_EXPECTED_BATTERY_LIFE = "ExpectedBatteryLife";
    public static final String POWER_INFO_BATTERY_INSTALL_DATE = "InstallDate";
    public static final String POWER_INFO_BATTERY_TIME_TO_FULL_CHARGE = "TimeToFullCharge";
    public static final String POWER_INFO_BATTERY_CAPTION = "Caption";
    public static final String POWER_INFO_BATTERY_CHEMISTRY = "Chemistry";
    public static final String POWER_INFO_BATTERY_CONFIG_MANAGER_ERROR_CODE = "ConfigManagerErrorCode";
    public static final String POWER_INFO_BATTERY_POWER_MANAGEMENT_CAPABILITIES = "PowerManagementCapabilities";
    public static final String POWER_INFO_BATTERY_BATTERY_STATUS = "BatteryStatus";
    public static final String POWER_INFO_BATTERY_STATUS_INFO = "StatusInfo";
    public static final String POWER_INFO_BATTERY_CREATION_CLASS_NAME = "CreationClassName";
    public static final String POWER_INFO_BATTERY_FULL_CHARGE_CAPACITY = "FullChargeCapacity";
    public static final String POWER_INFO_BATTERY_LAST_ERROR_CODE = "LastErrorCode";
    public static final String POWER_INFO_BATTERY_ESTIMATED_RUN_TIME = "EstimatedRunTime";
    
    @Override
    public PowerInfo getPowerInfo() throws Exception {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime()
                .exec(COMMAND_POWER_INFO)
                .getInputStream()));
        final String line_1 = bufferedReader.readLine();
        bufferedReader.readLine();
        final String line_2 = bufferedReader.readLine();
        bufferedReader.close();
        if (line_1.isEmpty() || line_2.isEmpty()) {
            return null;
        }
        final Properties properties = new Properties();
        WindowsHelper.process(line_1, line_2, properties);
        final int batteryState_int = Integer.parseInt(properties.getProperty(POWER_INFO_BATTERY_BATTERY_STATUS));
        BatteryState batteryState = null;
        switch (batteryState_int) {
            case 1:
                batteryState = BatteryState.ERROR;
                break;
            case 3:
                batteryState = BatteryState.FULL;
                break;
            case 4:
                batteryState = BatteryState.DISCHARGING_LOW;
                break;
            case 5:
                batteryState = BatteryState.DISCHARGING_CRITICAL;
                break;
            case 6:
                batteryState = BatteryState.CHARGING;
                break;
            case 7:
                batteryState = BatteryState.CHARGING_HIGH;
                break;
            case 8:
                batteryState = BatteryState.CHARGING_LOW;
                break;
            case 9:
                batteryState = BatteryState.CHARGING_CRITICAL;
                break;
            case 11:
                batteryState = BatteryState.PARTIALLY_CHARGED;
                break;
            case 2:
            case 10:
            default:
                batteryState = BatteryState.UNKNOWN;
                break;
        }
        return new PowerInfo(properties.getProperty(POWER_INFO_BATTERY_DEVICE_ID, POWER_INFO_BATTERY_DEVICE_ID), properties.getProperty(POWER_INFO_BATTERY_NAME, POWER_INFO_BATTERY_NAME), Double
                .parseDouble(properties.getProperty(POWER_INFO_BATTERY_ESTIMATED_CHARGE_REMAINING, "0.0")) * 0.01, batteryState, Integer.parseInt(properties
                .getProperty(POWER_INFO_BATTERY_ESTIMATED_RUN_TIME, "0")), TimeUnit.MINUTES, properties.getProperty(POWER_INFO_BATTERY_TIME_TO_FULL_CHARGE, "")
                .isEmpty() ? -1 : Integer.parseInt(properties.getProperty(POWER_INFO_BATTERY_TIME_TO_FULL_CHARGE, "0")), TimeUnit.MINUTES, PowerSupply.BATTERY, properties);
    }
    
}
