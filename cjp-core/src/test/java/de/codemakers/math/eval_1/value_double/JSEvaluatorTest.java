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

package de.codemakers.math.eval_1.value_double;

import de.codemakers.base.entities.results.ReturningResult;

public class JSEvaluatorTest {
    
    public static final void main(String[] args) {
        final JSEvaluator jsEvaluator = new JSEvaluator();
        //final String expression = "1.00+23.4545 - (-3*4) * 4 + (5 - 3 * (4 - 7)) / (3 ^-2)";
        final String expression = "1 + 2 - 3 * 5 + 81 / 9";
        System.out.println("expression: " + expression);
        final ReturningResult<Double> result = jsEvaluator.eval(expression);
        System.out.println("result: " + result);
    }
    
}
