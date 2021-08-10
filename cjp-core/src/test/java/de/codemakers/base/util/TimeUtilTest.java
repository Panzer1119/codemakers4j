/*
 *     Copyright 2018 - 2020 Paul Hagedorn (Panzer1119)
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

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeUtilTest {
    
    public static final void main(String[] args) throws Exception {
        final long timestamp_1 = System.currentTimeMillis();
        System.out.println(timestamp_1);
        final long timestamp_1_f = TimeUtil.getTimeFloored(timestamp_1, 30, TimeUnit.SECONDS);
        System.out.println(timestamp_1_f);
        final long timestamp_1_c = TimeUtil.getTimeCeiled(timestamp_1, 30, TimeUnit.SECONDS);
        System.out.println(timestamp_1_c);
        final Instant instant_now_1 = Instant.now();
        printInstant(instant_now_1);
        final Instant instant_now_1_f = TimeUtil.getTimeFloored(instant_now_1, 30, TimeUnit.SECONDS);
        final Instant instant_now_1_c = TimeUtil.getTimeCeiled(instant_now_1, 30, TimeUnit.SECONDS);
        printInstant(instant_now_1_f);
        printInstant(instant_now_1_c);
        Thread.sleep(40000);
        System.out.println();
        final Instant instant_now_2 = Instant.now();
        printInstant(instant_now_2);
        final Instant instant_now_2_f = TimeUtil.getTimeFloored(instant_now_2, 30, TimeUnit.SECONDS);
        final Instant instant_now_2_c = TimeUtil.getTimeCeiled(instant_now_2, 30, TimeUnit.SECONDS);
        printInstant(instant_now_2_f);
        printInstant(instant_now_2_c);
    }
    
    public static final void printInstant(Instant instant) {
        System.out.println(instant == null ? instant : ZonedDateTime.ofInstant(instant, ZoneId.of("Europe/Berlin")).format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
    }
    
}
