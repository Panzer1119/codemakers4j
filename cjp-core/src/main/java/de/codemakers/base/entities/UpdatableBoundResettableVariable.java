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

import de.codemakers.base.util.interfaces.Updatable;
import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.base.util.tough.ToughSupplier;

public class UpdatableBoundResettableVariable<T> extends BoundResettableVariable<T> implements Updatable<Boolean, Void> {
    
    protected ToughSupplier<T> toughSupplier;
    
    public UpdatableBoundResettableVariable(ToughSupplier<T> toughSupplier, ToughConsumer<T> toughConsumer) {
        super(toughSupplier.getWithoutException(), toughConsumer);
        this.toughSupplier = toughSupplier;
    }
    
    public ToughSupplier<T> getToughSupplier() {
        return toughSupplier;
    }
    
    public UpdatableBoundResettableVariable setToughSupplier(ToughSupplier<T> toughSupplier) {
        this.toughSupplier = toughSupplier;
        return this;
    }
    
    @Override
    public Boolean update(Void aVoid) throws Exception {
        setCurrent(toughSupplier.get());
        setTemp(getCurrent());
        return true;
    }
    
    @Override
    public String toString() {
        return "UpdatableBoundResettableVariable{" + "toughSupplier=" + toughSupplier + ", toughConsumer=" + toughConsumer + ", current=" + current + ", temp=" + temp + '}';
    }
    
}
