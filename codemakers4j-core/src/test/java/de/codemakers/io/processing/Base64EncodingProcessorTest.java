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

package de.codemakers.io.processing;

import de.codemakers.base.util.ArrayUtil;

import java.util.Arrays;
import java.util.Base64;

public class Base64EncodingProcessorTest {
    
    public static final void main(String[] args) {
        final Base64EncodingProcessor base64EncodingProcessor = new Base64EncodingProcessor();
        final String string_1 = "Test String";
        System.out.println("string_1           =" + string_1);
        final byte[] bytes_1 = string_1.getBytes();
        System.out.println("bytes_1            =" + Arrays.toString(bytes_1));
        final String base64_1_string_org = Base64.getEncoder().encodeToString(bytes_1);
        System.out.println("base64_1_string_org=" + base64_1_string_org);
        final byte[] base64_1_bytes_org = Base64.getEncoder().encode(bytes_1);
        System.out.println("base64_1_bytes_org =" + Arrays.toString(base64_1_bytes_org));
        System.out.println("base64_1_bytes_orgS=" + new String(base64_1_bytes_org));
        byte[] base64_1_bytes_new = new byte[0];
        for (byte b : bytes_1) {
            final byte[] temp = base64EncodingProcessor.process(b);
            if (temp != null) {
                base64_1_bytes_new = ArrayUtil.concatArrays(base64_1_bytes_new, temp);
                System.out.println("temp               =" + Arrays.toString(temp));
            }
        }
        final byte[] temp = base64EncodingProcessor.doFinal();
        if (temp != null) {
            base64_1_bytes_new = ArrayUtil.concatArrays(base64_1_bytes_new, temp);
            System.out.println("end                =" + Arrays.toString(temp));
        }
        System.out.println("base64_1_bytes_new =" + Arrays.toString(base64_1_bytes_new));
        System.out.println("base64_1_bytes_newS=" + new String(base64_1_bytes_new));
        System.out.println("Arrays.equals(base64_1_bytes_org, base64_1_bytes_new)=" + Arrays.equals(base64_1_bytes_org, base64_1_bytes_new));
    }
    
}
