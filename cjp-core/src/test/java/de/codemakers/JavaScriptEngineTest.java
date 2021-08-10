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

package de.codemakers;/*
 *    Copyright 2018 Paul Hagedorn (Panzer1119)
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

import de.codemakers.base.CJP;
import de.codemakers.base.scripting.JavaScriptEngine;
import de.codemakers.base.scripting.JavaScriptEngineBuilder;
import de.codemakers.io.SerializationUtil;

import javax.script.ScriptException;
import java.util.concurrent.TimeUnit;

public class JavaScriptEngineTest {
    
    public static final void main(String[] args) throws ScriptException, InterruptedException {
        final JavaScriptEngineBuilder javaScriptEngineBuilder = new JavaScriptEngineBuilder();
        //javaScriptEngineBuilder.addImports("Packages.de.codemakers.io.SerializationUtil");
        javaScriptEngineBuilder.addImports(SerializationUtil.class);
        final JavaScriptEngine javaScriptEngine = javaScriptEngineBuilder.build();
        //final String code = "System.currentTimeMillis();";
        //javaScriptEngine.put("E", Math.E);
        //javaScriptEngine.put("PI", Math.PI);
        final String code = "return SerializationUtil.objectToBytes(\"Test\");";
        System.out.println(code + " => " + javaScriptEngine.execute(code));
        javaScriptEngine.executeLarge("Thread.sleep(4000); return 1234;").queue(System.out::println, System.err::println);
        CJP.shutdown().queueAfter(6, TimeUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            System.out.println("i: " + i);
        }
    }
    
}
