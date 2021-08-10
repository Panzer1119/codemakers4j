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

package de.codemakers.base.util;

import de.codemakers.security.util.EasyCryptUtil;

import java.time.Instant;
import java.util.Random;

public class IDTimeUtil {
    
    public static final int ID_TIMESTAMP_LENGTH = 48;
    public static final int ID_RANDOM_LENGTH = Long.SIZE - ID_TIMESTAMP_LENGTH;
    public static final long TIMESTAMP_MAX = (long) Math.pow(2, ID_TIMESTAMP_LENGTH);
    public static final Instant TIMESTAMP_MAX_INSTANT = Instant.ofEpochMilli(TIMESTAMP_MAX);
    private static final Random RANDOM = new Random(EasyCryptUtil.getSecurestRandom().nextLong());
    
    public static final long createId() {
        return createId(System.currentTimeMillis());
    }
    
    public static final long createId(long timestamp) {
        return createId(timestamp, getShortenedRandom());
    }
    
    public static final long createId(long timestamp, int random) {
        return (timestamp << ID_RANDOM_LENGTH) | random;
    }
    
    public static final long getTimestamp(long id) {
        return id >>> ID_RANDOM_LENGTH;
    }
    
    public static final int getRandom(long id) {
        return (int) ((id << ID_TIMESTAMP_LENGTH) >>> ID_TIMESTAMP_LENGTH);
    }
    
    public static final int shortenToRandom(int random) {
        return random >>> ID_RANDOM_LENGTH;
    }
    
    public static final int getShortenedRandom() {
        return shortenToRandom(RANDOM.nextInt());
    }
    
}
