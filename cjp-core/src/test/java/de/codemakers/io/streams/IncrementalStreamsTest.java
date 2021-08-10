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

import de.codemakers.base.util.interfaces.Snowflake;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.EOFException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;

public class IncrementalStreamsTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        final PipedOutputStream pipedOutputStream = new PipedOutputStream();
        final PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);
        final IncrementalObjectOutputStream<TestObject> incrementalObjectOutputStream = new IncrementalObjectOutputStream<>(pipedOutputStream);
        final IncrementalObjectInputStream<TestObject> incrementalObjectInputStream = new IncrementalObjectInputStream<>(pipedInputStream);
        new Thread(() -> {
            Thread.currentThread().setName("RECEIVER-THREAD");
            try {
                logger.info("[RECEIVER] TEST started");
                TestObject testObject = null;
                while (true) {
                    testObject = incrementalObjectInputStream.readIncrementalObject();
                    logger.error("[RECEIVER] TEST received: " + testObject);
                }
                //logger.info("TEST stopped");
            } catch (EOFException ignore) {
                System.err.println("[RECEIVER] TEST EOF");
            } catch (Exception ex) {
                logger.info("[RECEIVER] TEST errored");
                logger.error(ex);
            }
        }).start();
        /*
        new Thread(() -> {
            try {
                logger.info("BUFFEREDREADER started");
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pipedInputStream));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    logger.info("BUFFEREDREADER: " + line);
                }
                bufferedReader.close();
                logger.info("BUFFEREDREADER stopped");
            } catch (Exception ex) {
                logger.info("BUFFEREDREADER errored");
                logger.error(ex);
            }
        }).start();
        */
        Thread.sleep(500);
        /*
        final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(pipedOutputStream));
        bufferedWriter.write("Test " + Math.random());
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();
        //pipedInputStream.close();
        */
        new Thread(() -> {
            Thread.currentThread().setName(" SENDER-THREAD ");
            try {
                final TestObject testObject = new TestObject();
                logger.info("[SENDER  ] Sending TestObject: " + testObject);
                incrementalObjectOutputStream.writeObject(testObject);
                incrementalObjectOutputStream.flush();
                Thread.sleep(1000);
                testObject.newRandomNumber();
                logger.info("[SENDER  ] Sending TestObject: " + testObject);
                incrementalObjectOutputStream.writeObject(testObject);
                incrementalObjectOutputStream.flush();
                Thread.sleep(1000);
                testObject.newRandomNumber();
                logger.info("[SENDER  ] Sending incremental TestObject: " + testObject);
                incrementalObjectOutputStream.writeIncrementalObject(testObject, true, true);
                incrementalObjectOutputStream.flush();
                Thread.sleep(1000);
                logger.info("[SENDER  ] Sending incremental TestObject: " + testObject);
                incrementalObjectOutputStream.writeIncrementalObject(testObject, true, true);
                incrementalObjectOutputStream.flush();
                Thread.sleep(5000);
                incrementalObjectOutputStream.close();
            } catch (Exception ex) {
                logger.error(ex);
            }
        }).start();
        //incrementalObjectInputStream.close();
    }
    
    static class TestObject implements Serializable, Snowflake {
        
        private final long id;
        public double random = Math.random();
        
        public TestObject() {
            this.id = generateId();
        }
        
        public TestObject(long id) {
            this.id = id;
        }
        
        public void newRandomNumber() {
            final double old = random;
            while (old == random) {
                random = Math.random();
            }
        }
        
        @Override
        public String toString() {
            return getClass().getSimpleName() + "{" + "id=" + id + ", random=" + random + '}';
        }
        
        @Override
        public long getId() {
            return id;
        }
        
    }
    
    public static class Test {
    
    }
    
}
