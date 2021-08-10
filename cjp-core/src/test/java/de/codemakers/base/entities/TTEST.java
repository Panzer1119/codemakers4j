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

import de.codemakers.base.entities.data.Data;
import de.codemakers.base.entities.data.DeltaData;
import de.codemakers.base.entities.data.IncrementalData;
import de.codemakers.base.entities.data.KeepingIncrementalData;

import java.util.Arrays;
import java.util.Optional;

public class TTEST {
    
    public static void main(String[] args) throws Exception {
        final Data data_1 = new Data("Test".getBytes());
        System.out.println("data_1=" + data_1);
        final Optional<byte[]> optional_1 = data_1.toBytes();
        if (optional_1.isEmpty()) {
            return;
        }
        final byte[] temp_1 = optional_1.get();
        System.out.println("temp_1=" + Arrays.toString(temp_1));
        final Data data_2 = new Data(null);
        System.out.println("data_2=" + data_2);
        data_2.fromBytes(temp_1);
        System.out.println("data_2=" + data_2);
        //
        System.out.println();
        System.out.println();
        System.out.println();
        //
        final IncrementalData incrementalData_1 = new IncrementalData("Test".getBytes());
        System.out.println("incrementalData_1=" + incrementalData_1);
        final Optional<byte[]> optional_2 = incrementalData_1.toBytes();
        if (optional_2.isEmpty()) {
            return;
        }
        final byte[] temp_2 = optional_2.get();
        System.out.println("temp=" + Arrays.toString(temp_2));
        final IncrementalData incrementalData_2 = new IncrementalData(null);
        System.out.println("incrementalData_2=" + incrementalData_2);
        incrementalData_2.fromBytes(temp_2);
        System.out.println("incrementalData_2=" + incrementalData_2);
        //
        System.out.println();
        System.out.println();
        System.out.println();
        //
        final KeepingIncrementalData keepingIncrementalData = new KeepingIncrementalData("Test".getBytes());
        final DeltaData deltaData = keepingIncrementalData.changeData("Test2".getBytes());
        final KeepingIncrementalData keepingIncrementalData_1 = new KeepingIncrementalData("Test".getBytes());
        System.out.println("keepingIncrementalData_1=" + keepingIncrementalData_1);
        keepingIncrementalData_1.incrementData(deltaData);
        System.out.println("keepingIncrementalData_1=" + keepingIncrementalData_1);
        final Optional<byte[]> optional_3 = keepingIncrementalData_1.toBytes();
        if (optional_3.isEmpty()) {
            return;
        }
        final byte[] temp_3 = optional_3.get();
        System.out.println("temp_3=" + Arrays.toString(temp_3));
        final KeepingIncrementalData keepingIncrementalData_2 = new KeepingIncrementalData(null);
        System.out.println("keepingIncrementalData_2=" + keepingIncrementalData_2);
        keepingIncrementalData_2.fromBytes(temp_3);
        System.out.println("keepingIncrementalData_2=" + keepingIncrementalData_2);
    }
}
