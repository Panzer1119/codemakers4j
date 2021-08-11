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

package de.codemakers.base.entities;

import de.codemakers.base.util.interfaces.Testable;
import de.codemakers.base.util.tough.ToughConsumer;

import java.util.Objects;

public class BoundResettableVariable<T> extends ResettableVariable<T> implements Testable {
    
    protected ToughConsumer<T> toughConsumer;
    
    public BoundResettableVariable(ToughConsumer<T> toughConsumer) {
        this(null, toughConsumer);
    }
    
    public BoundResettableVariable(T current, ToughConsumer<T> toughConsumer) {
        this(current, current, toughConsumer);
    }
    
    public BoundResettableVariable(T current, T temp, ToughConsumer<T> toughConsumer) {
        super(current, temp);
        this.toughConsumer = Objects.requireNonNull(toughConsumer, "toughConsumer");
    }
    
    public ToughConsumer<T> getToughConsumer() {
        return toughConsumer;
    }
    
    public BoundResettableVariable setToughConsumer(ToughConsumer<T> toughConsumer) {
        this.toughConsumer = toughConsumer;
        return this;
    }
    
    @Override
    public boolean reset() throws Exception {
        toughConsumer.accept(current);
        return super.reset();
    }
    
    @Override
    public boolean test() throws Exception {
        toughConsumer.accept(temp);
        return true;
    }
    
    @Override
    public T finish() throws Exception {
        final T t = super.finish();
        toughConsumer.accept(t);
        return t;
    }
    
    @Override
    public String toString() {
        return "BoundResettableVariable{" + "toughConsumer=" + toughConsumer + ", current=" + current + ", temp=" + temp + '}';
    }
    
}
