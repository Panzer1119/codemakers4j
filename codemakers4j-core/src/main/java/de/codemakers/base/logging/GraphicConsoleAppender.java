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

package de.codemakers.base.logging;

import de.codemakers.base.Standard;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;

import java.io.Serializable;
import java.util.Objects;

@Plugin(name = GraphicConsoleAppender.PLUGIN_NAME, category = "Core", elementType = Appender.ELEMENT_TYPE, printObject = true)
public class GraphicConsoleAppender extends AbstractAppender {
    
    /*
    //TODO FIXME
    Für log4j2-test.xml und log4j2.xml ConsoleAppender austauschen mit einem eigenen, da als target SYSTEM_OUT oder SYSTEM_ERR benutzt wird
    wo er aber auf System.out oder System.err vermutlich zurückgreift und das sollte ja von uns verändert worden sein, um alle System.out.println...
    abzufangen.
    Also einen eigenen ConsoleAppender machen, der die echten/originalen System.out und System.err aus Standard benutzt.
     */
    
    public static final String PLUGIN_NAME = "GraphicConsole";
    
    /**
     * Builds ConsoleAppender instances.
     *
     * @param <B> The type to build
     */
    public static class Builder<B extends Builder<B>> extends AbstractAppender.Builder<B> implements org.apache.logging.log4j.core.util.Builder<GraphicConsoleAppender> {
        
        @PluginBuilderAttribute
        private String consoleName = "default";
        
        @Override
        public GraphicConsoleAppender build() {
            final String consoleName = getConsoleName();
            final Console console = Standard.getConsoleByName(consoleName);
            if (console == null) {
                //TODO Throw error?
            }
            return new GraphicConsoleAppender(getName(), getFilter(), getLayout(), isIgnoreExceptions(), getPropertyArray(), console);
        }
        
        public String getConsoleName() {
            return consoleName;
        }
        
        public B setConsoleName(String consoleName) {
            this.consoleName = consoleName;
            return asBuilder();
        }
        
    }
    
    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return new Builder<B>().asBuilder();
    }
    
    private final Console console;
    
    public GraphicConsoleAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties, Console console) {
        super(name, filter, layout, ignoreExceptions, properties);
        this.console = Objects.requireNonNull(console);
    }
    
    @Override
    public void append(LogEvent logEvent) {
        console.addLogEvent(logEvent);
    }
    
}
