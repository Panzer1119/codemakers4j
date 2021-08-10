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

package de.codemakers.math;

public class MathUtil {
    
    //(Primary) Trigonometric Functions
    
    public static double sin(double x) {
        return Math.sin(x);
    }
    
    public static double cos(double x) {
        return Math.cos(x);
    }
    
    public static double tan(double x) {
        return Math.tan(x);
    }
    
    public static double cot(double x) {
        return 1.0 / Math.tan(x);
    }
    
    public static double sec(double x) {
        return 1.0 / Math.cos(x);
    }
    
    public static double csc(double x) {
        return 1.0 / Math.sin(x);
    }
    
    //Inverse Trigonometric Functions
    
    public static double asin(double x) {
        return Math.asin(x);
    }
    
    public static double acos(double x) {
        return Math.acos(x);
    }
    
    public static double atan(double x) {
        return Math.atan(x);
    }
    
    public static double acot(double x) {
        if (x > 0.0) {
            return Math.atan(1.0 / x);
        } else if (x < 0.0) {
            return Math.atan((1.0 / x) + Math.PI);
        }
        return Double.NaN;
    }
    
    public static double arcsec(double x) {
        return Math.acos(1.0 / x);
    }
    
    public static double arccsc(double x) {
        return Math.asin(1.0 / x);
    }
    
    //Hyperbolic Functions
    
    public static double sinh(double x) {
        return Math.sinh(x);
    }
    
    public static double cosh(double x) {
        return Math.cosh(x);
    }
    
    public static double tanh(double x) {
        return Math.tanh(x);
    }
    
    public static double coth(double x) {
        return Math.cosh(x) / Math.sinh(x);
    }
    
    public static double sech(double x) {
        return 1.0 / Math.cosh(x);
    }
    
    public static double csch(double x) {
        return 1.0 / Math.sinh(x);
    }
    
    //Area Functions
    
    public static double asinh(double x) {
        return Math.log(x + Math.sqrt((x * x) + 1));
    }
    
    public static double acosh(double x) {
        if (x < 1.0) {
            return Double.NaN;
        }
        return Math.log(x + Math.sqrt((x * x) + 1));
    }
    
    public static double atanh(double x) {
        if (Math.abs(x) >= 1.0) {
            return Double.NaN;
        }
        return Math.log((1.0 + x) / (1.0 - x)) / 2.0;
    }
    
    public static double acoth(double x) {
        if (Math.abs(x) < 1.0) {
            return Double.NaN;
        }
        return Math.log((x + 1.0) / (x - 1.0)) / 2.0;
    }
    
    public static double arsech(double x) {
        return Math.log((1 + Math.sqrt(1 - (x * x))) / x);
    }
    
    public static double arcsch(double x) {
        return Math.log((1 + Math.sqrt(1 + (x * x))) / x);
    }
    
    //End
    
}
