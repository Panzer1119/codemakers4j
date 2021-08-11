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

package de.codemakers.base;

import de.codemakers.base.logging.Console;
import de.codemakers.base.logging.DefaultConsole;
import de.codemakers.base.os.OSUtil;
import de.codemakers.base.util.tough.*;
import de.codemakers.io.file.AdvancedFile;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Standard {
    
    public static final String NAME = Standard.class.getName();
    public static final PrintStream SYSTEM_OUTPUT_STREAM = System.out;
    public static final PrintStream SYSTEM_ERROR_STREAM = System.err;
    public static final InputStream SYSTEM_INPUT_STREAM = System.in;
    
    public static final Map<Integer, ToughRunnable> SHUTDOWN_HOOKS = new ConcurrentHashMap<>();
    
    public static final URL RUNNING_JAR_URL = Standard.class.getProtectionDomain().getCodeSource().getLocation();
    public static final URI RUNNING_JAR_URI = silentError(URL::toURI, RUNNING_JAR_URL);
    public static final String RUNNING_JAR_PATH_STRING = RUNNING_JAR_URL.getPath();
    public static final boolean RUNNING_JAR_IS_JAR = RUNNING_JAR_URL.getPath().toLowerCase().endsWith(".jar");
    public static final File RUNNING_JAR_FILE = new File(RUNNING_JAR_URL.getPath());
    public static final AdvancedFile RUNNING_JAR_ADVANCED_FILE = new AdvancedFile(new File(RUNNING_JAR_URI));
    
    public static final String MAIN_PATH = "/de/codemakers/";
    public static final AdvancedFile MAIN_FOLDER = new AdvancedFile(AdvancedFile.PREFIX_INTERN + MAIN_PATH);
    public static final String ICONS_PATH = "icons/";
    public static final AdvancedFile ICONS_FOLDER = new AdvancedFile(MAIN_FOLDER, ICONS_PATH);
    public static final String ICONS_FAT_COW_PATH = "FatCow/";
    public static final AdvancedFile ICONS_FAT_COW_FOLDER = new AdvancedFile(ICONS_FOLDER, ICONS_FAT_COW_PATH);
    public static final String ICONS_16x16_PATH = "16x16/";
    public static final AdvancedFile ICONS_FAT_COW_16x16_FOLDER = new AdvancedFile(ICONS_FAT_COW_FOLDER, ICONS_16x16_PATH);
    public static final String ICONS_32x32_PATH = "32x32/";
    public static final AdvancedFile ICONS_FAT_COW_32x32_FOLDER = new AdvancedFile(ICONS_FAT_COW_FOLDER, ICONS_32x32_PATH);
    
    private static final Map<String, Console> CONSOLES = new ConcurrentHashMap<>();
    
    static {
        addShutdownHookAsSingleThread(() -> SHUTDOWN_HOOKS.values()
                .forEach(ToughRunnable::runWithoutException)); //TODO Clone/Copy the values before execution? So that if a Shutdown Hook modifies #SHUTDOWN_HOOKS no unwanted behaviour is happening?
    }
    
    public static final File getInternFileFromAbsolutePath(String path) {
        Objects.requireNonNull(path);
        return new File(RUNNING_JAR_PATH_STRING + path);
    }
    
    public static final File getInternFileFromRelative(Class<?> clazz, String path) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(path);
        return new File(RUNNING_JAR_PATH_STRING + File.separator + clazz.getPackage()
                .getName()
                .replaceAll("\\.", OSUtil.CURRENT_OS_HELPER.getFileSeparatorRegex()) + File.separator + path);
    }
    
    public static final void async(ToughRunnable toughRunnable) {
        toughThread(toughRunnable).start();
    }
    
    public static Thread toughThread(ToughRunnable toughRunnable) {
        return new Thread(toughRunnable::runWithoutException);
    }
    
    public static final Throwable silentClose(Closeable closeable) {
        return silentError(closeable::close);
    }
    
    public static final Throwable silentError(ToughRunnable toughRunnable) {
        try {
            toughRunnable.run();
            return null;
        } catch (Exception ex) {
            return ex;
        }
    }
    
    public static final <T> void silentError(ToughConsumer<T> toughConsumer, T input) {
        try {
            toughConsumer.accept(input);
        } catch (Exception ex) {
            //Nothing
        }
    }
    
    public static final <T, R> R silentError(ToughFunction<T, R> toughFunction, T input) {
        try {
            return toughFunction.apply(input);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static final <R> R silentError(ToughSupplier<R> toughSupplier) {
        try {
            return toughSupplier.get();
        } catch (Exception ex) {
            return null;
        }
    }
    
    public static final Throwable silentSleep(long millis) {
        try {
            Thread.sleep(millis);
            return null;
        } catch (Exception ex) {
            return ex;
        }
    }
    
    public static final Throwable silentSleep(long millis, int nanos) {
        try {
            Thread.sleep(millis, nanos);
            return null;
        } catch (Exception ex) {
            return ex;
        }
    }
    
    public static final long DEFAULT_SLEEP_CONTROL_PERIOD = 250;
    
    public static final Throwable silentSleepUntil(long millis, ToughSupplier<Boolean> stopSleep) {
        return silentSleepUntil(millis, stopSleep, DEFAULT_SLEEP_CONTROL_PERIOD);
    }
    
    public static final Throwable silentSleepUntil(long millis, ToughSupplier<Boolean> stopSleep, long controlPeriod) {
        return silentSleepWhile(millis, () -> !stopSleep.get(), controlPeriod);
    }
    
    public static final Throwable silentSleepWhile(long millis, ToughSupplier<Boolean> continueSleep) {
        return silentSleepWhile(millis, continueSleep, DEFAULT_SLEEP_CONTROL_PERIOD);
    }
    
    public static final Throwable silentSleepWhile(long millis, ToughSupplier<Boolean> continueSleep, long controlPeriod) {
        if (continueSleep == null) {
            return silentSleep(millis);
        }
        try {
            while (continueSleep.get()) {
                Thread.sleep(controlPeriod);
            }
            return null;
        } catch (Exception ex) {
            return ex;
        }
    }
    
    public static final Throwable silentSleepUntil(long millis, ToughPredicate<Long> stopSleep) {
        return silentSleepUntil(millis, stopSleep, DEFAULT_SLEEP_CONTROL_PERIOD);
    }
    
    public static final Throwable silentSleepUntil(long millis, ToughPredicate<Long> stopSleep, long controlPeriod) {
        return silentSleepWhile(millis, stopSleep.negate(), controlPeriod);
    }
    
    public static final Throwable silentSleepWhile(long millis, ToughPredicate<Long> continueSleep) {
        return silentSleepWhile(millis, continueSleep, DEFAULT_SLEEP_CONTROL_PERIOD);
    }
    
    public static final Throwable silentSleepWhile(long millis, ToughPredicate<Long> continueSleep, long controlPeriod) {
        if (continueSleep == null) {
            return silentSleep(millis);
        }
        try {
            final long start = System.currentTimeMillis();
            while (continueSleep.test(System.currentTimeMillis() - start)) {
                Thread.sleep(controlPeriod);
            }
            return null;
        } catch (Exception ex) {
            return ex;
        }
    }
    
    public static Thread addShutdownHookAsSingleThread(ToughRunnable toughRunnable) {
        Objects.requireNonNull(toughRunnable);
        final Thread thread = new Thread(toughRunnable::runWithoutException);
        Runtime.getRuntime().addShutdownHook(thread);
        return thread;
    }
    
    public static boolean removeShutdownHook(Thread thread) {
        return Runtime.getRuntime().removeShutdownHook(thread);
    }
    
    public static int addShutdownHook(ToughRunnable toughRunnable) {
        Objects.requireNonNull(toughRunnable, "toughRunnable");
        int id = Integer.MIN_VALUE;
        while (SHUTDOWN_HOOKS.containsKey(id)) {
            id++;
        }
        SHUTDOWN_HOOKS.put(id, toughRunnable);
        return id;
    }
    
    public static ToughRunnable removeShutdownHook(int id) {
        return SHUTDOWN_HOOKS.remove(id);
    }
    
    public static <R, T> R useWhenNotNull(T t, ToughFunction<T, R> toughFunction) {
        if (t == null || toughFunction == null) {
            return null;
        }
        return toughFunction.applyWithoutException(t);
    }
    
    public static <T> void useWhenNotNull(T t, ToughConsumer<T> toughConsumer) {
        if (t == null || toughConsumer == null) {
            return;
        }
        toughConsumer.acceptWithoutException(t);
    }
    
    public static Console getConsoleByName(String name) {
        if (!CONSOLES.containsKey(name)) {
            CONSOLES.put(name, new DefaultConsole());
        }
        return CONSOLES.get(name);
    }
    
    public static Thread getThread(long id) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getId() == id) {
                return thread;
            }
        }
        return null;
    }
    
    public static Thread getThread(String name) {
        if (name == null) {
            return null;
        }
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (Objects.equals(name, thread.getName())) {
                return thread;
            }
        }
        return null;
    }
    
}
