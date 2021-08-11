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

package de.codemakers.math.eval_1.value_double.operations;

import de.codemakers.math.eval_1.Operator;
import de.codemakers.math.eval_1.Value;
import de.codemakers.math.eval_1.value_double.DoubleOperationValue;

@Operator(operationSign = "!", parameters = 1, unaryOperator = true)
public class DoubleFactorialValue extends DoubleOperationValue {
    
    public DoubleFactorialValue(Value<Double>... values) {
        super(values);
    }
    
    public static double factorial(double d) {
        return factorial(d, false);
    }
    
    public static double factorial(double d, boolean round) {
        if (d % 1 == 0) {
            final long n = (int) d;
            long result = 1;
            for (long l = 2; l <= n; l++) {
                result *= l;
            }
            return result;
        }
        final double fact = gamma(d + 1);
        return round ? Math.round(fact) : fact;
    }
    
    /**
     * https://introcs.cs.princeton.edu/java/91float/Gamma.java.html
     *
     * @param x x
     *
     * @return log(gamma(x))
     */
    public static double logGamma(double x) {
        final double temp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
        final double series = 1.0 + 76.18009173 / (x + 0) - 86.50532033 / (x + 1) + 24.01409822 / (x + 2) - 1.231739516 / (x + 3) + 0.00120858003 / (x + 4) - 0.00000536382 / (x + 5);
        return temp + Math.log(series * Math.sqrt(2 * Math.PI));
    }
    
    /**
     * https://introcs.cs.princeton.edu/java/91float/Gamma.java.html
     *
     * @param x x
     *
     * @return gamma(x)
     */
    public static double gamma(double x) {
        return Math.exp(logGamma(x));
    }
    
    @Override
    protected Double apply(Value<Double>[] values) {
        return factorial(values[0].getValue());
    }
    
}
