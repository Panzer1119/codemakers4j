/*
 *     Copyright 2018 Paul Hagedorn (Panzer1119)
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

package de.codemakers.base.util;

import de.codemakers.base.util.tough.ToughSupplier;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Waiter {
    
    public static final long STANDARD_SLEEP_TIME_MILLIS = 10;
    
    private ToughSupplier<Boolean> done;
    private long started;
    private long timeoutMillis;
    private long sleepTimeMillis = STANDARD_SLEEP_TIME_MILLIS;
    
    public Waiter(ToughSupplier<Boolean> done) {
        this(-1, null, done);
    }
    
    public Waiter(long timeout, TimeUnit unit, ToughSupplier<Boolean> done) {
        this(System.currentTimeMillis(), timeout, unit, done);
    }
    
    public Waiter(long started, long timeout, TimeUnit unit, ToughSupplier<Boolean> done) {
        setStarted(started);
        if (timeout < 0 || unit == null) {
            setTimeoutMillis(-1);
        } else {
            setTimeoutMillis(unit.toMillis(timeout));
        }
        setDoneSupplier(done);
    }
    
    public final ToughSupplier<Boolean> getDoneSupplier() {
        return done;
    }
    
    public final Waiter setDoneSupplier(ToughSupplier<Boolean> done) {
        Objects.requireNonNull(done);
        this.done = done;
        return this;
    }
    
    public final boolean isDone() {
        return done.getWithoutException();
    }
    
    public final long getStarted() {
        return started;
    }
    
    public final Waiter setStarted(long started) {
        this.started = started;
        return this;
    }
    
    public final long getTimeoutMillis() {
        return timeoutMillis;
    }
    
    public final Waiter setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
        return this;
    }
    
    public final long getSleepTimeMillis() {
        return sleepTimeMillis;
    }
    
    public final Waiter setSleepTimeMillis(long sleepTimeMillis) {
        this.sleepTimeMillis = sleepTimeMillis;
        return this;
    }
    
    protected final void waitStep() {
        try {
            Thread.sleep(sleepTimeMillis);
        } catch (Exception ex) {
        }
    }
    
    public final boolean waitFor() {
        try {
            while (!done.getWithoutException()) {
                if (timeoutMillis >= 0 && (System.currentTimeMillis() - started) >= timeoutMillis) {
                    return false;
                }
                waitStep();
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    
}
