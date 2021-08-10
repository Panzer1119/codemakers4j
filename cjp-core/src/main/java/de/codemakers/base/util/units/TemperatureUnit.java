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

package de.codemakers.base.util.units;

import de.codemakers.base.util.ArrayUtil;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum TemperatureUnit {
    KELVIN("kelvin", "K", 0, 273.16, (f_1, f_2) -> ((f_2 - f_1) / 273.16)) {
        @Override
        public double toKelvin(double temperature) {
            return temperature;
        }
        
        @Override
        public double toDegreeCelsius(double temperature) {
            return temperature - 273.15;
        }
        
        @Override
        public double toDegreeFahrenheit(double temperature) {
            return temperature * 1.8 - 459.67;
        }
        
        @Override
        public double toDegreeRankine(double temperature) {
            return temperature * 1.8;
        }
        
        @Override
        public double toDegreeDelisle(double temperature) {
            return (373.15 - temperature) * 1.5;
        }
        
        @Override
        public double toDegreeReaumur(double temperature) {
            return (temperature - 273.15) * 0.8;
        }
        
        @Override
        public double toDegreeNewton(double temperature) {
            return (temperature - 273.15) * 0.33;
        }
        
        @Override
        public double toDegreeRomer(double temperature) {
            return ((temperature - 273.15) * 21.0) / 40.0 + 7.5;
        }
    },
    CELSIUS("celsius", "°C", 0, 100, (f_1, f_2) -> ((f_2 - f_1) / 100)) {
        @Override
        public double toKelvin(double temperature) {
            return temperature + 273.15;
        }
        
        @Override
        public double toDegreeCelsius(double temperature) {
            return temperature;
        }
        
        @Override
        public double toDegreeFahrenheit(double temperature) {
            return temperature * 1.8 + 32.0;
        }
        
        @Override
        public double toDegreeRankine(double temperature) {
            return temperature * 1.8 + 491.67;
        }
        
        @Override
        public double toDegreeDelisle(double temperature) {
            return (100.0 - temperature) * 1.5;
        }
        
        @Override
        public double toDegreeReaumur(double temperature) {
            return temperature * 0.8;
        }
        
        @Override
        public double toDegreeNewton(double temperature) {
            return temperature * 0.33;
        }
        
        @Override
        public double toDegreeRomer(double temperature) {
            return (temperature * 21.0) / 40.0 + 7.5;
        }
    },
    FAHRENHEIT("fahrenheit", "°F", 0, 96, (f_1, f_2) -> ((f_2 - f_1) / 96)) {
        @Override
        public double toKelvin(double temperature) {
            return ((temperature + 459.67) * 5.0) / 9.0;
        }
        
        @Override
        public double toDegreeCelsius(double temperature) {
            return ((temperature - 32.0) * 5.0) / 9.0;
        }
        
        @Override
        public double toDegreeFahrenheit(double temperature) {
            return temperature;
        }
        
        @Override
        public double toDegreeRankine(double temperature) {
            return temperature + 459.67;
        }
        
        @Override
        public double toDegreeDelisle(double temperature) {
            return ((212.0 - temperature) * 5.0) / 6.0;
        }
        
        @Override
        public double toDegreeReaumur(double temperature) {
            return ((temperature - 32.0) * 4.0) / 9.0;
        }
        
        @Override
        public double toDegreeNewton(double temperature) {
            return ((temperature - 32.0) * 11.0) / 60.0;
        }
        
        @Override
        public double toDegreeRomer(double temperature) {
            return ((temperature - 32.0) * 7.0) / 24.0 + 7.5;
        }
    },
    RANKINE("rankine", "°Ra", 0, -Double.MAX_VALUE, FAHRENHEIT.interval, "°R") {
        @Override
        public double toKelvin(double temperature) {
            return (temperature * 5.0) / 9.0;
        }
        
        @Override
        public double toDegreeCelsius(double temperature) {
            return (temperature * 5.0) / 9.0 - 273.15;
        }
        
        @Override
        public double toDegreeFahrenheit(double temperature) {
            return temperature - 459.67;
        }
        
        @Override
        public double toDegreeRankine(double temperature) {
            return temperature;
        }
        
        @Override
        public double toDegreeDelisle(double temperature) {
            return ((671.67 - temperature) * 5.0) / 6.0;
        }
        
        @Override
        public double toDegreeReaumur(double temperature) {
            return (temperature * 4.0) / 9.0 - 218.52;
        }
        
        @Override
        public double toDegreeNewton(double temperature) {
            return ((temperature - 491.67) * 11.0) / 60.0;
        }
        
        @Override
        public double toDegreeRomer(double temperature) {
            return ((temperature - 491.67) * 7.0) / 24.0 + 7.5;
        }
    },
    DELISLE("delisle", "°De", 150, 0, (f_1, f_2) -> ((f_1 - f_2) / 150), "°D") {
        @Override
        public double toKelvin(double temperature) {
            return 373.15 - temperature * 2.0 / 3.0;
        }
        
        @Override
        public double toDegreeCelsius(double temperature) {
            return 100.0 - temperature * 2.0 / 3.0;
        }
        
        @Override
        public double toDegreeFahrenheit(double temperature) {
            return 212.0 - temperature * 1.2;
        }
        
        @Override
        public double toDegreeRankine(double temperature) {
            return 671.67 - temperature * 1.2;
        }
        
        @Override
        public double toDegreeDelisle(double temperature) {
            return temperature;
        }
        
        @Override
        public double toDegreeReaumur(double temperature) {
            return 80.0 - temperature * 8.0 / 15.0;
        }
        
        @Override
        public double toDegreeNewton(double temperature) {
            return 33.0 - temperature * 0.22;
        }
        
        @Override
        public double toDegreeRomer(double temperature) {
            return 60.0 - temperature * 0.35;
        }
    },
    REAUMUR("réaumur", "°Ré", 0, 80, (f_1, f_2) -> ((f_2 - f_1) / 80), "°Re", "°R") {
        @Override
        public double toKelvin(double temperature) {
            return temperature * 1.25 + 273.15;
        }
        
        @Override
        public double toDegreeCelsius(double temperature) {
            return temperature * 1.25;
        }
        
        @Override
        public double toDegreeFahrenheit(double temperature) {
            return temperature * 2.25 + 32.0;
        }
        
        @Override
        public double toDegreeRankine(double temperature) {
            return temperature * 2.25 + 491.67;
        }
        
        @Override
        public double toDegreeDelisle(double temperature) {
            return (80.0 - temperature) * 1.875;
        }
        
        @Override
        public double toDegreeReaumur(double temperature) {
            return temperature;
        }
        
        @Override
        public double toDegreeNewton(double temperature) {
            return (temperature * 33.0) / 80.0;
        }
        
        @Override
        public double toDegreeRomer(double temperature) {
            return (temperature * 21.0) / 32.0 + 7.5;
        }
    },
    NEWTON("newton", "°N", 0, 33, (f_1, f_2) -> ((f_2 - f_1) / 33)) {
        @Override
        public double toKelvin(double temperature) {
            return (temperature * 100.0) / 33.0 + 273.15;
        }
        
        @Override
        public double toDegreeCelsius(double temperature) {
            return (temperature * 100.0) / 33.0;
        }
        
        @Override
        public double toDegreeFahrenheit(double temperature) {
            return (temperature * 60.0) / 11.0 + 32.0;
        }
        
        @Override
        public double toDegreeRankine(double temperature) {
            return (temperature * 60.0) / 11.0 + 491.67;
        }
        
        @Override
        public double toDegreeDelisle(double temperature) {
            return ((33.0 - temperature) * 50.0) / 11.0;
        }
        
        @Override
        public double toDegreeReaumur(double temperature) {
            return (temperature * 80.0) / 33.0;
        }
        
        @Override
        public double toDegreeNewton(double temperature) {
            return temperature;
        }
        
        @Override
        public double toDegreeRomer(double temperature) {
            return (temperature * 35.0) / 22.0 + 7.5;
        }
    },
    ROMER("rømer", "°Rø", 0, 60, (f_1, f_2) -> ((f_2 - f_1) / 60)) {
        @Override
        public double toKelvin(double temperature) {
            return ((temperature - 7.5) * 40.0) / 21.0 + 273.15;
        }
        
        @Override
        public double toDegreeCelsius(double temperature) {
            return ((temperature - 7.5) * 40.0) / 21.0;
        }
        
        @Override
        public double toDegreeFahrenheit(double temperature) {
            return ((temperature - 7.5) * 24.0) / 7.0 + 32.0;
        }
        
        @Override
        public double toDegreeRankine(double temperature) {
            return ((temperature - 7.5) * 24.0) / 7.0 + 491.67;
        }
        
        @Override
        public double toDegreeDelisle(double temperature) {
            return ((60.0 - temperature) * 20.0) / 7.0;
        }
        
        @Override
        public double toDegreeReaumur(double temperature) {
            return ((temperature - 7.5) * 32.0) / 21.0;
        }
        
        @Override
        public double toDegreeNewton(double temperature) {
            return ((temperature - 7.5) * 22.0) / 35.0;
        }
        
        @Override
        public double toDegreeRomer(double temperature) {
            return temperature;
        }
    };
    
    private final String name;
    private final String symbol;
    private final String[] alternateSymbols;
    private final double lowFixed;
    private final double highFixed;
    private final double interval;
    
    TemperatureUnit(String name, String symbol, double lowFixed, double highFixed, double interval, String... alternateSymbols) {
        this.name = name;
        this.symbol = symbol;
        this.alternateSymbols = alternateSymbols;
        this.lowFixed = lowFixed;
        this.highFixed = highFixed;
        this.interval = interval;
    }
    
    TemperatureUnit(String name, String symbol, double lowFixed, double highFixed, BiFunction<Double, Double, Double> calculateInterval, String... alternateSymbols) {
        this.name = name;
        this.symbol = symbol;
        this.alternateSymbols = alternateSymbols;
        this.lowFixed = lowFixed;
        this.highFixed = highFixed;
        if (calculateInterval != null) {
            this.interval = calculateInterval.apply(lowFixed, highFixed);
        } else {
            this.interval = -1;
        }
    }
    
    public static final TemperatureUnit ofName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (TemperatureUnit temperatureUnit : values()) {
            if (name.equalsIgnoreCase(temperatureUnit.getName())) {
                return temperatureUnit;
            }
        }
        return null;
    }
    
    public static final TemperatureUnit ofSymbol(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            return null;
        }
        for (TemperatureUnit temperatureUnit : values()) {
            if (temperatureUnit.getSymbol().equalsIgnoreCase(symbol) || temperatureUnit.getSymbol().endsWith(symbol) || temperatureUnit.getSymbol().toLowerCase().endsWith(symbol.toLowerCase())) {
                return temperatureUnit;
            }
        }
        for (TemperatureUnit temperatureUnit : values()) {
            if (temperatureUnit.getAlternateSymbols() == null) {
                continue;
            }
            if (ArrayUtil.arrayContains(temperatureUnit.getAlternateSymbols(), symbol)) {
                return temperatureUnit;
            }
        }
        return null;
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getSymbol() {
        return symbol;
    }
    
    public final String[] getAlternateSymbols() {
        return alternateSymbols;
    }
    
    public final double getLowFixed() {
        return lowFixed;
    }
    
    public final double getHighFixed() {
        return highFixed;
    }
    
    public final double getInterval() {
        return interval;
    }
    
    public final boolean isDegree() {
        return this != KELVIN;
    }
    
    public double toKelvin(double temperature) {
        throw new AbstractMethodError();
    }
    
    public double toDegreeCelsius(double temperature) {
        throw new AbstractMethodError();
    }
    
    public double toDegreeFahrenheit(double temperature) {
        throw new AbstractMethodError();
    }
    
    public double toDegreeRankine(double temperature) {
        throw new AbstractMethodError();
    }
    
    public double toDegreeDelisle(double temperature) {
        throw new AbstractMethodError();
    }
    
    public double toDegreeReaumur(double temperature) {
        throw new AbstractMethodError();
    }
    
    public double toDegreeNewton(double temperature) {
        throw new AbstractMethodError();
    }
    
    public double toDegreeRomer(double temperature) {
        throw new AbstractMethodError();
    }
    
    @Override
    public final String toString() {
        return getClass().getSimpleName() + "{" + "name='" + name + '\'' + ", symbol='" + symbol + '\'' + ", alternateSymbols=" + Arrays.toString(alternateSymbols) + ", lowFixed=" + lowFixed + ", highFixed=" + highFixed + ", interval=" + interval + '}';
    }
    
}
