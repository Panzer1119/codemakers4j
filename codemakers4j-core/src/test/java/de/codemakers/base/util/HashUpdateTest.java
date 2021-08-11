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

import net.openhft.hashing.LongHashFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;
import java.util.Arrays;

public class HashUpdateTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        final byte[] bytes_1 = new byte[10];
        SecureRandom.getInstanceStrong().nextBytes(bytes_1);
        logger.info(String.format("bytes_1  =%s", Arrays.toString(bytes_1)));
        final byte[] bytes_2_1 = new byte[bytes_1.length / 2];
        final byte[] bytes_2_2 = new byte[bytes_1.length / 2];
        System.arraycopy(bytes_1, 0, bytes_2_1, 0, bytes_2_1.length);
        System.arraycopy(bytes_1, bytes_2_1.length, bytes_2_2, 0, bytes_2_2.length);
        logger.info(String.format("bytes_2_1=%s", Arrays.toString(bytes_2_1)));
        logger.info(String.format("bytes_2_2=%s", Arrays.toString(bytes_2_2)));
        final long hash_1 = LongHashFunction.xx().hashBytes(bytes_1);
        logger.info(String.format("hash_1  =%d", hash_1));
        final long hash_2_1 = LongHashFunction.xx().hashBytes(bytes_2_1);
        logger.info(String.format("hash_2_1=%d", hash_2_1));
        final long hash_2_2 = LongHashFunction.xx(hash_2_1).hashBytes(bytes_2_2);
        logger.info(String.format("hash_2_2=%d", hash_2_2));
    }
    
}
