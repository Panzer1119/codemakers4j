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
import de.codemakers.base.util.tough.Tough;
import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.base.util.tough.ToughRunnable;
import de.codemakers.base.util.tough.ToughSupplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Inspired by the RestAction from Austin Keener &amp; Michael Ritter &amp; Florian Spieß
 *
 * @param <T> Type Input
 * @param <R> Type Output
 */
public abstract class Action<T extends Tough<?, ?>, R> {
    
    private static final Logger logger = LogManager.getLogger();
    
    protected final CJP cjp;
    
    Action() {
        this(CJP.getInstance());
    }
    
    Action(CJP cjp) {
        Objects.requireNonNull(cjp, "CJP may not be null!");
        this.cjp = cjp;
    }
    
    /**
     * Creates an {@link de.codemakers.base.action.ReturningAction} of an {@link java.util.function.Supplier}
     *
     * @param supplier {@link java.util.function.Supplier}
     * @param <T> The Type of the {@link de.codemakers.base.action.ReturningAction}
     *
     * @return The created {@link de.codemakers.base.action.ReturningAction}
     */
    public static final <T> ReturningAction<T> ofSupplier(Supplier<T> supplier) {
        return ofToughSupplier(supplier::get);
    }
    
    /**
     * Creates an {@link de.codemakers.base.action.ReturningAction} of an {@link de.codemakers.base.util.tough.ToughSupplier}
     *
     * @param supplier {@link de.codemakers.base.util.tough.ToughSupplier}
     * @param <T> The Type of the {@link de.codemakers.base.action.ReturningAction}
     *
     * @return The created {@link de.codemakers.base.action.ReturningAction}
     */
    public static final <T> ReturningAction<T> ofToughSupplier(ToughSupplier<T> supplier) {
        return new ReturningAction<>(supplier);
    }
    
    /**
     * Creates an {@link de.codemakers.base.action.RunningAction} of an {@link java.lang.Runnable}
     *
     * @param runnable {@link java.lang.Runnable}
     *
     * @return The created {@link de.codemakers.base.action.RunningAction}
     */
    public static final RunningAction ofRunnable(Runnable runnable) {
        return ofToughRunnable(runnable::run);
    }
    
    /**
     * Creates an {@link de.codemakers.base.action.RunningAction} of an {@link de.codemakers.base.util.tough.ToughRunnable}
     *
     * @param runnable {@link de.codemakers.base.util.tough.ToughRunnable}
     *
     * @return The created {@link de.codemakers.base.action.RunningAction}
     */
    public static final RunningAction ofToughRunnable(ToughRunnable runnable) {
        return new RunningAction(runnable);
    }
    
    /**
     * Returns the CJP used in this Action
     *
     * @return Never-null CJP Object
     */
    public final CJP getCJP() {
        return cjp;
    }
    
    /**
     * Submits a Task for execution.
     *
     * <p><b>This method is asynchronous</b>
     */
    public void queue() {
        queue(null, null);
    }
    
    /**
     * Submits a Task for execution.
     *
     * <p><b>This method is asynchronous</b>
     *
     * @param success The success callback that will be called at a convenient time for the API. (can be null)
     */
    public void queue(T success) {
        queue(success, null);
    }
    
    /**
     * Submits a Task for execution.
     *
     * <p><b>This method is asynchronous</b>
     *
     * @param success The success callback that will be called at a convenient time for the API. (can be null)
     * @param failure The failure callback that will be called if the Request encounters an exception at its execution point.
     */
    public abstract void queue(T success, ToughConsumer<Throwable> failure);
    
    /**
     * Submits a Task for execution.
     *
     * <p><b>This method is asynchronous</b>
     *
     * <br>The execution is done with an {@link java.util.concurrent.ExecutorService single threaded ExecutorService}, so it is guaranteed, that the order of tasks submitted is correct when executed
     */
    public void queueSingle() {
        queueSingle(null, null);
    }
    
    /**
     * Submits a Task for execution.
     *
     * <p><b>This method is asynchronous</b>
     *
     * <br>The execution is done with an {@link java.util.concurrent.ExecutorService single threaded ExecutorService}, so it is guaranteed, that the order of tasks submitted is correct when executed
     *
     * @param success The success callback that will be called at a convenient time for the API. (can be null)
     */
    public void queueSingle(T success) {
        queueSingle(success, null);
    }
    
    /**
     * Submits a Task for execution.
     *
     * <p><b>This method is asynchronous</b>
     *
     * <br>The execution is done with an {@link java.util.concurrent.ExecutorService single threaded ExecutorService}, so it is guaranteed, that the order of tasks submitted is correct when executed
     *
     * @param success The success callback that will be called at a convenient time for the API. (can be null)
     * @param failure The failure callback that will be called if the Request encounters an exception at its execution point.
     */
    public abstract void queueSingle(T success, ToughConsumer<Throwable> failure);
    
    /**
     * Schedules a call to {@link #queue()} to be executed after the specified {@code delay}.
     * <br>This is an <b>asynchronous</b> operation that will return a
     * {@link java.util.concurrent.ScheduledFuture ScheduledFuture} representing the task.
     *
     * <p>This operation gives no access to the response value.
     * <br>Use {@link #queueAfter(long, java.util.concurrent.TimeUnit, T)} to access
     * the success consumer for {@link #queue(T)}!
     *
     * <p>The local CJP {@link java.util.concurrent.ScheduledExecutorService ScheduledExecutorService} is used for this operation.
     *
     * @param delay The delay after which this computation should be executed, negative to execute immediately
     * @param unit The {@link java.util.concurrent.TimeUnit TimeUnit} to convert the specified {@code delay}
     *
     * @return {@link java.util.concurrent.ScheduledFuture ScheduledFuture}
     * representing the delayed operation
     *
     * @throws java.lang.IllegalArgumentException If the provided TimeUnit is {@code null}
     */
    public ScheduledFuture<?> queueAfter(long delay, TimeUnit unit) {
        return queueAfter(delay, unit, null, null);
    }
    
    /**
     * Schedules a call to {@link #queue()} to be executed after the specified {@code delay}.
     * <br>This is an <b>asynchronous</b> operation that will return a
     * {@link java.util.concurrent.ScheduledFuture ScheduledFuture} representing the task.
     *
     * <p>This operation gives no access to the response value.
     * <br>Use {@link #queueAfter(long, java.util.concurrent.TimeUnit, T)} to access
     * the success consumer for {@link #queue(T)}!
     *
     * <p>The local CJP {@link java.util.concurrent.ScheduledExecutorService ScheduledExecutorService} is used for this operation.
     *
     * @param delay The delay after which this computation should be executed, negative to execute immediately
     * @param unit The {@link java.util.concurrent.TimeUnit TimeUnit} to convert the specified {@code delay}
     * @param success The success callback that will be called at a convenient time for the API. (can be null)
     *
     * @return {@link java.util.concurrent.ScheduledFuture ScheduledFuture}
     * representing the delayed operation
     *
     * @throws java.lang.IllegalArgumentException If the provided TimeUnit is {@code null}
     */
    public ScheduledFuture<?> queueAfter(long delay, TimeUnit unit, T success) {
        return queueAfter(delay, unit, success, null);
    }
    
    /**
     * Schedules a call to {@link #queue()} to be executed after the specified {@code delay}.
     * <br>This is an <b>asynchronous</b> operation that will return a
     * {@link java.util.concurrent.ScheduledFuture ScheduledFuture} representing the task.
     *
     * <p>This operation gives no access to the response value.
     * <br>Use {@link #queueAfter(long, java.util.concurrent.TimeUnit, T)} to access
     * the success consumer for {@link #queue(T)}!
     *
     * <p>The local CJP {@link java.util.concurrent.ScheduledExecutorService ScheduledExecutorService} is used for this operation.
     *
     * @param delay The delay after which this computation should be executed, negative to execute immediately
     * @param unit The {@link java.util.concurrent.TimeUnit TimeUnit} to convert the specified {@code delay}
     * @param success The success callback that will be called at a convenient time for the API. (can be null)
     * @param failure The failure callback that will be called if the Request encounters an exception at its execution point.
     *
     * @return {@link java.util.concurrent.ScheduledFuture ScheduledFuture}
     * representing the delayed operation
     *
     * @throws java.lang.IllegalArgumentException If the provided TimeUnit is {@code null}
     */
    public ScheduledFuture<?> queueAfter(long delay, TimeUnit unit, T success, ToughConsumer<Throwable> failure) {
        return cjp.getScheduledExecutorService().schedule(() -> queue(success, failure), delay, unit);
    }
    
    /**
     * Schedules a call to {@link #queue()} to be executed after the specified {@code delay}.
     * <br>This is an <b>asynchronous</b> operation that will return a
     * {@link java.util.concurrent.ScheduledFuture ScheduledFuture} representing the task.
     *
     * <p>This operation gives no access to the response value.
     * <br>Use {@link #queueAfter(long, java.util.concurrent.TimeUnit, T)} to access
     * the success consumer for {@link #queue(T)}!
     *
     * <br>The execution is done with an {@link java.util.concurrent.ExecutorService single threaded ExecutorService}, so it is guaranteed, that the order of tasks submitted is correct when executed
     *
     * <p>The local CJP {@link java.util.concurrent.ScheduledExecutorService ScheduledExecutorService} is used for this operation.
     *
     * @param delay The delay after which this computation should be executed, negative to execute immediately
     * @param unit The {@link java.util.concurrent.TimeUnit TimeUnit} to convert the specified {@code delay}
     *
     * @throws java.lang.IllegalArgumentException If the provided TimeUnit is {@code null}
     */
    public void queueSingleAfter(long delay, TimeUnit unit) {
        queueSingleAfter(delay, unit, null, null);
    }
    
    /**
     * Schedules a call to {@link #queue()} to be executed after the specified {@code delay}.
     * <br>This is an <b>asynchronous</b> operation that will return a
     * {@link java.util.concurrent.ScheduledFuture ScheduledFuture} representing the task.
     *
     * <p>This operation gives no access to the response value.
     * <br>Use {@link #queueAfter(long, java.util.concurrent.TimeUnit, T)} to access
     * the success consumer for {@link #queue(T)}!
     *
     * <br>The execution is done with an {@link java.util.concurrent.ExecutorService single threaded ExecutorService}, so it is guaranteed, that the order of tasks submitted is correct when executed
     *
     * <p>The local CJP {@link java.util.concurrent.ScheduledExecutorService ScheduledExecutorService} is used for this operation.
     *
     * @param delay The delay after which this computation should be executed, negative to execute immediately
     * @param unit The {@link java.util.concurrent.TimeUnit TimeUnit} to convert the specified {@code delay}
     * @param success The success callback that will be called at a convenient time for the API. (can be null)
     *
     * @throws java.lang.IllegalArgumentException If the provided TimeUnit is {@code null}
     */
    public void queueSingleAfter(long delay, TimeUnit unit, T success) {
        queueSingleAfter(delay, unit, success, null);
    }
    
    /**
     * Schedules a call to {@link #queue()} to be executed after the specified {@code delay}.
     * <br>This is an <b>asynchronous</b> operation that will return a
     * {@link java.util.concurrent.ScheduledFuture ScheduledFuture} representing the task.
     *
     * <p>This operation gives no access to the response value.
     * <br>Use {@link #queueAfter(long, java.util.concurrent.TimeUnit, T)} to access
     * the success consumer for {@link #queue(T)}!
     *
     * <br>The execution is done with an {@link java.util.concurrent.ExecutorService single threaded ExecutorService}, so it is guaranteed, that the order of tasks submitted is correct when executed
     *
     * <p>The local CJP {@link java.util.concurrent.ScheduledExecutorService ScheduledExecutorService} is used for this operation.
     *
     * @param delay The delay after which this computation should be executed, negative to execute immediately
     * @param unit The {@link java.util.concurrent.TimeUnit TimeUnit} to convert the specified {@code delay}
     * @param success The success callback that will be called at a convenient time for the API. (can be null)
     * @param failure The failure callback that will be called if the Request encounters an exception at its execution point.
     *
     * @throws java.lang.IllegalArgumentException If the provided TimeUnit is {@code null}
     */
    public void queueSingleAfter(long delay, TimeUnit unit, T success, ToughConsumer<Throwable> failure) {
        cjp.getScheduledExecutorService().schedule(() -> queueSingle(success, failure), delay, unit);
    }
    
    /**
     * Submits a Task for execution and provides a {@link java.util.concurrent.Future Future}
     * representing its completion task.
     * <br>Cancelling the returned Future will result in the cancellation of the Task!
     *
     * @return Never-null {@link java.util.concurrent.Future Future} representing the completion promise
     */
    public abstract Future<R> submit();
    
    /**
     * Submits a Task for execution and provides a {@link java.util.concurrent.Future Future}
     * representing its completion task.
     * <br>Cancelling the returned Future will result in the cancellation of the Task!
     *
     * <br>The execution is done with an {@link java.util.concurrent.ExecutorService single threaded ExecutorService}, so it is guaranteed, that the order of tasks submitted is correct when executed
     *
     * @return Never-null {@link java.util.concurrent.Future Future} representing the completion promise
     */
    public abstract Future<R> submitSingle();
    
    /**
     * Schedules a call to {@link #complete()} to be executed after the specified {@code delay}.
     * <br>This is an <b>asynchronous</b> operation that will return a
     * {@link java.util.concurrent.ScheduledFuture ScheduledFuture} representing the task.
     *
     * <p>The returned Future will provide the return type of a {@link #complete()} operation when
     * received through the <b>blocking</b> call to {@link java.util.concurrent.Future#get()}!
     *
     * <p>The local CJP {@link java.util.concurrent.ScheduledExecutorService ScheduledExecutorService}
     * is used for this operation.
     *
     * @param delay The delay after which this computation should be executed, negative to execute immediately
     * @param unit The {@link java.util.concurrent.TimeUnit TimeUnit} to convert the specified {@code delay}
     *
     * @return {@link java.util.concurrent.ScheduledFuture ScheduledFuture} representing the
     * delayed operation
     *
     * @throws java.lang.IllegalArgumentException If the provided TimeUnit is {@code null}
     */
    public ScheduledFuture<R> submitAfter(long delay, TimeUnit unit) {
        return submitAfter(delay, unit, null);
    }
    
    /**
     * Schedules a call to {@link #complete()} to be executed after the specified {@code delay}.
     * <br>This is an <b>asynchronous</b> operation that will return a
     * {@link java.util.concurrent.ScheduledFuture ScheduledFuture} representing the task.
     *
     * <p>The returned Future will provide the return type of a {@link #complete()} operation when
     * received through the <b>blocking</b> call to {@link java.util.concurrent.Future#get()}!
     *
     * <p>The local CJP {@link java.util.concurrent.ScheduledExecutorService ScheduledExecutorService}
     * is used for this operation.
     *
     * @param delay The delay after which this computation should be executed, negative to execute immediately
     * @param unit The {@link java.util.concurrent.TimeUnit TimeUnit} to convert the specified {@code delay}
     * @param failure The failure callback that will be called if the Request encounters an exception at its execution point.
     *
     * @return {@link java.util.concurrent.ScheduledFuture ScheduledFuture} representing the
     * delayed operation
     *
     * @throws java.lang.IllegalArgumentException If the provided TimeUnit is {@code null}
     */
    public ScheduledFuture<R> submitAfter(long delay, TimeUnit unit, ToughConsumer<Throwable> failure) {
        return cjp.getScheduledExecutorService().schedule(() -> complete(failure), delay, unit);
    }
    
    /**
     * Blocks the current Thread and awaits the completion
     * of an {@link #submit()} request.
     * <br>Used for synchronous logic.
     *
     * @return The response value
     */
    public R complete() {
        return complete(null);
    }
    
    /**
     * Blocks the current Thread and awaits the completion
     * of an {@link #submit()} request.
     * <br>Used for synchronous logic.
     *
     * @param failure The failure callback that will be called if the Request encounters an exception at its execution point.
     *
     * @return The response value
     */
    public R complete(ToughConsumer<Throwable> failure) {
        try {
            return submit().get();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
                return null;
            } else {
                throw new RuntimeException(ex);
            }
        }
    }
    
    /**
     * Blocks the current Thread for the specified delay and calls {@link #complete()}
     * when delay has been reached.
     * <br>If the specified delay is negative this action will execute immediately. (see: {@link TimeUnit#sleep(long)})
     *
     * @param delay The delay after which to execute a call to {@link #complete()}
     * @param unit The {@link java.util.concurrent.TimeUnit TimeUnit} which should be used
     * (this will use {@link java.util.concurrent.TimeUnit#sleep(long) unit.sleep(delay)})
     * @param failure The failure callback that will be called if the Request encounters an exception at its execution point.
     *
     * @return The response value
     *
     * @throws java.lang.RuntimeException If the sleep operation is interrupted
     */
    public R completeAfter(long delay, TimeUnit unit, ToughConsumer<Throwable> failure) {
        try {
            if (unit != null) {
                unit.sleep(delay);
            }
            return complete();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
                return null;
            } else {
                throw new RuntimeException(ex);
            }
        }
    }
    
    /**
     * Consumes directly the {@link R}
     *
     * @param consumer Consumer that consumes the {@link R}
     */
    public abstract void consume(ToughConsumer<R> consumer) throws Exception;
    
    /**
     * Consumes directly the {@link R}
     *
     * @param consumer Consumer that consumes the {@link R}
     * @param failure The failure callback that will be called if the Request encounters an exception at its execution point
     */
    public void consume(ToughConsumer<R> consumer, ToughConsumer<Throwable> failure) {
        try {
            consume(consumer);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
        }
    }
    
    /**
     * Consumes directly the {@link R}, but without throwing an {@link java.lang.Exception}
     *
     * @param consumer Consumer that consumes the {@link R}
     */
    public void consumeWithoutException(ToughConsumer<R> consumer) {
        consume(consumer, null);
    }
    
}
