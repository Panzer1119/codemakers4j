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

package de.codemakers.base.logger;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.io.Serializable;

@Plugin(name = ConsoleAppender.PLUGIN_NAME, category = "Core", elementType = Appender.ELEMENT_TYPE, printObject = true)
public class ConsoleAppender extends AbstractAppender {
    
    public static final String PLUGIN_NAME = "Console";
    
    /**
     * Builds ConsoleAppender instances.
     *
     * @param <B> The type to build
     */
    public static class Builder<B extends Builder<B>> extends AbstractAppender.Builder<B> implements org.apache.logging.log4j.core.util.Builder<ConsoleAppender> {
        
        @PluginBuilderAttribute
        @Required
        private Console console;
        
        @Override
        public ConsoleAppender build() {
            return new ConsoleAppender(getName(), getFilter(), getLayout(), isIgnoreExceptions(), getPropertyArray(), getConsole());
        }
    
        public Console getConsole() {
            return console;
        }
    
        public Builder setConsole(Console console) {
            this.console = console;
            return this;
        }
        
    }
    
    @PluginFactory
    public static <B extends Builder<B>> B newBuilder() {
        return new Builder<B>().asBuilder();
    }
    
    private final Console console;
    
    public ConsoleAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties, Console console) {
        super(name, filter, layout, ignoreExceptions, properties);
        this.console = console;
    }
    
    @Override
    public void append(LogEvent event) {
        //TODO Append to console
        System.out.println("Append: " + event); //REMOVE DEBUG Test
    }
    
}