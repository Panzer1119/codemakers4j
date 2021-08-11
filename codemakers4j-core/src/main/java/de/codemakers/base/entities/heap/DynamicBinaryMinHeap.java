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

package de.codemakers.base.entities.heap;

import java.util.Arrays;

public class DynamicBinaryMinHeap<E extends Comparable<E>> extends BinaryMinHeap<E> {
    
    public DynamicBinaryMinHeap() {
        this(10);
    }
    
    public DynamicBinaryMinHeap(int capacity) {
        super(capacity);
    }
    
    @Override
    public boolean add(E e) {
        if (isFull()) {
            heap = Arrays.copyOf(heap, heap.length + 1);
        }
        return super.add(e);
    }
    
    @Override
    public E remove(int i) {
        final E removed = super.remove(i);
        if (heap.length >= 1) {
            heap = Arrays.copyOf(heap, heap.length - 1);
        }
        return removed;
    }
    
    @Override
    public String toString() {
        return "DynamicBinaryMinHeap{" + "heap=" + Arrays.toString(heap) + ", heapSize=" + heapSize + '}';
    }
    
}
