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

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class BufferedPipedOutputStreamTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        if (false) {
            test();
            return;
        }
        final BufferedPipedOutputStream bufferedPipedOutputStream = new BufferedPipedOutputStream();
        logger.info("bufferedPipedOutputStream=" + bufferedPipedOutputStream);
        final PipedInputStream pipedInputStream = new PipedInputStream(bufferedPipedOutputStream);
        logger.info("pipedInputStream=" + pipedInputStream);
        Standard.async(() -> {
            logger.info("reading: " + pipedInputStream.read());
            Thread.currentThread().setName("RECEIVER");
            logger.info("Started Receiving");
            final ObjectInputStream objectInputStream = new ObjectInputStream(pipedInputStream);
            logger.info("received: \"" + objectInputStream.readObject() + "\"");
            logger.info("Stopping Receiving");
            objectInputStream.close();
            /*
            logger.info("Started Receiving");
            final DataInputStream dataInputStream = new DataInputStream(pipedInputStream);
            logger.info("received: \"" + dataInputStream.readInt() + "\"");
            logger.info("Stopping Receiving");
            dataInputStream.close();
            */
        });
        logger.info("Waiting");
        Thread.sleep(500);
        logger.info("Writing");
        bufferedPipedOutputStream.write(44);
        Thread.sleep(1000);
        Thread.currentThread().setName("TRANSMITTER");
        logger.info("Started Transmitting");
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedPipedOutputStream);
        /*
        for (int i = 0; i < 1; i++) {
            final TestObject testObject = new TestObject();
            logger.info("transmitting: \"" + testObject + "\"");
            objectOutputStream.writeObject(testObject);
        }
        */
        final TestObject testObject = new TestObject();
        logger.info("transmitting: \"" + testObject + "\"");
        objectOutputStream.writeObject(testObject);
        Thread.sleep(1000);
        logger.info("Stopping Transmitting");
        objectOutputStream.close();
        /*
        logger.info("Started Transmitting");
        final DataOutputStream dataOutputStream = new DataOutputStream(bufferedPipedOutputStream);
        logger.info("transmitting: \"42\"");
        dataOutputStream.writeInt(42);
        Thread.sleep(1000);
        logger.info("Stopping Transmitting");
        dataOutputStream.close();
        */
    }
    
    private static void test() throws Exception {
        final BufferedPipedOutputStream bufferedPipedOutputStream = new BufferedPipedOutputStream();
        final PipedInputStream pipedInputStream = new PipedInputStream(bufferedPipedOutputStream);
        Standard.async(() -> {
            final byte[] buffer = new byte[16];
            int read = -1;
            while ((read = pipedInputStream.read(buffer)) >= 0) {
                logger.info(String.format("buffer=%s, read=%d", Arrays.toString(buffer), read));
            }
            pipedInputStream.close();
        });
        final Random random = new Random();
        final byte[] temp = new byte[64];
        logger.info("Start");
        for (int i = 0; i < 8; i++) {
            random.nextBytes(temp);
            logger.info(String.format("temp=%s", Arrays.toString(temp)));
            bufferedPipedOutputStream.write(temp);
        }
        logger.info("Stop");
        bufferedPipedOutputStream.close();
    }
    
    public static class TestObject implements Serializable {
        
        public final double random;
        
        public TestObject() {
            this(Math.random());
        }
        
        public TestObject(double random) {
            this.random = random;
        }
        
        @Override
        public String toString() {
            return "TestObject{" + "random=" + random + '}';
        }
        
    }
    
}
