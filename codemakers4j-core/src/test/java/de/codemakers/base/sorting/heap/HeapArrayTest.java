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

package de.codemakers.base.sorting.heap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HeapArrayTest {
    
    public static final void main(String[] args) {
        final String[] data_1_s = new String[] {"31", "55", "18", "98", "62", "11", "22", "70"};
        System.out.println("data_1_s=" + Arrays.toString(data_1_s));
        System.out.println("data_1_s-hashCodes=" + Stream.of(data_1_s).map(Object::hashCode).map(Object::toString).collect(Collectors.joining(", ")));
        final List<Sortable<String>> data_1 = Stream.of(data_1_s).map(Sortable::new).collect(Collectors.toList());
        System.out.println("data_1=" + data_1);
        System.out.println("data_1.size()=" + data_1.size());
        final HeapArray<Sortable<String>> heapArray_1 = HeapArray.create(data_1);
        System.out.println("heapArray_1=" + heapArray_1);
        //System.out.println("heapArray_1=" + heapArray_1);
        heapArray_1.add(new Sortable<>("63"));
        heapArray_1.add(new Sortable<>("75"));
        heapArray_1.add(new Sortable<>("10"));
        heapArray_1.add(new Sortable<>("60"));
        heapArray_1.add(new Sortable<>("5"));
        heapArray_1.add(new Sortable<>("100"));
        System.out.println("heapArray_1=" + heapArray_1);
        System.out.println("heapArray_1.size()=" + heapArray_1.size());
        System.out.println("heapArray_1.getHeight()=" + heapArray_1.getHeight());
        System.out.println("heapArray_1.toTreeString()=" + heapArray_1.toTreeString());
        /*
        while (!heapArray_1.isEmpty()) {
            System.out.println("heapArray_1.remove()=" + heapArray_1.remove());
            //System.out.println("heapArray_1=" + heapArray_1);
        }
        */
        while (!heapArray_1.isEmpty()) {
            heapArray_1.remove();
            System.out.println("heapArray_1.toTreeString()=" + heapArray_1.toTreeString());
        }
    }
    
    static class Sortable<T> {
        
        final T object;
        final int hashCode;
        
        public Sortable(T object) {
            this(object, Integer.parseInt("" + object));
        }
        
        public Sortable(T object, int hashCode) {
            this.object = object;
            this.hashCode = hashCode;
        }
        
        public T getObject() {
            return object;
        }
        
        public int getHashCode() {
            return hashCode;
        }
        
        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Sortable<?>)) {
                return false;
            }
            final Sortable<?> sortable = (Sortable<?>) object;
            return hashCode == sortable.hashCode;
        }
        
        @Override
        public int hashCode() {
            return hashCode;
        }
        
        @Override
        public String toString() {
            //return "S{" + "o=" + object + ", hc=" + hashCode + '}';
            return object.toString();
        }
        
    }
    
}
