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

package de.codemakers.base.multiplets;

import java.util.Objects;

public class Quadruplet<A, B, C, D> extends Triplet<A, B, C> {
    
    protected D d;
    
    public Quadruplet(A a, B b, C c, D d) {
        super(a, b, c);
        this.d = d;
    }
    
    public final D getD() {
        return d;
    }
    
    public final Quadruplet setD(D d) {
        this.d = d;
        return this;
    }
    
    @Override
    public Object[] toArray() {
        return new Object[] {a, b, c, d};
    }
    
    @Override
    public String toString() {
        return "Quadruplet{" + "d=" + d + ", c=" + c + ", b=" + b + ", a=" + a + '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final Quadruplet<?, ?, ?, ?> that = (Quadruplet<?, ?, ?, ?>) o;
        return Objects.equals(d, that.d);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), d);
    }
    
}
