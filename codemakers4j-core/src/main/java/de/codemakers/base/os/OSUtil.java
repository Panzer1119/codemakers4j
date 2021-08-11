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

package de.codemakers.base.os;

import de.codemakers.base.Standard;
import de.codemakers.base.env.SystemProperties;
import de.codemakers.base.logging.LogLevel;
import de.codemakers.base.os.functions.OSFunction;
import de.codemakers.base.os.functions.RegisterOSFunction;
import de.codemakers.base.reflection.ReflectionUtil;
import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.base.util.tough.ToughSupplier;
import de.codemakers.io.file.AdvancedFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class OSUtil {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final String[] STANDARD_OS_NAMES = new String[] {"Windows", "Linux", "Mac OS", "SunOS", "FreeBSD"};
    
    public static final String OS_NAME = SystemProperties.getOSName();
    public static final String OS_ARCH = SystemProperties.getOSArch();
    public static final String JAVA_VERSION = SystemProperties.getJavaVersion();
    
    public static final String USER_HOME = SystemProperties.getUserHome();
    public static final String USER_DIR = SystemProperties.getUserDir();
    
    //public static final AdvancedFile USER_HOME_DIRECTORY = new AdvancedFile(USER_HOME);
    //public static final AdvancedFile USER_DIR_DIRECTORY = new AdvancedFile(USER_DIR);
    public static final WindowsHelper WINDOWS_HELPER = new WindowsHelper();
    public static final LinuxHelper LINUX_HELPER = new LinuxHelper();
    public static final MacOSHelper MAC_OS_HELPER = new MacOSHelper();
    public static final OSHelper DEFAULT_HELPER = LINUX_HELPER;
    public static final CurrentOSHelper CURRENT_OS_HELPER = new CurrentOSHelper();
    private static final OS CURRENT_OS = OS.byName(OS_NAME);
    public static final OSHelper SYSTEM_OS_HELPER = CURRENT_OS.getHelper();
    
    private static final AtomicBoolean LOADED_OS_FUNCTIONS = new AtomicBoolean(false);
    
    static {
        //initAsync(); //FIXME Cause some problems with SpringBoot etc...
    }
    
    public static final void initAsync() {
        new Thread(() -> Standard.silentError(OSUtil::init)).start(); //This is here, because calling the class "Standard" directly from here causes Errors
    }
    
    private static final void init() {
        if (LOADED_OS_FUNCTIONS.get()) {
            return;
        }
        final Set<Class<?>> classes = ReflectionUtil.getTypesAnnotatedWith(RegisterOSFunction.class);
        for (Class<?> clazz : classes) {
            registerClassAsOSFunction(clazz);
        }
        initCurrent();
        LOADED_OS_FUNCTIONS.set(true);
    }
    
    private static final boolean registerClassAsOSFunction(Class<?> clazz) {
        try {
            final RegisterOSFunction registerOSFunction = clazz.getAnnotation(RegisterOSFunction.class);
            final OSFunction osFunction = (OSFunction) clazz.newInstance();
            logger.log(LogLevel.FINEST, String.format("[%s]: Registering %s: \"%s\" as %s (Annotation: \"%s\")", OSUtil.class.getSimpleName(), OSFunction.class
                    .getSimpleName(), osFunction, osFunction.getClass().getSuperclass().getName(), registerOSFunction));
            for (OS os : registerOSFunction.supported()) {
                os.getHelper().addOSFunction(osFunction);
            }
            logger.log(LogLevel.FINER, String.format("[%s]: Registered %s: \"%s\" as %s (Annotation: \"%s\")", OSUtil.class.getSimpleName(), OSFunction.class
                    .getSimpleName(), osFunction, osFunction.getClass().getSuperclass().getName(), registerOSFunction));
            return true;
        } catch (Exception ex) {
            logger.error(ex);
            return false;
        }
    }
    
    private static final void initCurrent() {
        if (LOADED_OS_FUNCTIONS.get()) {
            return;
        }
        CURRENT_OS.getHelper().getOSFunctions().forEach(CURRENT_OS_HELPER::addOSFunction);
    }
    
    public static final WindowsHelper getWindowsHelper() {
        return WINDOWS_HELPER;
    }
    
    public static final LinuxHelper getLinuxHelper() {
        return LINUX_HELPER;
    }
    
    public static final MacOSHelper getMacOSHelper() {
        return MAC_OS_HELPER;
    }
    
    public static final CurrentOSHelper getCurrentHelper() {
        return CURRENT_OS_HELPER;
    }
    
    public static final OSHelper getSystemOSHelper() {
        return SYSTEM_OS_HELPER;
    }
    
    public static final OS getCurrentOS() {
        return CURRENT_OS;
    }
    
    public static final <T extends OSFunction> T getFunction(Class<T> clazz) {
        while (!LOADED_OS_FUNCTIONS.get()) {
            Standard.silentSleep(100);
        }
        switch (CURRENT_OS) {
            case WINDOWS:
                return WINDOWS_HELPER.getOSFunction(clazz);
            case MACOS:
                return MAC_OS_HELPER.getOSFunction(clazz);
            case LINUX:
                return LINUX_HELPER.getOSFunction(clazz);
            case FREEBSD:
            case SUNOS:
            case UNKNOWN:
                return DEFAULT_HELPER.getOSFunction(clazz);
            default:
                return null;
        }
    }
    
    public static final <T extends OSFunction> T getCurrentFunction(Class<T> clazz) {
        while (!LOADED_OS_FUNCTIONS.get()) {
            Standard.silentSleep(100);
        }
        return CURRENT_OS_HELPER.getOSFunction(clazz);
    }
    
    public static final <T extends OSFunction, R> R invokeFunction(Class<T> clazz, ToughFunction<T, R> toughFunction) {
        return invokeFunction(clazz, toughFunction, null);
    }
    
    public static final <T extends OSFunction, R> R invokeFunction(Class<T> clazz, ToughFunction<T, R> toughFunction, ToughSupplier<R> defaultValueSupplier) {
        return invokeOSFunction(getFunction(clazz), toughFunction, defaultValueSupplier);
    }
    
    public static final <T extends OSFunction, R> R invokeCurrentFunction(Class<T> clazz, ToughFunction<T, R> toughFunction) {
        return invokeCurrentFunction(clazz, toughFunction, null);
    }
    
    public static final <T extends OSFunction, R> R invokeCurrentFunction(Class<T> clazz, ToughFunction<T, R> toughFunction, ToughSupplier<R> defaultValueSupplier) {
        return invokeOSFunction(getCurrentFunction(clazz), toughFunction, defaultValueSupplier);
    }
    
    public static final <T extends OSFunction, R> R invokeOSFunction(T osFunction, ToughFunction<T, R> toughFunction, ToughSupplier<R> defaultValueSupplier) {
        if (osFunction == null || toughFunction == null) {
            return defaultValueSupplier == null ? null : defaultValueSupplier.getWithoutException();
        }
        return toughFunction.applyWithoutException(osFunction);
    }
    
    public static AdvancedFile getUserHomeDirectory() {
        return new AdvancedFile(USER_HOME);
    }
    
    public static AdvancedFile getUserCurrentDirectory() {
        return new AdvancedFile(USER_DIR);
    }
    
    public static AdvancedFile getUserDirDirectory() {
        return new AdvancedFile(SystemProperties.getUserDir());
    }
    
    public static AdvancedFile getUsersDirectory() {
        return SYSTEM_OS_HELPER.getUsersDirectory();
    }
    
    public static String getUser() {
        return SYSTEM_OS_HELPER.getUser();
    }
    
    public static AdvancedFile getUserDirectory(String user) {
        return SYSTEM_OS_HELPER.getUserDirectory(user);
    }
    
    public static AdvancedFile getAppDataDirectory() {
        return SYSTEM_OS_HELPER.getAppDataDirectory();
    }
    
    public static AdvancedFile getAppDataSubDirectory(String name) {
        return SYSTEM_OS_HELPER.getAppDataSubDirectory(name);
    }
    
}
