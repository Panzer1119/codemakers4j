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

import de.codemakers.base.util.tough.ToughRunnable;

public class TimerJob extends AbstractTimerJob {
    
    protected final ToughRunnable toughRunnable;
    
    public TimerJob(ToughRunnable toughRunnable) {
        super();
        this.toughRunnable = toughRunnable;
    }
    
    public TimerJob(long created, ToughRunnable toughRunnable) {
        super(created);
        this.toughRunnable = toughRunnable;
    }
    
    @Override
    public void run() throws Exception {
        toughRunnable.run();
    }
    
    public ToughRunnable getToughRunnable() {
        return toughRunnable;
    }
    
    @Override
    public String toString() {
        return "TimerJob{" + "toughRunnable=" + toughRunnable + ", lock=" + lock + ", created=" + created + ", state=" + state + ", nextExecutionTime=" + nextExecutionTime + ", period=" + period + '}';
    }
    
}
