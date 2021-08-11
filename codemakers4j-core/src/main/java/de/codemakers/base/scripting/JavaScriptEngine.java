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

package de.codemakers.base.scripting;

import de.codemakers.base.action.ReturningAction;

import javax.script.*;
import java.io.Reader;

public class JavaScriptEngine implements ScriptEngine {

    private final JavaScriptEngineBuilder javaScriptEngineBuilder;
    private ScriptEngine scriptEngine;

    protected JavaScriptEngine(JavaScriptEngineBuilder javaScriptEngineBuilder, ScriptEngine scriptEngine) {
        this.javaScriptEngineBuilder = javaScriptEngineBuilder;
        this.scriptEngine = scriptEngine;
    }

    public static final JavaScriptEngine getStandardJavaScriptEngine() {
        return new JavaScriptEngineBuilder().build();
    }

    private static final String toFunction(String code) {
        return String.format("(function(){with(imports){%s}})();", code);
    }

    public final JavaScriptEngineBuilder getJavaScriptEngineBuilder() {
        return javaScriptEngineBuilder;
    }

    public final ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

    public final ScriptEngine reset() {
        final ScriptEngine old = scriptEngine;
        scriptEngine = javaScriptEngineBuilder.buildScriptEngine();
        return old;
    }

    @Override
    public final Object eval(String script, ScriptContext context) throws ScriptException {
        return scriptEngine.eval(script, context);
    }

    @Override
    public final Object eval(Reader reader, ScriptContext context) throws ScriptException {
        return scriptEngine.eval(reader, context);
    }

    @Override
    public final Object eval(String script) throws ScriptException {
        return scriptEngine.eval(script);
    }

    @Override
    public final Object eval(Reader reader) throws ScriptException {
        return scriptEngine.eval(reader);
    }

    @Override
    public final Object eval(String script, Bindings n) throws ScriptException {
        return scriptEngine.eval(script, n);
    }

    @Override
    public final Object eval(Reader reader, Bindings n) throws ScriptException {
        return scriptEngine.eval(reader, n);
    }

    @Override
    public final void put(String key, Object value) {
        scriptEngine.put(key, value);
    }

    @Override
    public final Object get(String key) {
        return scriptEngine.get(key);
    }

    @Override
    public final Bindings getBindings(int scope) {
        return scriptEngine.getBindings(scope);
    }

    @Override
    public final void setBindings(Bindings bindings, int scope) {
        scriptEngine.setBindings(bindings, scope);
    }

    @Override
    public final Bindings createBindings() {
        return scriptEngine.createBindings();
    }

    @Override
    public final ScriptContext getContext() {
        return scriptEngine.getContext();
    }

    @Override
    public final void setContext(ScriptContext context) {
        scriptEngine.setContext(context);
    }

    @Override
    public final ScriptEngineFactory getFactory() {
        return scriptEngine.getFactory();
    }

    public final <T> T execute(String code) throws ScriptException {
        return (T) scriptEngine.eval(toFunction(code));
    }

    public final <T> ReturningAction<T> executeLarge(String code) {
        return new ReturningAction<>(() -> execute(code));
    }

}
