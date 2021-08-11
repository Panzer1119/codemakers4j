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

import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.base.util.ArrayUtil;

import java.lang.reflect.Array;
import java.util.*;

public class RingBuffer<T> implements Queue<T> {
    
    private final Class<T> clazz;
    private final T[] array;
    private int head = 0;
    private int size = 0;
    
    public RingBuffer(Class<T> clazz, int length) {
        this.clazz = clazz;
        this.array = (T[]) Array.newInstance(clazz, length);
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    
    @Override
    public boolean contains(Object o) {
        return ArrayUtil.arrayContains(array, o);
    }
    
    @Override
    public Iterator<T> iterator() {
        return null;
    }
    
    @Override
    public Object[] toArray() {
        if (isEmpty()) {
            return new Object[0];
        }
        final T[] output = (T[]) Array.newInstance(clazz, size());
        if (head + size >= array.length) {
            final int size_ = array.length - head;
            System.arraycopy(array, head, output, 0, size_);
            System.arraycopy(array, 0, output, size_, size - size_);
        } else {
            System.arraycopy(array, head, output, 0, size);
        }
        return output;
    }
    
    @Override
    public <T1> T1[] toArray(T1[] a) {
        return (T1[]) toArray();
    }
    
    @Override
    public boolean add(T t) {
        array[(head + size) % array.length] = t;
        if (size >= array.length) {
            if (head >= array.length - 1) {
                head = 0;
            } else {
                head++;
            }
        } else {
            size++;
        }
        return true;
    }
    
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("This is a " + RingBuffer.class.getSimpleName() + " (FIFO)");
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }
    
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return c.stream().allMatch(this::add);
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        return c.stream().allMatch(this::remove);
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("This is a " + RingBuffer.class.getSimpleName() + " (FIFO)");
    }
    
    @Override
    public void clear() {
        size = 0;
    }
    
    @Override
    public boolean offer(T t) {
        throw new NotYetImplementedRuntimeException("What should this do?");
    }
    
    @Override
    public T remove() {
        if (isEmpty()) {
            return null;
        }
        final T t = array[head];
        if (head >= array.length - 1) {
            head = 0;
        } else {
            head++;
        }
        size--;
        return t;
    }
    
    @Override
    public T poll() {
        return remove();
    }
    
    @Override
    public T element() {
        return peek();
    }
    
    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return array[head];
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RingBuffer<?> that = (RingBuffer<?>) o;
        return head == that.head && size == that.size && Arrays.equals(array, that.array);
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hash(head, size);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }
    
    @Override
    public String toString() {
        return "RingBuffer{" + "array=" + Arrays.toString(array) + ", head=" + head + ", size=" + size + '}';
    }
    
}
