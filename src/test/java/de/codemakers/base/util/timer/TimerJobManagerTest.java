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

import de.codemakers.base.Standard;
import de.codemakers.base.logger.LogLevel;
import de.codemakers.base.logger.Logger;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class TimerJobManagerTest {
    
    public static void main(String[] args) {
        Logger.getDefaultAdvancedLeveledLogger().setMinimumLogLevel(LogLevel.FINE);
        final AtomicLong temp_1 = new AtomicLong(-1);
        final TimerJobManager timerJobManager = new TimerJobManager();
        Logger.log("timerJobManager=" + timerJobManager);
        timerJobManager.scheduleAtFixedRate(new TimerJob(() -> {
            final long timestamp = System.currentTimeMillis();
            if (temp_1.get() == -1) {
                temp_1.set(timestamp);
            }
            System.out.println("CURRENT TIME: " + ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
        }), 1000);
        Logger.log("timerJobManager=" + timerJobManager);
        timerJobManager.startWithoutException();
        Standard.addShutdownHook(timerJobManager::stop);
        Logger.log("timerJobManager=" + timerJobManager);
        Standard.async(() -> {
            Thread.sleep(3750);
            timerJobManager.scheduleAtFixedRate(new TimerJob(() -> {
                System.err.println("SECOND TIME: " + ZonedDateTime.now());
            }), 1500);
            Thread.sleep(1250);
            timerJobManager.schedule(new TimerJob(() -> {
                final long offset = 3750 + 1250 + 1000;
                final long timestamp = System.currentTimeMillis();
                final long duration = timestamp - temp_1.get() - offset;
                System.out.println(String.format("This should be (%s), and is (%s), the difference is %d ms", ZonedDateTime.ofInstant(Instant.ofEpochMilli(temp_1.get() + offset), ZoneId.systemDefault()), ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()), duration));
            }), 1000);
            timerJobManager.schedule(() -> System.out.println("HEHE"));
        });
        Standard.async(() -> {
            Thread.sleep(20000);
            System.exit(0);
        });
    }
    
}
