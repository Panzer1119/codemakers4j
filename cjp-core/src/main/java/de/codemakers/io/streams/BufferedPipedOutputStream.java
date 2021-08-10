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

package de.codemakers.io.streams;

import de.codemakers.base.Standard;
import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.base.util.tough.ToughRunnable;
import de.codemakers.io.streams.exceptions.StreamClosedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BufferedPipedOutputStream extends PipedOutputStream {
    
    protected final Object lock = new Object();
    //protected final Queue<byte[]> buffer = new ConcurrentLinkedQueue<>(); //FIXME What is the problem with this method? The current method uses probably more memory...
    protected final Queue<ToughRunnable> buffer_ = new ConcurrentLinkedQueue<>();
    protected Thread thread = null;
    protected ToughFunction<Thread, Thread> threadFunction = null;
    
    public BufferedPipedOutputStream() {
        super();
        initThread();
    }
    
    public BufferedPipedOutputStream(PipedInputStream pipedInputStream) throws IOException {
        super(pipedInputStream);
        initThread();
    }
    
    private void initThread() {
        thread = Standard.toughThread(this::loop);
        thread.setName(this + "-Writing-" + Thread.class.getSimpleName());
        if (threadFunction != null) {
            thread = Objects.requireNonNull(threadFunction.applyWithoutException(thread), "threadFunction.applyWithoutException(thread)");
        }
    }
    
    private void loop() throws Exception {
        try {
            while (pipedInputStream != null && pipedInputStream.connected) {
                final ToughRunnable toughRunnable = buffer_.poll();
                if (toughRunnable == null) {
                    synchronized (lock) {
                        lock.wait();
                    }
                    continue;
                }
                toughRunnable.run();
                /*
                final byte[] temp = buffer.poll();
                if (temp == null) {
                    synchronized (lock) {
                        lock.wait();
                    }
                    continue;
                }
                super.write(temp);
                */
                //lock.notify(); //TODO Is this creating a deadlock?
                
            }
            if (pipedInputStream != null) {
                throw new StreamClosedException();
            }
        } catch (InterruptedException ex) {
            //Nothing probably close was called
        }
    }
    
    public Thread getThread() {
        return thread;
    }
    
    public BufferedPipedOutputStream setThread(Thread thread) {
        if (this.thread != null && this.thread.isAlive()) {
            throw new IllegalStateException("A " + Thread.class.getSimpleName() + " is already running");
        }
        this.thread = thread;
        return this;
    }
    
    public ToughFunction<Thread, Thread> getThreadFunction() {
        return threadFunction;
    }
    
    public BufferedPipedOutputStream setThreadFunction(ToughFunction<Thread, Thread> threadFunction) {
        this.threadFunction = threadFunction;
        if (thread == null || !thread.isAlive()) {
            initThread();
        }
        return this;
    }
    
    @Override
    public synchronized void connect(PipedInputStream pipedInputStream) throws IOException {
        super.connect(pipedInputStream);
        thread.start();
    }
    
    @Override
    public synchronized void write(int b) throws IOException {
        //buffer.add(new byte[] {(byte) (b & 0xFF)}); //FIXME What is the problem with this method? The current method uses probably more memory...
        buffer_.add(() -> super.write(b));
        synchronized (lock) {
            lock.notify();
        }
    }
    
    @Override
    public synchronized void write(byte[] data) throws IOException {
        write(data, 0, data.length);
    }
    
    @Override
    public synchronized void write(byte[] data, int off, int len) throws IOException {
        /* //FIXME What is the problem with this method? The current method uses probably more memory...
        final byte[] temp = new byte[len - off];
        System.arraycopy(data, off, temp, 0, len);
        buffer.add(temp);
        */
        final byte[] temp = Arrays.copyOf(data, data.length);
        buffer_.add(() -> super.write(temp, off, len));
        synchronized (lock) {
            lock.notify();
        }
    }
    
    @Override
    public synchronized void flush() throws IOException {
        synchronized (lock) {
            lock.notify();
            //lock.wait(); //TODO Is this creating a deadlock?
        }
        super.flush();
    }
    
    @Override
    public synchronized void close() throws IOException {
        super.close();
        thread.interrupt(); //TODO Do more to end the thread? (Because what happens when the thread is blocked by the super.write method?)
    }
    
}
