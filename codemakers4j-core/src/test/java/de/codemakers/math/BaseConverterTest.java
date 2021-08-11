package de.codemakers.math;

import de.codemakers.base.multiplets.Doublet;

import java.util.Arrays;

public class BaseConverterTest {
    
    public static void main(String[] args) throws Exception {
        for (int i = 31; i <= 128; i++) {
            System.out.println(i + "=" + ((char) i));
        }
        System.out.println(Arrays.toString(BaseConverter.CHARSET_BASE_94_CUSTOM));
        System.out.println("128  <=> " + BaseConverter.convertDecimalToBase(128, 16));
        System.out.println("256  <=> " + BaseConverter.convertDecimalToBase(256, 16));
        System.out.println("257  <=> " + BaseConverter.convertDecimalToBase(257, 16));
        System.out.println("-457 <=> " + BaseConverter.convertDecimalToBase(-457, 16));
        System.out.println("3567685368L <=> " + BaseConverter.convertDecimalToBase(3567685368L, 16));
        System.out.println("3567685368L <=> " + BaseConverter.convertDecimalToBase(3567685368L, 32));
        System.out.println("3567685368L <=> " + BaseConverter.convertDecimalToBase(3567685368L, 64));
        System.out.println("3567685368L <=> " + BaseConverter.convertDecimalToBase(3567685368L, 94));
        final long number_1 = 23756258L;
        final Doublet<int[], Boolean> number_1_2 = BaseConverter.convertDecimalToBaseArray(number_1, 2); //2^1
        final Doublet<int[], Boolean> number_1_8 = BaseConverter.convertDecimalToBaseArray(number_1, 8); // 2^3
        final Doublet<int[], Boolean> number_1_10 = BaseConverter.convertDecimalToBaseArray(number_1, 10); // 2^3.3219
        final Doublet<int[], Boolean> number_1_16 = BaseConverter.convertDecimalToBaseArray(number_1, 16); // 2^4
        final Doublet<int[], Boolean> number_1_32 = BaseConverter.convertDecimalToBaseArray(number_1, 32); // 2^5
        final Doublet<int[], Boolean> number_1_64 = BaseConverter.convertDecimalToBaseArray(number_1, 64); // 2^6
        final Doublet<int[], Boolean> number_1_94 = BaseConverter.convertDecimalToBaseArray(number_1, 94); // 2^6.5546
        final Doublet<int[], Boolean> number_1_128 = BaseConverter.convertDecimalToBaseArray(number_1, 128); // 2^7
        final Doublet<int[], Boolean> number_1_256 = BaseConverter.convertDecimalToBaseArray(number_1, 256); // 2^8
        final Doublet<int[], Boolean> number_1_512 = BaseConverter.convertDecimalToBaseArray(number_1, 512); // 2^9
        final Doublet<int[], Boolean> number_1_1024 = BaseConverter.convertDecimalToBaseArray(number_1, 1024); // 2^10
        final Doublet<int[], Boolean> number_1_2048 = BaseConverter.convertDecimalToBaseArray(number_1, 2048); // 2^11
        final Doublet<int[], Boolean> number_1_4096 = BaseConverter.convertDecimalToBaseArray(number_1, 4096); // 2^12
        final Doublet<int[], Boolean> number_1_8192 = BaseConverter.convertDecimalToBaseArray(number_1, 8192); // 2^13
        final Doublet<int[], Boolean> number_1_65536 = BaseConverter.convertDecimalToBaseArray(number_1, 65536); // 2^16
        final Doublet<int[], Boolean> number_1_1048576 = BaseConverter.convertDecimalToBaseArray(number_1, 1048576); // 2^20
        final Doublet<int[], Boolean> number_1_16777216 = BaseConverter.convertDecimalToBaseArray(number_1, 16777216); // 2^24
        final Doublet<int[], Boolean> number_1_1073741824 = BaseConverter.convertDecimalToBaseArray(number_1, 1073741824); // 2^30
        System.out.println("number_1           =" + number_1);
        System.out.println("number_1_2         =" + Arrays.toString(number_1_2.getA()));
        System.out.println("number_1_8         =" + Arrays.toString(number_1_8.getA()));
        System.out.println("number_1_10        =" + Arrays.toString(number_1_10.getA()));
        System.out.println("number_1_16        =" + Arrays.toString(number_1_16.getA()));
        System.out.println("number_1_32        =" + Arrays.toString(number_1_32.getA()));
        System.out.println("number_1_64        =" + Arrays.toString(number_1_64.getA()));
        System.out.println("number_1_94        =" + Arrays.toString(number_1_94.getA()));
        System.out.println("number_1_128       =" + Arrays.toString(number_1_128.getA()));
        System.out.println("number_1_256       =" + Arrays.toString(number_1_256.getA()));
        System.out.println("number_1_512       =" + Arrays.toString(number_1_512.getA()));
        System.out.println("number_1_1024      =" + Arrays.toString(number_1_1024.getA()));
        System.out.println("number_1_2048      =" + Arrays.toString(number_1_2048.getA()));
        System.out.println("number_1_4096      =" + Arrays.toString(number_1_4096.getA()));
        System.out.println("number_1_8192      =" + Arrays.toString(number_1_8192.getA()));
        System.out.println("number_1_65536     =" + Arrays.toString(number_1_65536.getA()));
        System.out.println("number_1_1048576   =" + Arrays.toString(number_1_1048576.getA()));
        System.out.println("number_1_16777216  =" + Arrays.toString(number_1_16777216.getA()));
        System.out.println("number_1_1073741824=" + Arrays.toString(number_1_1073741824.getA()));
    }
    
}
