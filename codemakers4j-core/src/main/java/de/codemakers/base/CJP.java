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

import de.codemakers.base.action.Action;
import de.codemakers.base.action.RunningAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CJP {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final String PROJECT_NAME = "codemakers4j";
    public static final String VERSION = "0.16.14";
    
    private static final CJP CJP = createInstance();
    
    static {
        Standard.addShutdownHook(de.codemakers.base.CJP::shutdownInstance);
    }
    
    private ExecutorService fixedExecutorService;
    private ScheduledExecutorService scheduledExecutorService;
    private ExecutorService singleExecutorService;
    
    public CJP(ExecutorService fixedExecutorService, ScheduledExecutorService scheduledExecutorService) {
        this.fixedExecutorService = fixedExecutorService;
        this.scheduledExecutorService = scheduledExecutorService;
        this.singleExecutorService = Executors.newSingleThreadExecutor();
    }
    
    public CJP(int fixedThreadPoolSize) {
        this(Executors.newScheduledThreadPool(fixedThreadPoolSize), Executors.newSingleThreadScheduledExecutor());
    }
    
    public static final void shutdownInstance() {
        CJP.stopExecutorServiceNow();
    }
    
    public static final RunningAction shutdown() {
        return shutdown(0);
    }
    
    public static final RunningAction shutdown(int status) {
        return Action.ofToughRunnable(() -> System.exit(status));
    }
    
    public static final CJP getInstance() {
        return CJP;
    }
    
    public static final CJP createInstance(int fixedThreadPoolSize) {
        return new CJP(fixedThreadPoolSize);
    }
    
    public static final CJP createInstance() {
        return createInstance(Runtime.getRuntime().availableProcessors());
    }
    
    public final ExecutorService getFixedExecutorService() {
        return fixedExecutorService;
    }
    
    public final CJP setFixedExecutorService(ExecutorService fixedExecutorService) {
        this.fixedExecutorService = fixedExecutorService;
        return this;
    }
    
    public final ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }
    
    public final CJP setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
        return this;
    }
    
    public final ExecutorService getSingleExecutorService() {
        return singleExecutorService;
    }
    
    public final CJP setSingleExecutorService(ExecutorService singleExecutorService) {
        this.singleExecutorService = singleExecutorService;
        return this;
    }
    
    public final CJP stopExecutorServiceNow() {
        if (fixedExecutorService != null) {
            fixedExecutorService.shutdownNow();
            fixedExecutorService = null;
        }
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
            scheduledExecutorService = null;
        }
        if (singleExecutorService != null) {
            singleExecutorService.shutdownNow();
            singleExecutorService = null;
        }
        return this;
    }
    
    public final CJP stopExecutors(long timeout, TimeUnit unit) {
        if (fixedExecutorService == null && scheduledExecutorService == null && singleExecutorService == null) {
            return this;
        }
        timeout = unit.toMillis(timeout);
        unit = TimeUnit.MILLISECONDS;
        timeout = shutdownExecutor(timeout, unit, fixedExecutorService);
        timeout = shutdownExecutor(timeout, unit, scheduledExecutorService);
        shutdownExecutor(timeout, unit, singleExecutorService);
        return stopExecutorServiceNow();
    }
    
    private final long shutdownExecutor(long timeout, TimeUnit unit, ExecutorService executorService) {
        final long timestamp = System.currentTimeMillis();
        if (executorService != null) {
            try {
                executorService.shutdown();
                executorService.awaitTermination(timeout, unit);
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        return Math.max(0, timeout - (System.currentTimeMillis() - timestamp));
    }
    
}
