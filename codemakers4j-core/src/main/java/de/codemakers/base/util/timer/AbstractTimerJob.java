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

package de.codemakers.base.util.timer;

public abstract class AbstractTimerJob implements ITimerJob {
    
    final Object lock = new Object();
    
    protected final long created;
    protected TimerJobState state = TimerJobState.CREATED;
    /**
     * The next time in millis this job gets executed
     */
    protected long nextExecutionTime = -1;
    /**
     * Period in millis for repeating tasks
     * <br>
     * + = Fixed-rate execution
     * <br>
     * 0 = Non-repeating job
     * <br>
     * - = Fixed-delay execution
     */
    protected long period = 0;
    
    public AbstractTimerJob() {
        this(System.currentTimeMillis());
    }
    
    public AbstractTimerJob(long created) {
        this.created = created;
    }
    
    @Override
    public Object getLock() {
        return lock;
    }
    
    @Override
    public boolean cancel() {
        synchronized (lock) {
            boolean result = (state == TimerJobState.SCHEDULED);
            state = TimerJobState.CANCELLED;
            return result;
        }
    }
    
    @Override
    public long getCreated() {
        return created;
    }
    
    @Override
    public TimerJobState getState() {
        return state;
    }
    
    @Override
    public AbstractTimerJob setState(TimerJobState state) {
        this.state = state;
        return this;
    }
    
    @Override
    public long getNextExecutionTime() {
        return nextExecutionTime;
    }
    
    @Override
    public AbstractTimerJob setNextExecutionTime(long nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
        return this;
    }
    
    @Override
    public long getPeriod() {
        return period;
    }
    
    @Override
    public AbstractTimerJob setPeriod(long period) {
        this.period = period;
        return this;
    }
    
    @Override
    public String toString() {
        return "AbstractTimerJob{" + "lock=" + lock + ", created=" + created + ", state=" + state + ", nextExecutionTime=" + nextExecutionTime + ", period=" + period + '}';
    }
    
}
