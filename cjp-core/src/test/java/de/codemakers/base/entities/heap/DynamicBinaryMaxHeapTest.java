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

package de.codemakers.base.entities.heap;

import java.util.Random;

public class DynamicBinaryMaxHeapTest {
    
    public static final void main(String[] args) throws Exception {
        final Random random = new Random();
        final DynamicBinaryMaxHeap<Integer> integerDynamicBinaryMaxHeap = new DynamicBinaryMaxHeap<>();
        System.out.println("integerDynamicBinaryMaxHeap -1=" + integerDynamicBinaryMaxHeap);
        for (int i = 0; i < 15; i++) {
            integerDynamicBinaryMaxHeap.add(random.nextInt());
            System.out.println("integerDynamicBinaryMaxHeap  " + i + " =" + integerDynamicBinaryMaxHeap);
        }
        System.out.println();
        System.out.println();
        while (!integerDynamicBinaryMaxHeap.isEmpty()) {
            System.out.println(integerDynamicBinaryMaxHeap.removeMax());
            System.out.println("integerDynamicBinaryMaxHeap=" + integerDynamicBinaryMaxHeap);
        }
        System.out.println();
        System.out.println();
        System.out.println("integerDynamicBinaryMaxHeap=" + integerDynamicBinaryMaxHeap);
        System.out.println();
        System.out.println();
        integerDynamicBinaryMaxHeap.add(random.nextInt());
        System.out.println("integerDynamicBinaryMaxHeap=" + integerDynamicBinaryMaxHeap);
    }
    
}
