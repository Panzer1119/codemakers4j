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

import java.util.concurrent.TimeUnit;

public class TimeTaker {
    
    private boolean started = false;
    private long start = 0;
    private long pause = 0;
    private long stop = 0;
    private long pauses = 0;
    
    public TimeTaker() {
    }
    
    public TimeTaker(long start) {
        this.start = start;
    }
    
    public long getStart() {
        return start;
    }
    
    public TimeTaker setStart(long start) {
        this.start = start;
        return this;
    }
    
    public long getPause() {
        return pause;
    }
    
    public TimeTaker setPause(long pause) {
        this.pause = pause;
        return this;
    }
    
    public long getStop() {
        return stop;
    }
    
    public TimeTaker setStop(long stop) {
        this.stop = stop;
        return this;
    }
    
    public long getPauses() {
        return pauses;
    }
    
    public TimeTaker addPause(long duration) {
        this.pauses += duration;
        return this;
    }
    
    public boolean reset() {
        started = false;
        start = 0;
        pause = 0;
        stop = 0;
        resetPauses();
        return true;
    }
    
    public boolean resetPauses() {
        pauses = 0;
        return true;
    }
    
    public long start() {
        if (started) {
            return start;
        }
        started = true;
        start = System.currentTimeMillis();
        return start;
    }
    
    public long pause() {
        if (!started) {
            return 0;
        }
        if (pause != 0) {
            return pause;
        }
        pause = System.currentTimeMillis();
        return pause;
    }
    
    public boolean unpause() {
        if (!started || pause == 0) {
            return false;
        }
        final long now = System.currentTimeMillis();
        pauses += (now - pause);
        pause = 0;
        return true;
    }
    
    public long stop() {
        if (!started) {
            return stop;
        }
        if (pause != 0) {
            unpause();
        }
        stop = System.currentTimeMillis();
        started = false;
        return stop;
    }
    
    public long getDuration() {
        final long now = System.currentTimeMillis();
        if (!started) {
            return (stop - start) - pauses;
        } else {
            if (pause != 0) {
                return (now - start) - pauses - (now - pause);
            } else {
                return (now - start) - pauses;
            }
        }
    }
    
    public long getDuration(TimeUnit unit) {
        return unit.convert(getDuration(), TimeUnit.MILLISECONDS);
    }
    
    @Override
    public String toString() {
        return "TimeTaker{" + "started=" + started + ", start=" + start + ", pause=" + pause + ", stop=" + stop + ", pauses=" + pauses + '}';
    }
    
}
