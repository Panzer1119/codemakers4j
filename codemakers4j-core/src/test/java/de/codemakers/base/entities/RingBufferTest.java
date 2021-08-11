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

package de.codemakers.base.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class RingBufferTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static void main(String[] args) throws Exception {
        final RingBuffer ringBuffer = new RingBuffer(Integer.class, 5);
        logger.info("ringBuffer=" + ringBuffer);
        logger.info("ringBuffer.toArray=" + Arrays.toString(ringBuffer.toArray()));
        logger.info("ringBuffer.isEmpty=" + ringBuffer.isEmpty());
        logger.info("==============================================");
        for (int i = 0; i < 11; i++) {
            ringBuffer.add(i);
            logger.info("ringBuffer=" + ringBuffer);
            logger.info("ringBuffer.toArray=" + Arrays.toString(ringBuffer.toArray()));
            logger.info("ringBuffer.isEmpty=" + ringBuffer.isEmpty());
            logger.info("==============================================");
        }
        for (int i = 0; i < 5; i++) {
            logger.info("ringBuffer.remove=" + ringBuffer.remove());
            logger.info("ringBuffer=" + ringBuffer);
            logger.info("ringBuffer.toArray=" + Arrays.toString(ringBuffer.toArray()));
            logger.info("ringBuffer.isEmpty=" + ringBuffer.isEmpty());
            logger.info("==============================================");
        }
    }
    
}
