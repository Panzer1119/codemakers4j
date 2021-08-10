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

public class BinaryHeapTest {
    
    public static final void main(String[] args) throws Exception {
        BinaryMaxHeap.CLEAR_REMOVED_INDICES = true;
        final Random random = new Random();
        final BinaryMaxHeap<Integer> integerBinaryMaxHeap = new BinaryMaxHeap<>(20);
        System.out.println("integerBinaryMaxHeap -1=" + integerBinaryMaxHeap);
        for (int i = 0; i < 10; i++) {
            integerBinaryMaxHeap.add(random.nextInt());
            System.out.println("integerBinaryMaxHeap  " + i + " =" + integerBinaryMaxHeap);
        }
        System.out.println();
        System.out.println();
        while (!integerBinaryMaxHeap.isEmpty()) {
            System.out.println(integerBinaryMaxHeap.removeMax());
            System.out.println("integerBinaryMaxHeap=" + integerBinaryMaxHeap);
        }
        System.out.println();
        System.out.println();
        System.out.println("integerBinaryMaxHeap=" + integerBinaryMaxHeap);
    }
    
}
