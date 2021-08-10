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

import de.codemakers.base.util.Require;
import de.codemakers.base.util.StringUtil;
import de.codemakers.base.util.interfaces.Copyable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.*;
import java.util.stream.Collectors;

public class JavaScriptEngineBuilder implements Copyable {
    
    private final List<String> imports = new ArrayList<>();
    
    public JavaScriptEngineBuilder(Collection<String> imports) {
        initStandardImports();
        addImports(imports);
    }
    
    public JavaScriptEngineBuilder(String... imports) {
        initStandardImports();
        addImports(imports);
    }
    
    public JavaScriptEngineBuilder() {
        initStandardImports();
    }
    
    protected final JavaScriptEngineBuilder initStandardImports() {
        imports.add("java.io");
        imports.add("java.lang");
        imports.add("java.util");
        return this;
    }
    
    public final JavaScriptEngineBuilder addImports(String... imports) {
        return addImports(Arrays.asList(imports));
    }
    
    public final JavaScriptEngineBuilder addImports(Collection<String> imports) {
        if (imports == null) {
            return this;
        }
        this.imports.addAll(imports.stream().filter(Objects::nonNull).filter(StringUtil::isNotEmpty).collect(Collectors.toList()));
        return this;
    }
    
    public final JavaScriptEngineBuilder addImports(Class<?>... classes) {
        return addImports(Arrays.asList(classes));
    }
    
    public final JavaScriptEngineBuilder addImports(List<Class<?>> classes) {
        if (classes == null) {
            return this;
        }
        classes.stream().filter(Objects::nonNull).map((clazz) -> "Packages." + clazz.getName()).forEach(imports::add);
        return this;
    }
    
    public final JavaScriptEngineBuilder addImportsFromPackages(boolean recursive, String... packages) {
        return addImportsFromPackages(recursive, Arrays.asList(packages));
    }
    
    public JavaScriptEngineBuilder addImportsFromPackages(boolean recursive, List<String> packages) {
        throw new RuntimeException("Not implemented!");
    }
    
    public final JavaScriptEngineBuilder addImportsFromObjects(Object... objects) {
        return addImportsFromObjects(Arrays.asList(objects));
    }
    
    public final JavaScriptEngineBuilder addImportsFromObjects(List<Object> objects) {
        if (objects == null) {
            return this;
        }
        return addImports(objects.stream().filter(Objects::nonNull).map(Object::getClass).collect(Collectors.toList()));
    }
    
    public final List<String> getImports() {
        return imports;
    }
    
    public final JavaScriptEngine build() {
        try {
            final JavaScriptEngine javaScriptEngine = new JavaScriptEngine(copy(), buildScriptEngine());
            return javaScriptEngine;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    protected final ScriptEngine buildScriptEngine() {
        try {
            final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
            scriptEngine.eval(importsToString());
            return scriptEngine;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private final String importsToString() {
        return String.format("var imports = new JavaImporter(%s);", imports.stream().collect(Collectors.joining(", ")));
    }
    
    @Override
    public final JavaScriptEngineBuilder copy() {
        return new JavaScriptEngineBuilder(imports);
    }
    
    @Override
    public void set(Copyable copyable) {
        Objects.requireNonNull(copyable);
        final JavaScriptEngineBuilder javaScriptEngineBuilder = Require.clazz(copyable, JavaScriptEngineBuilder.class);
        this.imports.clear();
        this.imports.addAll(javaScriptEngineBuilder.imports);
    }
    
}
