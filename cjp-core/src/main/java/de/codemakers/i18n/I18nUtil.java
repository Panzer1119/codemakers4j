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

import de.codemakers.base.Standard;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class I18nUtil {
    
    private static final String PATH_BASE = (Standard.MAIN_PATH + "i18n/").substring(1);
    private static final String PATH_CONSOLE = PATH_BASE + "console";
    private static final String PATH_LOG_LEVEL = PATH_BASE + "log_level";
    private static final String PATH_UI = PATH_BASE + "ui";
    
    private static volatile Locale LOCALE = Locale.getDefault();
    
    private static ResourceBundle RESOURCE_BUNDLE_CONSOLE;
    private static ResourceBundle RESOURCE_BUNDLE_LOG_LEVEL;
    private static ResourceBundle RESOURCE_BUNDLE_UI;
    private static final Map<String, ResourceBundle> RESOURCE_BUNDLES = new ConcurrentHashMap<>();
    
    static {
        reload();
    }
    
    public static synchronized void setLocale(Locale locale) {
        LOCALE = locale;
        Locale.setDefault(locale);
        Locale.setDefault(Locale.Category.DISPLAY, locale);
        Locale.setDefault(Locale.Category.FORMAT, locale);
        reload();
    }
    
    private static synchronized void reload() {
        loadResourceBundles();
        loadCustomResourceBundles();
        I18nReloadEventHandler.triggerReload(LOCALE);
    }
    
    private static synchronized void loadResourceBundles() {
        RESOURCE_BUNDLE_CONSOLE = loadResourceBundle(PATH_CONSOLE);
        RESOURCE_BUNDLE_LOG_LEVEL = loadResourceBundle(PATH_LOG_LEVEL);
        RESOURCE_BUNDLE_UI = loadResourceBundle(PATH_UI);
    }
    
    private static synchronized ResourceBundle loadResourceBundle(String path) {
        return ResourceBundle.getBundle(path, LOCALE);
    }
    
    private static synchronized void loadCustomResourceBundles() {
        RESOURCE_BUNDLES.keySet().forEach(I18nUtil::loadCustomResourceBundle);
    }
    
    private static synchronized void loadCustomResourceBundle(String path) {
        RESOURCE_BUNDLES.put(path, loadResourceBundle(path));
    }
    
    public static ResourceBundle getResourceBundleConsole() {
        return RESOURCE_BUNDLE_CONSOLE;
    }
    
    public static ResourceBundle getResourceBundleLogLevel() {
        return RESOURCE_BUNDLE_LOG_LEVEL;
    }
    
    public static ResourceBundle getResourceBundleUi() {
        return RESOURCE_BUNDLE_UI;
    }
    
    public static void addEventListener(I18nReloadEventListener eventListener) {
        I18nReloadEventHandler.addEventListener(eventListener);
    }
    
    public static void removeEventListener(I18nReloadEventListener eventListener) {
        I18nReloadEventHandler.removeEventListener(eventListener);
    }
    
    public static void triggerReload(Locale locale) {
        I18nReloadEventHandler.triggerReload(locale);
    }
    
    public static void registerResourceBundle(String path) {
        loadCustomResourceBundle(path);
    }
    
    public static ResourceBundle getResourceBundle(String path) {
        return RESOURCE_BUNDLES.get(path);
    }
    
}
