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

package de.codemakers.base.entities;

import de.codemakers.base.entities.data.DeltaData;
import de.codemakers.base.entities.data.IncrementalData;

import java.util.Arrays;

public class IncrementalDataTest {
    
    public static final void main(String[] args) {
        String test = "Test";
        System.out.println("test=" + test);
        byte[] test_bytes = test.getBytes();
        System.out.println("Arrays.toString(test_bytes)=" + Arrays.toString(test_bytes));
        final IncrementalData incrementalData_1 = new IncrementalData(test_bytes);
        System.out.println("incrementalData_1=" + incrementalData_1);
        System.out.println("new String(incrementalData_1.getData())=" + new String(incrementalData_1.getData()));
        test = "tEST";
        System.out.println("test=" + test);
        test_bytes = test.getBytes();
        System.out.println("Arrays.toString(test_bytes)=" + Arrays.toString(test_bytes));
        final DeltaData delta_Data_1 = incrementalData_1.changeData(test_bytes);
        System.out.println("delta_Data_1=" + delta_Data_1);
        System.out.println("incrementalData_1=" + incrementalData_1);
        System.out.println("new String(incrementalData_1.getData())=" + new String(incrementalData_1.getData()));
        final IncrementalData incrementalData_2 = new IncrementalData("Test".getBytes());
        System.out.println("incrementalData_2=" + incrementalData_2);
        System.out.println("new String(incrementalData_2.getData())=" + new String(incrementalData_2.getData()));
        incrementalData_2.incrementData(delta_Data_1);
        System.out.println("incrementalData_2=" + incrementalData_2);
        System.out.println("new String(incrementalData_2.getData())=" + new String(incrementalData_2.getData()));
    }
    
}
