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

package de.codemakers.io.streams;

import de.codemakers.base.Standard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.security.SecureRandom;
import java.util.Arrays;

public class EndableInputStreamTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        final PipedOutputStream pipedOutputStream = new PipedOutputStream();
        final PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        final EndableInputStream endableInputStream = new EndableInputStream(pipedInputStream);
        logger.info("endableInputStream=" + endableInputStream);
        logger.info("EndableInputStream.ENDED_BYTE=" + EndableInputStream.ENDED_BYTE);
        logger.info("EndableInputStream.ENDED_BYTE_INT=" + EndableInputStream.ENDED_BYTE_INT);
        logger.info("EndableInputStream.ESCAPE_BYTE=" + EndableInputStream.ESCAPE_BYTE);
        logger.info("EndableInputStream.ESCAPE_BYTE_INT=" + EndableInputStream.ESCAPE_BYTE_INT);
        Standard.async(() -> {
            int b = 0;
            while ((b = endableInputStream.read()) >= 0) {
                logger.info("[RECEIVER] " + ((byte) (b & 0xFF)));
            }
            logger.info("[RECEIVER] ENDED WITH: " + ((byte) (b & 0xFF)));
            endableInputStream.close();
            pipedInputStream.close();
        });
        final EndableOutputStream endableOutputStream = new EndableOutputStream(pipedOutputStream);
        //final int b = Byte.MIN_VALUE & 0xFF;
        //logger.info("[SENDER] " + b);
        //endableOutputStream.write(b);
        //endableOutputStream.write(128);
        //endableOutputStream.flush();
        //Thread.sleep(100);
        for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++) {
            logger.info("[SENDER] " + b);
            endableOutputStream.write(b & 0xFF);
            endableOutputStream.flush();
            //Thread.sleep(100);
            Thread.sleep(1);
        }
        Thread.sleep(1000);
        logger.info("");
        final String message = "Test";
        endableOutputStream.write(message.getBytes());
        endableOutputStream.flush();
        Thread.sleep(1000);
        logger.info("");
        final byte[] bytes = new byte[16];
        SecureRandom.getInstanceStrong().nextBytes(bytes);
        logger.info("bytes=" + Arrays.toString(bytes));
        endableOutputStream.write(bytes);
        endableOutputStream.close();
        pipedOutputStream.close();
        /*
        for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++) {
            logger.info("b=" + b + ", converted=" + (b & 0xFF) + ", double converted=" + ((byte) ((b & 0xFF) & 0xFF)));
        }
        */
    }
    
}
