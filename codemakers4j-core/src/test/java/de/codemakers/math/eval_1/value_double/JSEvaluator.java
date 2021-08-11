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

import de.codemakers.base.scripting.JavaScriptEngine;
import de.codemakers.base.scripting.JavaScriptEngineBuilder;
import de.codemakers.math.eval_1.Evaluator;

public class JSEvaluator extends Evaluator<Double> {
    
    private final JavaScriptEngineBuilder javaScriptEngineBuilder = new JavaScriptEngineBuilder();
    private JavaScriptEngine javaScriptEngine = javaScriptEngineBuilder.build();
    
    protected Double evalIntern(String expression) throws Exception {
        final Object result = javaScriptEngine.eval(expression);
        return (result instanceof Integer) ? (int) result : (double) result;
    }
    
    public JSEvaluator resetEngine() {
        javaScriptEngine = javaScriptEngineBuilder.build();
        return this;
    }
    
}
