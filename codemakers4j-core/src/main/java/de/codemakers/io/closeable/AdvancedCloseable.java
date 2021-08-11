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

package de.codemakers.io.closeable;

import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.exceptions.CJPRuntimeException;
import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.base.util.tough.ToughFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdvancedCloseable<T extends Closeable, D> implements Closeable {
    
    private static final Logger logger = LogManager.getLogger();
    
    private final T closeable;
    private final D data;
    private final AtomicBoolean closed = new AtomicBoolean(false);
    
    public AdvancedCloseable(T closeable, D data) {
        this.closeable = closeable;
        this.data = data;
    }
    
    public final T getCloseable() {
        return closeable;
    }
    
    public final D getData() {
        return data;
    }
    
    public final boolean isClosed() {
        return closed.get();
    }
    
    public void preClose(T closeable, D data) throws IOException {
    }
    
    public void postClose(T closeable, D data) {
    }
    
    @Override
    public final void close() throws IOException {
        if (isClosed()) {
            throw new CJPRuntimeException(getClass().getSimpleName() + " already closed");
        }
        if (closeable != null) {
            preClose(closeable, data);
            closeable.close();
            postClose(closeable, data);
        }
        closed.set(true);
    }
    
    public final void close(ToughConsumer<Throwable> failure) {
        try {
            close();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
        }
    }
    
    public final void closeWithoutException() {
        close((ToughConsumer<Throwable>) null);
    }
    
    public <R> R close(ToughFunction<D, R> function) throws Exception {
        return close(function, null);
    }
    
    public <R> R close(ToughFunction<D, R> function, ToughConsumer<Throwable> failureClosing) throws Exception {
        R r = null;
        if (function != null) {
            r = function.apply(data);
        }
        close(failureClosing);
        return r;
    }
    
    public <R> R close(ToughFunction<D, R> function, ToughConsumer<Throwable> failureFunction, ToughConsumer<Throwable> failureClosing) {
        try {
            return close(function, failureClosing);
        } catch (Exception ex) {
            if (failureFunction != null) {
                failureFunction.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            close(failureClosing);
            return null;
        }
    }
    
    public <R> R closeWithoutException(ToughFunction<D, R> function, ToughConsumer<Throwable> failureFunction) {
        return close(function, failureFunction, null);
    }
    
    public <R> R closeWithoutException(ToughFunction<D, R> function) {
        return closeWithoutException(function, null);
    }
    
    public <R> ReturningAction<R> closeAction(ToughFunction<D, R> function, ToughConsumer<Throwable> failureFunction) {
        return new ReturningAction<>(() -> close(function, failureFunction));
    }
    
    public <R> ReturningAction<R> closeAction(ToughFunction<D, R> function) {
        return new ReturningAction<>(() -> close(function));
    }
    
}
