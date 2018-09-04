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

import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtilTest {
    
    public static final void main(String[] args) throws Exception {
        System.out.println(Long.toBinaryString(TimeUtil.TIMESTAMP_MAX).length());
        System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(TimeUtil.TIMESTAMP_MAX), ZoneId.systemDefault()));
        final long timestamp = System.currentTimeMillis();
        final int random = TimeUtil.shortenToRandom(SecureRandom.getInstanceStrong().nextInt());
        final long id = TimeUtil.createId(timestamp, random);
        System.out.println(String.format(" timestamp: %s%n    random: %s%n        id: %s", longS(timestamp, TimeUtil.ID_TIMESTAMP_LENGTH), longS(random, TimeUtil.ID_RANDOM_LENGTH), longS(id, Long.SIZE)));
        final long timestamp_ = TimeUtil.getTimestamp(id);
        final long random_ = TimeUtil.getRandom(id);
        System.out.println();
        System.out.println(String.format(" timestamp: %s%ntimestamp_: %s", longS(timestamp, TimeUtil.ID_TIMESTAMP_LENGTH), longS(timestamp_, TimeUtil.ID_TIMESTAMP_LENGTH)));
        System.out.println(String.format("    random: %s%n   random_: %s", longS(random, TimeUtil.ID_RANDOM_LENGTH), longS(random_, TimeUtil.ID_RANDOM_LENGTH)));
    }
    
    public static final String longS(long l, int len) {
        String l_ = Long.toBinaryString(l);
        final int length = l_.length();
        while (l_.length() < Long.SIZE) {
            l_ = "0" + l_;
        }
        len = Long.SIZE - len;
        if (len <= Long.SIZE / 2) {
            l_ = l_.substring(0, len) + "/" + l_.substring(len, Long.SIZE / 2) + "#" + l_.substring(Long.SIZE / 2);
        } else {
            l_ = l_.substring(0, Long.SIZE / 2) + "#" + l_.substring(Long.SIZE / 2, len) + "/" + l_.substring(len);
        }
        l_ += " : ";
        l_ += length;
        l_ += "/";
        l_ += Long.SIZE - len;
        l_ += " - ";
        l_ += ((Long.SIZE - len) - length);
        return l_;
    }
    
}
