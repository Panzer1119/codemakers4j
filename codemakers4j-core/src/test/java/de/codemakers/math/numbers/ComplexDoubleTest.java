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

package de.codemakers.math.numbers;

public class ComplexDoubleTest {
    
    public static final void main(String[] args) {
        final ComplexDouble complexDouble_1 = new ComplexDouble(4.0, 3.0);
        System.out.println("complexDouble_1             =" + complexDouble_1);
        System.out.println("complexDouble_1.conjugate() =" + complexDouble_1.conjugate());
        System.out.println("complexDouble_1.inverse()   =" + complexDouble_1.inverse());
        System.out.println("complexDouble_1.absSquared()=" + complexDouble_1.absSquared());
        System.out.println("complexDouble_1.abs()       =" + complexDouble_1.abs());
        System.out.println("complexDouble_1.sqrt()      =" + complexDouble_1.sqrt());
        System.out.println("complexDouble_1.sgn()       =" + complexDouble_1.sgn());
        System.out.println("complexDouble_1.sgn().abs() =" + complexDouble_1.sgn().abs());
        System.out.println("complexDouble_1.ln()        =" + complexDouble_1.ln());
        System.out.println("complexDouble_1.log(2)      =" + complexDouble_1.log(2));
        System.out.println("complexDouble_1.log(2+0i)   =" + complexDouble_1.log(new ComplexDouble(2)));
        System.out.println("complexDouble_1.log(2-4i)   =" + complexDouble_1.log(new ComplexDouble(2, -4)));
        System.out.println("complexDouble_1.arg()       =" + complexDouble_1.arg());
        System.out.println("complexDouble_1.pow(3)      =" + complexDouble_1.pow(3));
        System.out.println("complexDouble_1.exp()       =" + complexDouble_1.exp());
        System.out.println("complexDouble_1.pow(2-4i)   =" + complexDouble_1.pow(new ComplexDouble(2, -4)));
        System.out.println("complexDouble_1.sin()       =" + complexDouble_1.sin());
        System.out.println("complexDouble_1.asin()      =" + complexDouble_1.asin());
        System.out.println("complexDouble_1.sinh()      =" + complexDouble_1.sinh());
        System.out.println("complexDouble_1.cos()       =" + complexDouble_1.cos());
        System.out.println("complexDouble_1.acos()      =" + complexDouble_1.acos());
        System.out.println("complexDouble_1.cosh()      =" + complexDouble_1.cosh());
        System.out.println("complexDouble_1.tan()       =" + complexDouble_1.tan());
        System.out.println("complexDouble_1.atan()      =" + complexDouble_1.atan());
        System.out.println("complexDouble_1.tanh()      =" + complexDouble_1.tanh());
        System.out.println("complexDouble_1.cot()       =" + complexDouble_1.cot());
        System.out.println("complexDouble_1.acot()      =" + complexDouble_1.acot());
        System.out.println("complexDouble_1.coth()      =" + complexDouble_1.coth());
        System.out.println("complexDouble_1.sec()       =" + complexDouble_1.sec());
        System.out.println("complexDouble_1.arcsec()    =" + complexDouble_1.arcsec());
        System.out.println("complexDouble_1.sech()      =" + complexDouble_1.sech());
        System.out.println("complexDouble_1.csc()       =" + complexDouble_1.csc());
        System.out.println("complexDouble_1.arccsc()    =" + complexDouble_1.arccsc());
        System.out.println("complexDouble_1.csch()      =" + complexDouble_1.csch());
        System.out.println("=============================");
        final ComplexDouble complexDouble_2 = new ComplexDouble(5.0);
        System.out.println("complexDouble_2.sin()       =" + complexDouble_2.sin());
        System.out.println("complexDouble_2.cos()       =" + complexDouble_2.cos());
        final ComplexDouble complexDouble_3 = new ComplexDouble(0.0, 5.0);
        System.out.println("complexDouble_3.sin()       =" + complexDouble_3.sin());
        System.out.println("complexDouble_3.cos()       =" + complexDouble_3.cos());
        /*
        //final ComplexDouble complexDouble_4 = new ComplexDouble((Math.random() < 0.5 ? -1.0 : 1.0) * Math.random() * 50, (Math.random() < 0.5 ? -1.0 : 1.0) * Math.random() * 50);
        //final ComplexDouble complexDouble_4 = new ComplexDouble(245786);
        //final ComplexDouble complexDouble_4 = new ComplexDouble(0, 245786);
        System.out.println("complexDouble_4=" + complexDouble_4);
        final int runs = 1000000;
        final long start_1 = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            complexDouble_4.sinNEW();
        }
        final long duration_1 = System.currentTimeMillis() - start_1;
        System.out.println(String.format("Time taken 1 (new): %d ms (%d runs, %f ms per run)", duration_1, runs, duration_1 * 1.0 / runs));
        final long start_2 = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            complexDouble_4.sin();
        }
        final long duration_2 = System.currentTimeMillis() - start_2;
        System.out.println(String.format("Time taken 2 (old): %d ms (%d runs, %f ms per run)", duration_2, runs, duration_2 * 1.0 / runs));
        
        /*
        final ComplexDouble complexDouble_4 = new ComplexDouble((Math.random() < 0.5 ? -1.0 : 1.0) * Math.random() * 50, (Math.random() < 0.5 ? -1.0 : 1.0) * Math.random() * 50);
        System.out.println("complexDouble_4=" + complexDouble_4);
        final int runs = 1000000;
        final long start_1 = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            complexDouble_4.cos();
        }
        final long duration_1 = System.currentTimeMillis() - start_1;
        System.out.println(String.format("Time taken 1 (new): %d ms (%d runs, %f ms per run)", duration_1, runs, duration_1 * 1.0 / runs));
        final long start_2 = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            complexDouble_4.cosOLD();
        }
        final long duration_2 = System.currentTimeMillis() - start_2;
        System.out.println(String.format("Time taken 2 (old): %d ms (%d runs, %f ms per run)", duration_2, runs, duration_2 * 1.0 / runs));
        */
    }
    
}
