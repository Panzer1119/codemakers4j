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

import de.codemakers.math.eval_1.Value;

public class DoubleValue extends Value<Double> {
    
    protected double value = 0;
    
    public DoubleValue() {
        this(0);
    }
    
    public DoubleValue(double value) {
        this.value = value;
    }
    
    public DoubleValue(Value<Double> value) {
        super(value);
        this.value = value.getValue();
    }
    
    @Override
    public Double getValue() {
        return value;
    }
    
    public DoubleValue setValue(double value) {
        this.value = value;
        return this;
    }
    
}
