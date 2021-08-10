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

package de.codemakers.math;

import de.codemakers.base.util.TimeTaker;
import de.codemakers.io.file.AdvancedFile;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PrimeFactorTest {
    
    public static final void main(String[] args) throws Exception {
        //final Random random = new Random();
        //final long l_1 = Math.abs(random.nextLong());
        //final long l_1 = Math.abs(random.nextInt());
        //final long l_1 = 2 * 3 * 3 * 3 * 5 * 5 * 13 * 13 * 21 * 87;
        //System.out.println("l_1=" + l_1);
        /*
        final List<Long> p_1 = primeFactorTest(l_1, true);
        System.out.println("p_1=" + p_1);
        final int max_ops = 100000;
        final TimeTaker timeTaker_1 = new TimeTaker();
        timeTaker_1.start();
        for (int i = 0; i < max_ops; i++) {
            primeFactorTest(l_1, true);
        }
        timeTaker_1.stop();
        System.out.println(String.format("Time taken 1 (doubleWhile=true) : %d ms (per op: %f ms, with %d operations)", timeTaker_1.getDuration(), (timeTaker_1.getDuration() * 1.0 / max_ops), max_ops));
        final TimeTaker timeTaker_2 = new TimeTaker();
        timeTaker_2.start();
        for (int i = 0; i < max_ops; i++) {
            primeFactorTest(l_1, false);
        }
        timeTaker_2.stop();
        System.out.println(String.format("Time taken 2 (doubleWhile=false): %d ms (per op: %f ms, with %d operations)", timeTaker_2.getDuration(), (timeTaker_2.getDuration() * 1.0 / max_ops), max_ops));
        */
        /*
        Test Output:
        l_1=2084597448
        p_1=[2, 2, 2, 3, 8273, 10499]
        Time taken 1 (doubleWhile=true) : 16063 ms (per op: 0.160630 ms, with 100000 operations)
        Time taken 2 (doubleWhile=false): 15779 ms (per op: 0.157790 ms, with 100000 operations)
        */
        //final long l_2 = Math.abs(random.nextLong());
        //System.out.println("l_2=" + l_2);
        //final List<Long> p_2 = primeFactor(l_2);
        //System.out.println("p_2=" + p_2);
        AdvancedFile.DEBUG_TO_STRING = true;
        final AdvancedFile advancedFile_1 = new AdvancedFile("test/primeFactor/test_1.csv");
        System.out.println("advancedFile_1=" + advancedFile_1);
        primeFactor_1(1000000, advancedFile_1);
    }
    
    public static List<Long> primeFactorTest(long number, boolean doubleWhile) {
        final List<Long> primes = new ArrayList<>();
        long l = 2;
        if (doubleWhile) {
            while (l <= number) {
                while (number % l == 0) { //Slower, because the statement is tested twice, to leave the while-loop
                    number /= l;
                    primes.add(l);
                }
                l++;
            }
        } else {
            while (l <= number) {
                if (number % l == 0) {
                    number /= l;
                    primes.add(l);
                } else {
                    l++;
                }
            }
        }
        return primes;
    }
    
    public static List<Long> primeFactor(long number) {
        final List<Long> primes = new ArrayList<>();
        final int max_length = (int) (Math.log10(number) + 1.0);
        long l = 2;
        while (l <= number) {
            if (number % l == 0) {
                System.out.print(String.format("number=%1$" + max_length + "d, l=%2$14d, %1$" + max_length + "d %% %2$18d = ", number, l));
                number /= l;
                primes.add(l);
                System.out.println(String.format("%" + max_length + "d", number));
                //System.out.print(l + ", ");
            } else {
                l++;
            }
        }
        //System.out.println();
        return primes;
    }
    
    public static void primeFactor_1(long max_number, AdvancedFile advancedFile) throws Exception {
        final AtomicLong atomicLong = new AtomicLong(2);
        final BufferedWriter bufferedWriter = advancedFile.createBufferedWriterWithoutException(false);
        bufferedWriter.write("Number;...;PrimeFactorCount");
        bufferedWriter.newLine();
        bufferedWriter.flush();
        final TimeTaker timeTaker = new TimeTaker();
        timeTaker.start();
        while (atomicLong.get() < max_number) {
            //final TimeTaker timeTaker_ = new TimeTaker();
            //timeTaker_.start();
            final long l = atomicLong.getAndIncrement();
            bufferedWriter.write(l + ";");
            primeFactor_1_single(l, bufferedWriter);
            bufferedWriter.newLine();
            //bufferedWriter.flush();
            //timeTaker_.stop();
            //final long l_2 = l + 1;
            //final double p = (l_2 * 100.0) / max_number;
            //if (p % 10.0 == 0) {
            //    System.out.println(String.format("%d / %d = %f%% (Time taken: - ms)", l_2, max_number, p/*, timeTaker_.getDuration()*/));
            //}
        }
        timeTaker.stop();
        System.out.println(String.format("Time taken total: %d ms", timeTaker.getDuration()));
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    
    public static void primeFactor_1_single(long number, BufferedWriter bufferedWriter) throws Exception {
        int i = 0;
        long l = 2;
        while (l <= number) {
            if (number % l == 0) {
                number /= l;
                bufferedWriter.write(l + ";");
                i++;
            } else {
                l++;
            }
        }
        bufferedWriter.write("" + i);
    }
    
}
