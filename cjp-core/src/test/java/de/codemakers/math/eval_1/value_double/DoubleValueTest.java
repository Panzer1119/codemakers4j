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

import de.codemakers.math.eval_1.value_double.operations.DoubleAdditionValue;

public class DoubleValueTest {
    
    public static final void main(String[] ars) {
        final DoubleValue doubleValue_1 = new DoubleValue(1.0);
        System.out.println("doubleValue_1.getValue() = " + doubleValue_1.getValue());
        final DoubleValue doubleValue_2 = new DoubleValue(2.0);
        System.out.println("doubleValue_2.getValue() = " + doubleValue_2.getValue());
        final DoubleValue doubleValue_3 = new DoubleValue(3.0);
        System.out.println("doubleValue_3.getValue() = " + doubleValue_3.getValue());
        final DoubleAdditionValue doubleAdditionValue_1 = new DoubleAdditionValue(doubleValue_1, doubleValue_2, doubleValue_3);
        System.out.println("doubleAdditionValue_1.getValue() = " + doubleAdditionValue_1.getValue());
        final DoubleRandomValue doubleRandomValue_1 = new DoubleRandomValue();
        System.out.println("doubleRandomValue_1.getValue() = " + doubleRandomValue_1.getValue());
        final DoubleAdditionValue doubleAdditionValue_2 = new DoubleAdditionValue(doubleAdditionValue_1, doubleRandomValue_1);
        System.out.println("doubleAdditionValue_2.getValue() = " + doubleAdditionValue_2.getValue());
        
    }
    
}
