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

import java.util.Random;

public class DoubleRandomValue extends DoubleValue {
    
    private final Random random = new Random();
    private boolean lockedValue = true;
    
    public DoubleRandomValue() {
        setValue(getRandomValue());
    }
    
    @Override
    public Double getValue() {
        if (!lockedValue) {
            setValue(getRandomValue());
        }
        return super.getValue();
    }
    
    public Double getRandomValue() {
        return random.nextDouble();
    }
    
    public boolean isLockedValue() {
        return lockedValue;
    }
    
    public DoubleRandomValue setLockedValue(boolean lockedValue) {
        this.lockedValue = lockedValue;
        return this;
    }
    
}
