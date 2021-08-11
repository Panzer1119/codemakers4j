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

package de.codemakers.base.sorting.heap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class HeapArray<T> {
    
    public static final double LN_2 = Math.log(2.0);
    
    private T[] data;
    
    public HeapArray(Collection<T> data) {
        this((T[]) data.toArray());
    }
    
    public HeapArray(T[] data) {
        Objects.requireNonNull(data);
        this.data = data;
        for (T t : data) {
            Objects.requireNonNull(t);
        }
    }
    
    public static <T> HeapArray<T> create(Collection<T> objects) {
        Objects.requireNonNull(objects);
        return create((T[]) objects.toArray());
    }
    
    public static <T> HeapArray<T> create(T... objects) {
        final HeapArray<T> heapArray = new HeapArray<>(objects);
        System.out.println("heapArray  =" + heapArray);
        for (int i = objects.length - 1; i >= 0; i--) {
            //for (int i = objects.length / 2; i >= 0; i--) {
            final T t = heapArray.data[0];
            heapArray.data[0] = heapArray.data[i];
            heapArray.data[i] = t;
            System.out.println(i + " heapArray=" + heapArray);
            heapArray.heapify(i);
            System.out.println(i + "_heapArray=" + heapArray);
        }
        return heapArray;
    }
    
    public T[] getData() {
        return Arrays.copyOf(data, data.length);
    }
    
    public boolean isEmpty() {
        return data.length == 0;
    }
    
    public T get(int i) {
        if (i >= data.length) {
            return null;
        }
        return data[i];
    }
    
    public T get() {
        if (data.length == 0) {
            return null;
        }
        return data[0];
    }
    
    public HeapArray<T> add(T object) {
        Objects.requireNonNull(object);
        data = Arrays.copyOf(data, data.length + 1);
        data[data.length - 1] = object;
        siftUp(data.length - 1);
        return this;
    }
    
    protected HeapArray<T> siftUp(int i) {
        int parent = 0;
        while (i > 0 && data[i].hashCode() > data[parent = getParentPosition(i)].hashCode()) {
            final T t = data[parent];
            data[parent] = data[i];
            data[i] = t;
            i = parent;
        }
        return this;
    }
    
    public T remove() {
        final T t = get();
        if (data.length == 1) {
            data = Arrays.copyOf(data, 0);
            return t;
        }
        data[0] = data[data.length - 1];
        data = Arrays.copyOf(data, data.length - 1);
        //System.out.println("ASDasdasdSD=" + this);
        heapify(0);
        return t;
    }
    
    protected HeapArray<T> heapify(int i) {
        //System.out.println("Heapifing       " + i + ", " + this);
        int largest = i;
        int largest_key = data[largest].hashCode();
        if (hasLeftChild(i)) {
            final int position = getLeftChildPosition(i);
            if (data[position].hashCode() > largest_key) {
                largest_key = data[position].hashCode();
                largest = position;
            }
        }
        if (hasRightChild(i)) {
            final int position = getRightChildPosition(i);
            if (data[position].hashCode() > largest_key) {
                //largest_key = data[position].hashCode();
                largest = position;
            }
        }
        if (largest != i) {
            final T t = data[i];
            data[i] = data[largest];
            data[largest] = t;
            //System.out.println("Heapifing child " + largest + ", " + this);
            heapify(largest);
        }
        return this;
    }
    
    public int getParentPosition(int i) {
        return (i - 1) / 2;
    }
    
    public int getLeftChildPosition(int i) {
        return 2 * i + 1;
    }
    
    public int getRightChildPosition(int i) {
        return 2 * (i + 1);
    }
    
    public boolean hasParent(int i) {
        return getParentPosition(i) >= 0;
    }
    
    public boolean hasLeftChild(int i) {
        return data.length > getLeftChildPosition(i);
    }
    
    public boolean hasRightChild(int i) {
        return data.length > getRightChildPosition(i);
    }
    
    public T getParent(int i) {
        return data[getParentPosition(i)];
    }
    
    public T getLeftChild(int i) {
        return data[getLeftChildPosition(i)];
    }
    
    public T getRightChild(int i) {
        return data[getRightChildPosition(i)];
    }
    
    public int size() {
        return data.length;
    }
    
    public int getHeight() {
        return ((int) (Math.log(data.length) / LN_2)) + 1;
    }
    
    public T[] getLayer(int y) {
        //System.out.println("y         =" + y);
        final int length_all = (int) Math.pow(2, y + 1) - 1;
        //System.out.println("length_all=" + length_all);
        final int length_all_ = (int) Math.pow(2, y) - 1;
        //System.out.println("length_all_=" + length_all_);
        final int length_max = (int) Math.pow(2, y);
        //System.out.println("length_max=" + length_max);
        final int length = (y == getHeight() - 1) ? size() - length_all_ : length_max;
        //System.out.println("length    =" + length);
        final T[] temp = Arrays.copyOf(data, length);
        //System.out.println("size=" + size());
        final int source = length_all - length_max;
        //System.out.println("source=" + source);
        System.arraycopy(data, source, temp, 0, length);
        return temp;
    }
    
    public String toTreeString() {
        String string = "\n";
        final int height = getHeight();
        for (int y = 0; y < height; y++) {
            //System.out.println("y=" + y);
            final T[] layer = getLayer(y);
            //final String temp = Arrays.toString(layer);
            String temp = "";
            for (int i = 0; i < layer.length; i++) {
                temp += layer[i];
                if (i < layer.length - 1) {
                    if (i % 2 == 1) {
                        temp += " | ";
                    } else {
                        temp += ", ";
                    }
                }
            }
            //System.out.println("layer=" + temp);
            string += temp + "\n";
        }
        return string;
    }
    
    @Override
    public String toString() {
        return "HeapArray{" + "data=" + Arrays.toString(data) + '}';
    }
    
}
