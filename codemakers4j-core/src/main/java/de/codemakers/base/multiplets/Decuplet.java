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

public class Decuplet<A, B, C, D, E, F, G, H, I, J> extends Nonuplet<A, B, C, D, E, F, G, H, I> {
    
    protected J j;
    
    public Decuplet(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) {
        super(a, b, c, d, e, f, g, h, i);
        this.j = j;
    }
    
    public final J getJ() {
        return j;
    }
    
    public final Decuplet setJ(J j) {
        this.j = j;
        return this;
    }
    
    @Override
    public Object[] toArray() {
        return new Object[] {a, b, c, d, e, f, g, h, i, j};
    }
    
    @Override
    public String toString() {
        return "Decuplet{" + "j=" + j + ", i=" + i + ", h=" + h + ", g=" + g + ", f=" + f + ", e=" + e + ", d=" + d + ", c=" + c + ", b=" + b + ", a=" + a + '}';
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
        final Decuplet<?, ?, ?, ?, ?, ?, ?, ?, ?, ?> that = (Decuplet<?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) o;
        return Objects.equals(j, that.j);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), j);
    }
    
}
