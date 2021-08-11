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

import java.util.stream.Stream;

@Operator(operationSign = "*", parameters = -1)
public class DoubleMultiplicationValue extends DoubleOperationValue {
    
    public DoubleMultiplicationValue(Value<Double>... values) {
        super(values);
    }
    
    @Override
    protected Double apply(Value<Double>[] values) {
        double product = 1.0;
        return Stream.of(values).map(Value::getValue).reduce(product, (product_, value) -> product_ * value);
    }
    
}
