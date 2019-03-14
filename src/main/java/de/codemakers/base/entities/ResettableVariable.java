/*
 *     Copyright 2018 - 2019 Paul Hagedorn (Panzer1119)
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

package de.codemakers.base.entities;

import de.codemakers.base.util.interfaces.Finishable;
import de.codemakers.base.util.interfaces.Resettable;

import java.util.Objects;

public class ResettableVariable<T> implements Finishable<T>, Resettable {
    
    private T current;
    private T temp;
    
    public ResettableVariable() {
        this(null);
    }
    
    public ResettableVariable(T current) {
        this(current, current);
    }
    
    public ResettableVariable(T current, T temp) {
        this.current = current;
        this.temp = temp;
    }
    
    public T getCurrent() {
        return current;
    }
    
    public ResettableVariable setCurrent(T current) {
        this.current = current;
        return this;
    }
    
    public T getTemp() {
        return temp;
    }
    
    public ResettableVariable setTemp(T temp) {
        this.temp = temp;
        return this;
    }
    
    public boolean isSame() {
        return Objects.equals(current, temp);
    }
    
    public boolean isDifferent() {
        return !Objects.equals(current, temp);
    }
    
    @Override
    public boolean reset() throws Exception {
        temp = current;
        return isSame();
    }
    
    @Override
    public T finish() throws Exception {
        current = temp;
        return current;
    }
    
    @Override
    public String toString() {
        return "ResettableVariable{" + "current=" + current + ", temp=" + temp + '}';
    }
    
}