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

import de.codemakers.base.util.interfaces.Resettable;
import de.codemakers.base.util.tough.ToughSupplier;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class History<T> implements Resettable {
    
    protected final List<T> list = new CopyOnWriteArrayList<>();
    protected final AtomicInteger index = new AtomicInteger(-1);
    
    public T previous() {
        return previous(() -> null);
    }
    
    public T previous(ToughSupplier<T> defaultValueSupplier) {
        index.incrementAndGet();
        if (index.get() >= getSize()) {
            index.set(getSize() - 1);
        }
        return current(defaultValueSupplier);
    }
    
    public T current() {
        return current(() -> null);
    }
    
    public T current(ToughSupplier<T> defaultValueSupplier) {
        if ((list.isEmpty() || getIndex() < 0) && defaultValueSupplier != null) {
            return defaultValueSupplier.getWithoutException();
        }
        return list.get(getSize() - index.get() - 1);
    }
    
    public T next() {
        return next(() -> null);
    }
    
    public T next(ToughSupplier<T> defaultValueSupplier) {
        index.decrementAndGet();
        if (index.get() < -1) {
            index.set(-1);
        }
        return current(defaultValueSupplier);
    }
    
    public boolean isNewest() {
        return getIndex() + 1 == 0;
    }
    
    public boolean isOldest() {
        return getIndex() + 1 == getSize();
    }
    
    public History<T> add(T t) {
        list.add(t);
        index.set(-1);
        return this;
    }
    
    public History<T> addAndDeleteLast(T t) {
        deleteLastElements(index.get() + 1);
        list.add(t);
        index.set(-1);
        return this;
    }
    
    protected History<T> deleteLastElements(int elements) {
        if (elements <= 0) {
            return this;
        }
        for (int i = 0; i < elements; i++) {
            index.decrementAndGet();
            list.remove(list.size() - 1);
        }
        return this;
    }
    
    public int getIndex() {
        return index.get();
    }
    
    public int getSize() {
        return list.size();
    }
    
    @Override
    public boolean reset() throws Exception {
        index.set(-1);
        return true;
    }
    
    @Override
    public String toString() {
        return "History{" + "list=" + list + ", index=" + index + '}';
    }
    
}
