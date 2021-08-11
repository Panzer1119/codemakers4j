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

package de.codemakers.math.numbers;

import de.codemakers.base.util.Require;
import de.codemakers.base.util.interfaces.ByteSerializable;
import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.math.MathUtil;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;

public class ComplexDouble implements ByteSerializable, Copyable {
    
    public static final ComplexDouble MINUS_TWO_MINUS_TWO_I = new ComplexDouble(-2.0, -2.0);
    public static final ComplexDouble ZERO_MINUS_TWO_I = new ComplexDouble(0.0, -2.0);
    public static final ComplexDouble MINUS_TWO_ZERO_I = new ComplexDouble(-2.0);
    public static final ComplexDouble MINUS_ONE_MINUS_ONE_I = new ComplexDouble(-1.0, -1.0);
    public static final ComplexDouble ZERO_MINUS_ONE_I = new ComplexDouble(0.0, -1.0);
    public static final ComplexDouble MINUS_ONE_ZERO_I = new ComplexDouble(-1.0);
    public static final ComplexDouble ZERO_ZERO_I = new ComplexDouble();
    public static final ComplexDouble ONE_ZERO_I = new ComplexDouble(1.0);
    public static final ComplexDouble ZERO_ONE_I = new ComplexDouble(0.0, 1.0);
    public static final ComplexDouble ONE_ONE_I = new ComplexDouble(1.0, 1.0);
    public static final ComplexDouble TWO_ZERO_I = new ComplexDouble(2.0);
    public static final ComplexDouble ZERO_TWO_I = new ComplexDouble(0.0, 2.0);
    public static final ComplexDouble TWO_TWO_I = new ComplexDouble(2.0, 2.0);
    
    protected double re;
    protected double im;
    
    public ComplexDouble() {
        this(0.0);
    }
    
    public ComplexDouble(double re) {
        this(re, 0.0);
    }
    
    public ComplexDouble(double re, double im) {
        this.re = re;
        this.im = im;
    }
    
    public final double getReal() {
        return re;
    }
    
    public final boolean isPurelyReal() {
        return im == 0.0;
    }
    
    public final double getImaginary() {
        return im;
    }
    
    public final boolean isPurelyImaginary() {
        return re == 0.0;
    }
    
    public final ComplexDouble add(ComplexDouble complexDouble) {
        return new ComplexDouble(re + complexDouble.re, im + complexDouble.im);
    }
    
    public final ComplexDouble subtract(ComplexDouble complexDouble) {
        return new ComplexDouble(re - complexDouble.re, im - complexDouble.im);
    }
    
    public final ComplexDouble multiply(ComplexDouble complexDouble) {
        return new ComplexDouble((re * complexDouble.re) - (im * complexDouble.im), (im * complexDouble.re) + (re * complexDouble.im));
    }
    
    public final ComplexDouble divide(ComplexDouble complexDouble) {
        final double temp = complexDouble.absSquared();
        return new ComplexDouble(((re * complexDouble.re) + (im * complexDouble.im)) / temp, ((im * complexDouble.re) - (re * complexDouble.im)) / temp);
    }
    
    public final ComplexDouble negateReal() {
        return new ComplexDouble(-re, im);
    }
    
    public final ComplexDouble negateImaginary() {
        return new ComplexDouble(re, -im);
    }
    
    public final ComplexDouble conjugate() {
        return negateImaginary();
    }
    
    public final ComplexDouble negateBoth() {
        return new ComplexDouble(-re, -im);
    }
    
    public final double absSquared() {
        return (re * re) + (im * im);
    }
    
    public final ComplexDouble absSquaredAsComplex() {
        return new ComplexDouble(absSquared());
    }
    
    public final double abs() {
        return Math.sqrt(absSquared());
    }
    
    public final ComplexDouble absAsComplex() {
        return new ComplexDouble(abs());
    }
    
    public final double arg() {
        return Math.atan2(im, re);
    }
    
    public final ComplexDouble argAsComplex() {
        return new ComplexDouble(arg());
    }
    
    public final ComplexDouble inverseReal() {
        return new ComplexDouble(1.0 / re, im);
    }
    
    public final ComplexDouble inverseImaginary() {
        return new ComplexDouble(re, 1.0 / im);
    }
    
    public final ComplexDouble inverse() {
        final double temp = absSquared();
        return new ComplexDouble(re / temp, -im / temp);
    }
    
    public final ComplexDouble sgn() {
        return divide(absAsComplex());
    }
    
    public final ComplexDouble sqrt() {
        final double temp = abs();
        return new ComplexDouble(Math.sqrt((re + temp) / 2.0), Math.signum(im) * Math.sqrt((temp - re) / 2.0));
    }
    
    public final ComplexDouble ln() {
        return new ComplexDouble(Math.log(abs()), arg());
    }
    
    public final ComplexDouble log(double base) {
        return ln().divide(new ComplexDouble(Math.log(base)));
    }
    
    public final ComplexDouble log(ComplexDouble base) {
        return ln().divide(base.ln());
    }
    
    public final ComplexDouble exp() {
        final double exp_re = Math.exp(re);
        return new ComplexDouble(exp_re * Math.cos(im), exp_re * Math.sin(im));
    }
    
    public final ComplexDouble pow(int n) {
        final double rTon = Math.pow(abs(), n);
        final double nPhi = n * arg();
        return new ComplexDouble(rTon * Math.cos(nPhi), rTon * Math.sin(nPhi));
    }
    
    public final ComplexDouble pow(ComplexDouble complexDouble) {
        return complexDouble.multiply(ln()).exp();
    }
    
    public final ComplexDouble sin() {
        if (isPurelyImaginary()) {
            return new ComplexDouble(0.0, Math.sinh(im));
        }
        return new ComplexDouble(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }
    
    public final ComplexDouble asin() { //FIXME this is not returning the EXACT result, that WolframAlpha returns...
        final double a_2 = re * re;
        final double b_2 = im * im;
        final double c = (a_2 + b_2 - 1);
        final double d = Math.sqrt((c * c) + (4.0 * b_2));
        final double e = a_2 + b_2;
        return new ComplexDouble(Math.signum(re) / 2.0 * Math.acos(d - e), Math.signum(im) / 2.0 * MathUtil.acosh(d + e));
    }
    
    public final ComplexDouble sinh() {
        return new ComplexDouble(Math.cos(im) * Math.sinh(re), Math.sin(im) * Math.cosh(re));
    }
    
    public final ComplexDouble cos() {
        return new ComplexDouble(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }
    
    public final ComplexDouble acos() { //FIXME this is not returning the EXACT result, that WolframAlpha returns...
        return new ComplexDouble(Math.PI / 2.0).subtract(asin());
    }
    
    public final ComplexDouble cosh() {
        return new ComplexDouble(Math.cos(im) * Math.cosh(re), Math.sin(im) * Math.sinh(re));
    }
    
    public final ComplexDouble tan() {
        return sin().divide(cos());
    }
    
    public final ComplexDouble atan() {
        final double temp = absSquared();
        return new ComplexDouble((re == 0.0 ? (Math.abs(im) <= 1.0 ? 0.0 : ((Math.PI / 2.0) * Math.signum(im))) : ((Math.atan((temp - 1.0) / (2.0 * re)) + ((Math.PI / 2.0) * Math
                .signum(re))) / 2.0)), MathUtil.atanh((2.0 * im) / (temp + 1.0)) / 2.0);
    }
    
    public final ComplexDouble tanh() {
        return sinh().divide(cosh());
    }
    
    public final ComplexDouble cot() {
        return cos().divide(sin());
    }
    
    public final ComplexDouble acot() {
        return new ComplexDouble(Math.PI / 2.0).subtract(atan());
    }
    
    public final ComplexDouble coth() {
        return cosh().divide(sinh());
    }
    
    public final ComplexDouble sec() {
        return ONE_ZERO_I.divide(cos());
    }
    
    public final ComplexDouble arcsec() {
        //FIXME THIS IS NOT WORKING!!! (The Real part is correct, but not the Imaginary part)
        return ONE_ZERO_I.divide(this).acos();
    }
    
    public final ComplexDouble sech() {
        return ONE_ZERO_I.divide(cosh());
    }
    
    public final ComplexDouble csc() {
        return ONE_ZERO_I.divide(sin());
    }
    
    public final ComplexDouble arccsc() {
        //FIXME THIS IS NOT WORKING!!! (The Real part is correct, but not the Imaginary part)
        return ONE_ZERO_I.divide(this).asin();
    }
    
    public final ComplexDouble csch() {
        return ONE_ZERO_I.divide(sinh());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        final ComplexDouble that = (ComplexDouble) o;
        return Double.compare(that.re, re) == 0 && Double.compare(that.im, im) == 0;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }
    
    @Override
    public String toString() {
        return "ComplexDouble{" + "re=" + re + ", im=" + im + '}';
    }
    
    @Override
    public Copyable copy() {
        return new ComplexDouble(re, im);
    }
    
    @Override
    public void set(Copyable copyable) {
        final ComplexDouble complexDouble = Require.clazz(copyable, ComplexDouble.class);
        if (complexDouble != null) {
            this.re = complexDouble.re;
            this.im = complexDouble.im;
        }
    }
    
    @Override
    public Optional<byte[]> toBytes() {
        final ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES * 2);
        byteBuffer.putDouble(re);
        byteBuffer.putDouble(im);
        return Optional.of(byteBuffer.array());
    }
    
    @Override
    public boolean fromBytes(byte[] bytes) {
        if (bytes == null || bytes.length != Double.BYTES * 2) {
            return false;
        }
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        this.re = byteBuffer.getDouble();
        this.im = byteBuffer.getDouble();
        return true;
    }
    
}
