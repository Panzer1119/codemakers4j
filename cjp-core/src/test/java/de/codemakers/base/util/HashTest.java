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

package de.codemakers.base.util;

import de.codemakers.base.util.interfaces.Hasher;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class HashTest {
    
    public static final void main(String[] args) throws Exception {
        final Random random = new Random(SecureRandom.getInstanceStrong().nextLong());
        final long data_length = 1000 * 1000 * 10; // 10 MB (0.01 GB)
        final byte[] data = new byte[(int) data_length];
        random.nextBytes(data);
        //System.out.println(Arrays.toString(data));
        final Hasher hasher = HashUtil.createHasher64XX();
        final long ops_max = 1000;
        final long start = System.currentTimeMillis();
        byte[] hash = null;
        for (int i = 0; i < ops_max; i++) {
            hash = hasher.hash(data);
        }
        final long duration = System.currentTimeMillis() - start;
        final double duration_per_op = (duration * 1.0) / ops_max;
        System.out.println("Data (overall): " + (data_length * ops_max) + " bytes (" + ((data_length * ops_max) * 8) + " bits)");
        System.out.println("Data (overall): " + ((data_length * ops_max) / 1000000000.0) + " Gigabytes (" + ((data_length * ops_max) * 8.0 / 1000000000.0) + " Gigabits)");
        System.out.println("Data  (per op): " + data_length + " bytes (" + (data_length * 8) + " bits)");
        System.out.println("Data  (per op): " + (data_length / 1000000.0) + " Megabytes (" + (data_length * 8.0 / 1000000.0) + " Megabits)");
        //System.out.println("Time taken (overall): " + duration + " ms (" + (duration / 1000.0) + " seconds)");
        System.out.println(String.format("Time taken (overall): %d ms (%f seconds) (%f GB/s)", duration, duration / 1000.0, (data_length * ops_max) / duration / 1000000.0));
        //System.out.println("Time taken  (per op): " + duration_per_op + " ms (" + (duration_per_op / 1000.0) + " seconds)");
        System.out.println(String.format("Time taken  (per op): %f ms (%f seconds) (%f MB/s)", duration_per_op, duration_per_op / 1000.0, data_length / duration_per_op / 1000.0));
        System.out.println("hash bytes: " + hash.length + ", bits: " + (hash.length * 8));
        System.out.println(Arrays.toString(hash));
    }
    
}
