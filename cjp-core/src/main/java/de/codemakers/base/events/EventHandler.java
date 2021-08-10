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

package de.codemakers.base.events;

import de.codemakers.base.CJP;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventHandler<E> implements IEventHandler<E> {
    
    private final List<EventListener<E>> eventListeners = new CopyOnWriteArrayList<>();
    private ExecutorService executorService;
    private boolean consumeEvents = true;
    private boolean forceEvents = false;
    
    public EventHandler() {
        this(CJP.getInstance().getSingleExecutorService());
    }
    
    public EventHandler(ExecutorService executorService) {
        this.executorService = executorService;
    }
    
    public final ExecutorService getExecutorService() {
        return executorService;
    }
    
    public final EventHandler<E> setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }
    
    @Override
    public final EventHandler<E> addEventListener(EventListener<E> eventListener) {
        eventListeners.add(eventListener);
        return this;
    }
    
    @Override
    public final EventHandler<E> removeEventListener(EventListener<E> eventListener) {
        eventListeners.remove(eventListener);
        return this;
    }
    
    @Override
    public final EventHandler<E> clearEventListeners() {
        eventListeners.clear();
        return this;
    }
    
    @Override
    public boolean onEvent(final E event) {
        if (event == null) {
            return false;
        }
        final AtomicBoolean eventConsumed = new AtomicBoolean(false);
        final Runnable runnable = () -> eventConsumed.set(eventListeners.parallelStream().anyMatch((eventListener) -> {
            if (forceEvents) {
                eventListener.onEventWithoutException(event);
                return false;
            }
            return eventListener.onEventWithoutException(event);
        }));
        if (executorService != null) {
            executorService.submit(runnable);
        } else {
            runnable.run();
        }
        return consumeEvents || eventConsumed.get();
    }
    
    public final boolean isConsumeEvents() {
        return consumeEvents;
    }
    
    public final EventHandler<E> setConsumeEvents(boolean consumeEvents) {
        this.consumeEvents = consumeEvents;
        return this;
    }
    
    public final boolean isForceEvents() {
        return forceEvents;
    }
    
    public final EventHandler<E> setForceEvents(boolean forceEvents) {
        this.forceEvents = forceEvents;
        return this;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final EventHandler<?> that = (EventHandler<?>) other;
        return consumeEvents == that.consumeEvents && forceEvents == that.forceEvents && Objects.equals(eventListeners, that.eventListeners) && Objects
                .equals(executorService, that.executorService);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(eventListeners, executorService, consumeEvents, forceEvents);
    }
    
    @Override
    public String toString() {
        return "EventHandler{" + "eventListeners=" + eventListeners + ", executorService=" + executorService + ", consumeEvents=" + consumeEvents + ", forceEvents=" + forceEvents + '}';
    }
    
}
