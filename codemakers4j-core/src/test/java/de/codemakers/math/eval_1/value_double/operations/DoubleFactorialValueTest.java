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

import de.codemakers.math.eval_1.value_double.DoubleValue;

public class DoubleFactorialValueTest {
    
    public static final void main(String[] args) {
        final DoubleFactorialValue doubleFactorialValue_1 = new DoubleFactorialValue(new DoubleValue(4.0));
        System.out.println("doubleFactorialValue_1.getValue() = " + doubleFactorialValue_1.getValue());
        final DoubleFactorialValue doubleFactorialValue_2 = new DoubleFactorialValue(new DoubleValue(4.5));
        System.out.println("doubleFactorialValue_2.getValue() = " + doubleFactorialValue_2.getValue());
        
    }
    
}
