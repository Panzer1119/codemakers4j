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
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioReceiverTest implements Closeable, IAudioTest, Startable, Stoppable {
    
    private static final Logger logger = LogManager.getLogger();
    
    protected final AudioFormat audioFormat;
    protected final DataLine.Info info;
    protected final Mixer mixer;
    protected final InputStream inputStream;
    protected SourceDataLine sourceDataLine;
    protected final AtomicBoolean stopped = new AtomicBoolean(false);
    protected Thread thread = null;
    
    public AudioReceiverTest(AudioFormat audioFormat, Mixer mixer, InputStream inputStream) {
        this.audioFormat = audioFormat;
        this.info = new DataLine.Info(SourceDataLine.class, audioFormat);
        this.mixer = mixer;
        this.inputStream = inputStream;
    }
    
    @Override
    public void init() throws Exception {
        if (!AudioSystem.isLineSupported(info)) {
            throw new Exception(String.format("Line \"%s\" is not supported!", info));
        }
        sourceDataLine = (SourceDataLine) mixer.getLine(info);
        //((FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN)).setValue(6.0F); //FIXME
        sourceDataLine.open(audioFormat);
        sourceDataLine.addLineListener((event) -> {
            if (event.getType() == LineEvent.Type.CLOSE || event.getType() == LineEvent.Type.STOP) {
                stopWithoutException();
            }
        });
        thread = Standard.toughThread(() -> {
            final byte[] buffer = new byte[sourceDataLine.getBufferSize() / 5];
            int read = 0;
            while (!stopped.get()) {
                read = inputStream.read(buffer, 0, buffer.length);
                if (read == -1) {
                    logger.warn(String.format("%s ended with -1", inputStream.getClass().getSimpleName()));
                    stopped.set(true);
                    break;
                }
                sourceDataLine.write(buffer, 0, read);
            }
            logger.debug(String.format("%s Thread stopped", getClass().getSimpleName()));
        });
        logger.debug(String.format("%s initiated", getClass().getSimpleName()));
    }
    
    @Override
    public boolean start() throws Exception {
        stopped.set(false);
        thread.start();
        sourceDataLine.start();
        logger.debug(String.format("%s started", getClass().getSimpleName()));
        return sourceDataLine.isActive();
    }
    
    @Override
    public boolean stop() throws Exception {
        //sourceDataLine.flush();
        sourceDataLine.stop();
        Thread.sleep(750);
        stopped.set(true);
        Thread.sleep(250);
        thread.interrupt();
        logger.debug(String.format("%s stopped", getClass().getSimpleName()));
        return !sourceDataLine.isActive();
    }
    
    @Override
    public void closeIntern() throws Exception {
        if (!stopped.get()) {
            stop();
        }
        if (sourceDataLine != null) {
            if (sourceDataLine.isActive()) {
                sourceDataLine.stop(); //TODO Necessary?
            }
            sourceDataLine.close();
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
    
    public InputStream getInputStream() {
        return inputStream;
    }
    
    public SourceDataLine getSourceDataLine() {
        return sourceDataLine;
    }
    
    @Override
    public String toString() {
        return "AudioReceiverTest{" + "audioFormat=" + audioFormat + ", info=" + info + ", mixer=" + mixer + ", inputStream=" + inputStream + ", sourceDataLine=" + sourceDataLine + ", stopped=" + stopped + ", thread=" + thread + '}';
    }
    
}
