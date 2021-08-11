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

package de.codemakers.io.audio;

import de.codemakers.base.Standard;
import de.codemakers.base.util.interfaces.Closeable;
import de.codemakers.base.util.interfaces.Startable;
import de.codemakers.base.util.interfaces.Stoppable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.*;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioSenderTest implements Closeable, IAudioTest, Startable, Stoppable {
    
    private static final Logger logger = LogManager.getLogger();
    
    protected final AudioFormat audioFormat;
    protected final DataLine.Info info;
    protected final Mixer mixer;
    protected final OutputStream outputStream;
    protected TargetDataLine targetDataLine;
    protected final AtomicBoolean stopped = new AtomicBoolean(false);
    protected Thread thread = null;
    
    public AudioSenderTest(AudioFormat audioFormat, Mixer mixer, OutputStream outputStream) {
        this.audioFormat = audioFormat;
        this.info = new DataLine.Info(TargetDataLine.class, audioFormat);
        this.mixer = mixer;
        this.outputStream = outputStream;
    }
    
    @Override
    public void init() throws Exception {
        if (!AudioSystem.isLineSupported(info)) {
            throw new Exception(String.format("Line \"%s\" is not supported!", info));
        }
        targetDataLine = (TargetDataLine) mixer.getLine(info);
        targetDataLine.open(audioFormat);
        thread = Standard.toughThread(() -> {
            final byte[] buffer = new byte[targetDataLine.getBufferSize() / 5];
            int read = 0;
            while (!stopped.get()) {
                read = targetDataLine.read(buffer, 0, buffer.length);
                if (read == -1) {
                    logger.warn(String.format("%s ended with -1", targetDataLine.getClass().getSimpleName()));
                    stopped.set(true);
                    break;
                }
                outputStream.write(buffer, 0, read);
            }
            logger.debug(String.format("%s Thread stopped", getClass().getSimpleName()));
        });
        logger.debug(String.format("%s initiated", getClass().getSimpleName()));
    }
    
    @Override
    public boolean start() throws Exception {
        stopped.set(false);
        thread.start();
        targetDataLine.start();
        logger.debug(String.format("%s started", getClass().getSimpleName()));
        return targetDataLine.isActive();
    }
    
    @Override
    public boolean stop() throws Exception {
        //targetDataLine.drain();
        targetDataLine.stop();
        Thread.sleep(750);
        stopped.set(true);
        Thread.sleep(250);
        thread.interrupt();
        logger.debug(String.format("%s stopped", getClass().getSimpleName()));
        return !targetDataLine.isActive();
    }
    
    @Override
    public void closeIntern() throws Exception {
        if (!stopped.get()) {
            stop();
        }
        if (targetDataLine != null) {
            if (targetDataLine.isActive()) {
                targetDataLine.stop(); //TODO Necessary?
            }
            targetDataLine.close();
        }
        logger.debug(String.format("%s closed", getClass().getSimpleName()));
    }
    
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }
    
    public DataLine.Info getInfo() {
        return info;
    }
    
    public Mixer getMixer() {
        return mixer;
    }
    
    public OutputStream getOutputStream() {
        return outputStream;
    }
    
    public TargetDataLine getTargetDataLine() {
        return targetDataLine;
    }
    
    @Override
    public String toString() {
        return "AudioSenderTest{" + "audioFormat=" + audioFormat + ", info=" + info + ", mixer=" + mixer + ", outputStream=" + outputStream + ", targetDataLine=" + targetDataLine + ", stopped=" + stopped + ", thread=" + thread + '}';
    }
    
}
