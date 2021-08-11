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

package de.codemakers.math.eval_1.value_double;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class DoubleEvaluatorTest {
    
    public static final void main(String[] args) throws Exception {
        final DoubleEvaluator doubleEvaluator = new DoubleEvaluator();
        System.out.println("result = " + doubleEvaluator.eval("1.00+23.4545 - (-3*4) * 4 + (5 - 3 * (4 - 7)) / (3 ^-2)").getResult());
        final File folder = new File("test_3");
        folder.mkdirs();
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(folder.getPath() + File.separator + "test_1.txt"), false));
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        bufferedWriter.write(gson.toJson(doubleEvaluator.temp));
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    
}
