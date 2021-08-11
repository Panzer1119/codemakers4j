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

package de.codemakers.i18n;

import de.codemakers.base.events.EventHandler;
import de.codemakers.base.logging.LogLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public final class I18nReloadEventHandler extends EventHandler<I18nReloadEvent> {
    
    private static final Logger logger = LogManager.getLogger();
    
    private static final EventHandler<I18nReloadEvent> INSTANCE;
    
    static {
        INSTANCE = new I18nReloadEventHandler();
        INSTANCE.setForceEvents(true);
    }
    
    private I18nReloadEventHandler() {
        super(null);
    }
    
    private static EventHandler<I18nReloadEvent> getInstance() {
        return INSTANCE;
    }
    
    protected static void addEventListener(I18nReloadEventListener eventListener) {
        getInstance().addEventListener(eventListener);
    }
    
    protected static void removeEventListener(I18nReloadEventListener eventListener) {
        getInstance().removeEventListener(eventListener);
    }
    
    protected static void triggerReload(Locale locale) {
        final I18nReloadEvent event = new I18nReloadEvent(locale);
        logger.log(LogLevel.FINE, "Triggering I18nReloadEvent: {}", event);
        getInstance().onEvent(event);
    }
    
}
