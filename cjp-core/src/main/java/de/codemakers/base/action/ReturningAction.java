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

package de.codemakers.base.action;

import de.codemakers.base.CJP;
import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.base.util.tough.ToughSupplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.Future;

/**
 * Inspired by the RestAction from Austin Keener &amp; Michael Ritter &amp; Florian Spieß
 *
 * @param <T> Type input
 */
public class ReturningAction<T> extends Action<ToughConsumer<T>, T> {
    
    private static final Logger logger = LogManager.getLogger();
    
    protected final ToughSupplier<T> supplier;
    
    public ReturningAction(ToughSupplier<T> supplier) {
        super();
        Objects.requireNonNull(supplier, "supplier may not be null!");
        this.supplier = supplier;
    }
    
    public ReturningAction(CJP cjp, ToughSupplier<T> supplier) {
        super(cjp);
        Objects.requireNonNull(supplier, "supplier may not be null!");
        this.supplier = supplier;
    }
    
    /**
     * Returns the internal {@link de.codemakers.base.util.tough.ToughSupplier}
     *
     * @return {@link de.codemakers.base.util.tough.ToughSupplier}
     */
    public final ToughSupplier<T> getSupplier() {
        return supplier;
    }
    
    /**
     * Runs directly the {@link de.codemakers.base.util.tough.ToughRunnable}
     *
     * @param failure The failure callback that will be called if the Request encounters an exception at its execution point.
     *
     * @return {@link T}
     */
    public T direct(ToughConsumer<Throwable> failure) {
        return supplier.get(failure);
    }
    
    /**
     * Runs directly the {@link de.codemakers.base.util.tough.ToughRunnable}
     *
     * @return {@link T}
     */
    public T direct() {
        return supplier.getWithoutException();
    }
    
    @Override
    public void queue(ToughConsumer<T> success, ToughConsumer<Throwable> failure) {
        cjp.getFixedExecutorService().submit(() -> run(success, failure));
    }
    
    @Override
    public void queueSingle(ToughConsumer<T> success, ToughConsumer<Throwable> failure) {
        cjp.getSingleExecutorService().submit(() -> run(success, failure));
    }
    
    protected void run(ToughConsumer<T> success, ToughConsumer<Throwable> failure) {
        try {
            final T t = supplier.get();
            if (success != null) {
                success.acceptWithoutException(t);
            }
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                ex.printStackTrace();
            }
        }
    }
    
    @Override
    public Future<T> submit() {
        return cjp.getFixedExecutorService().submit(supplier::getWithoutException);
    }
    
    @Override
    public Future<T> submitSingle() {
        return cjp.getSingleExecutorService().submit(supplier::getWithoutException);
    }
    
    @Override
    public void consume(ToughConsumer<T> consumer) throws Exception {
        consumer.accept(direct());
    }
    
    /**
     * Consumes directly the {@link T} and returns an {@link R}
     *
     * @param function Function that uses the {@link R} and returns an {@link R}
     */
    public <R> R use(ToughFunction<T, R> function) throws Exception {
        return function.apply(direct());
    }
    
    /**
     * Consumes directly the {@link T} and returns an {@link R}
     *
     * @param function Function that uses the {@link R} and returns an {@link R}
     * @param failure The failure callback that will be called if the Request encounters an exception at its execution point
     */
    public <R> R use(ToughFunction<T, R> function, ToughConsumer<Throwable> failure) {
        try {
            return use(function);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    /**
     * Consumes directly the {@link T} and returns an {@link R}, but without throwing an {@link java.lang.Exception}
     *
     * @param function Function that uses the {@link R} and returns an {@link R}
     */
    public <R> R useWithoutException(ToughFunction<T, R> function) {
        return use(function, null);
    }
    
}
