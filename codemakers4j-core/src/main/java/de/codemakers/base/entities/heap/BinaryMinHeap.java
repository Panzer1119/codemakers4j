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
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryMinHeap<E extends Comparable<E>> implements Collection<E> {
    
    public static final int DIMENSION = 2;
    public static final boolean CLEAR_REMOVED_INDICES = false;
    
    protected Object[] heap;
    protected int heapSize = 0;
    
    public BinaryMinHeap(int capacity) {
        this.heap = new Object[capacity];
    }
    
    @Override
    public int size() {
        return heapSize;
    }
    
    @Override
    public boolean isEmpty() {
        return heapSize == 0;
    }
    
    @Override
    public boolean contains(Object object) {
        return indexOf(object) >= 0;
    }
    
    @Override
    public Iterator<E> iterator() {
        return Arrays.asList((E[]) heap).iterator();
    }
    
    @Override
    public Object[] toArray() {
        return heap;
    }
    
    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < heapSize) {
            return (T[]) Arrays.copyOf(heap, heapSize, array.getClass());
        }
        System.arraycopy(heap, 0, array, 0, heapSize);
        if (array.length > heapSize) {
            array[heapSize] = null;
        }
        return array;
    }
    
    public E get(int index) {
        return heapData(index);
    }
    
    private E heapData(int index) {
        return (E) heap[index];
    }
    
    public boolean isFull() {
        return heapSize >= heap.length;
    }
    
    protected static int parent(int i) {
        return (i - 1) / DIMENSION;
    }
    
    protected static int kthChild(int i, int k) {
        return DIMENSION * i + k;
    }
    
    public int indexOf(Object object) {
        if (object == null) {
            for (int i = 0; i < heapSize; i++) {
                if (heap[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < heapSize; i++) {
                if (object.equals(heap[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int lastIndexOf(Object object) {
        if (object == null) {
            for (int i = heapSize - 1; i >= 0; i--) {
                if (heap[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = heapSize - 1; i >= 0; i--) {
                if (object.equals(heap[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    @Override
    public boolean add(E e) {
        if (isFull()) {
            throw new IndexOutOfBoundsException("Heap is full");
        }
        heap[heapSize] = e;
        heapifyUp(heapSize);
        heapSize++;
        return true;
    }
    
    public E remove(int i) {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        final E e = heapData(i);
        heapSize--;
        heap[i] = heap[heapSize];
        if (CLEAR_REMOVED_INDICES) {
            heap[heapSize] = null;
        }
        heapifyDown(i);
        return e;
    }
    
    @Override
    public boolean remove(Object object) {
        final int index = indexOf(object);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object object : collection) {
            if (!contains(object)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean modified = false;
        for (E e : collection) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }
    
    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean modified = false;
        for (Object object : collection) {
            if (remove(object)) {
                modified = true;
            }
        }
        return modified;
    }
    
    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean modified = false;
        for (Object object : heap) {
            if (!collection.contains(object) && remove(object)) {
                modified = true;
            }
        }
        return modified;
    }
    
    @Override
    public void clear() {
        heapSize = 0;
        if (CLEAR_REMOVED_INDICES) {
            Arrays.fill(heap, null);
        }
    }
    
    protected void heapifyUp(int i) {
        E e = heapData(i);
        while (i > 0 && e.compareTo(heapData(parent(i))) < 0) {
            heap[i] = heap[parent(i)];
            i = parent(i);
        }
        heap[i] = e;
    }
    
    protected void heapifyDown(int i) {
        int child;
        E e = heapData(i);
        while (kthChild(i, 1) < heapSize) {
            child = minChild(i);
            if (e.compareTo(heapData(child)) > 0) {
                heap[i] = heap[child];
            } else {
                break;
            }
            i = child;
        }
        heap[i] = e;
    }
    
    protected int minChild(int i) {
        final int leftChild = kthChild(i, 1);
        final int rightChild = kthChild(i, 2);
        return heapData(leftChild).compareTo(heapData(rightChild)) < 0 ? leftChild : rightChild;
    }
    
    public E getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heapData(0);
    }
    
    public E removeMin() {
        return remove(0);
    }
    
    @Override
    public String toString() {
        return "BinaryMinHeap{" + "heap=" + Arrays.toString(heap) + ", heapSize=" + heapSize + '}';
    }
    
}
