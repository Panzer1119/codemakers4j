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

public class Nonuplet<A, B, C, D, E, F, G, H, I> extends Octuplet<A, B, C, D, E, F, G, H> {
    
    protected I i;
    
    public Nonuplet(A a, B b, C c, D d, E e, F f, G g, H h, I i) {
        super(a, b, c, d, e, f, g, h);
        this.i = i;
    }
    
    public final I getI() {
        return i;
    }
    
    public final Nonuplet setI(I i) {
        this.i = i;
        return this;
    }
    
    @Override
    public Object[] toArray() {
        return new Object[] {a, b, c, d, e, f, g, h, i};
    }
    
    @Override
    public String toString() {
        return "Nonuplet{" + "i=" + i + ", h=" + h + ", g=" + g + ", f=" + f + ", e=" + e + ", d=" + d + ", c=" + c + ", b=" + b + ", a=" + a + '}';
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
        final Nonuplet<?, ?, ?, ?, ?, ?, ?, ?, ?> that = (Nonuplet<?, ?, ?, ?, ?, ?, ?, ?, ?>) o;
        return Objects.equals(i, that.i);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), i);
    }
    
}
