/*
 *     Copyright 2018 - 2019 Paul Hagedorn (Panzer1119)
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

package de.codemakers.base.util.timer;

import de.codemakers.base.util.tough.ToughRunnable;

public interface ITimerJob extends Comparable<ITimerJob>, ToughRunnable {
    
    Object getLock();
    
    boolean cancel();
    
    long getCreated();
    
    TimerJobState getState();
    
    AbstractTimerJob setState(TimerJobState state);
    
    long getNextExecutionTime();
    
    AbstractTimerJob setNextExecutionTime(long nextExecutionTime);
    
    long getPeriod();
    
    AbstractTimerJob setPeriod(long period);
    
    @Override
    default int compareTo(ITimerJob timerJob) {
        return Long.compare(getNextExecutionTime(), timerJob.getNextExecutionTime());
    }
    
}