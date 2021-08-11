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

public class Octuplet<A, B, C, D, E, F, G, H> extends Septuplet<A, B, C, D, E, F, G> {
    
    protected H h;
    
    public Octuplet(A a, B b, C c, D d, E e, F f, G g, H h) {
        super(a, b, c, d, e, f, g);
        this.h = h;
    }
    
    public final H getH() {
        return h;
    }
    
    public final Octuplet setH(H h) {
        this.h = h;
        return this;
    }
    
    @Override
    public Object[] toArray() {
        return new Object[] {a, b, c, d, e, f, g, h};
    }
    
    @Override
    public String toString() {
        return "Octuplet{" + "h=" + h + ", g=" + g + ", f=" + f + ", e=" + e + ", d=" + d + ", c=" + c + ", b=" + b + ", a=" + a + '}';
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
        final Octuplet<?, ?, ?, ?, ?, ?, ?, ?> that = (Octuplet<?, ?, ?, ?, ?, ?, ?, ?>) o;
        return Objects.equals(h, that.h);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), h);
    }
    
}
