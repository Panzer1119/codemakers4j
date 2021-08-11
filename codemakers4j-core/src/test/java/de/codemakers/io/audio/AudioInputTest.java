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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class AudioInputTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static void main(String[] args) throws Exception {
        final Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (int i = 0; i < mixers.length; i++) {
            System.out.println(i + " " + mixers[i]);
        }
        
        final Mixer.Info mixerInfo_input = mixers[9];
        final Mixer.Info mixerInfo_output = mixers[1];
        
        if (false) {
            return;
        }
        
        final AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        logger.debug("audioFormat=" + audioFormat);
        final DataLine.Info info_input = new DataLine.Info(TargetDataLine.class, audioFormat);
        logger.debug("info_input=" + info_input);
        if (!AudioSystem.isLineSupported(info_input)) {
            logger.error(String.format("Line info_input is not supported \"%s\" (%s)", info_input, info_input == null ? "" : info_input.getClass()));
            return;
        }
        final DataLine.Info info_output = new DataLine.Info(SourceDataLine.class, audioFormat);
        logger.debug("info_output=" + info_output);
        if (!AudioSystem.isLineSupported(info_output)) {
            logger.error(String.format("Line info_output is not supported \"%s\" (%s)", info_output, info_output == null ? "" : info_output.getClass()));
            return;
        }
        //final TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
        final Mixer mixer_output = AudioSystem.getMixer(mixerInfo_output);
        logger.debug("mixer_output=" + mixer_output);
        final SourceDataLine sourceDataLine = (SourceDataLine) mixer_output.getLine(info_output);
        logger.debug("sourceDataLine=" + sourceDataLine);
        sourceDataLine.open(audioFormat);
        logger.debug("sourceDataLine.getControls=" + Arrays.toString(sourceDataLine.getControls()));
        ((FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN)).setValue(6.0F);
        logger.debug("sourceDataLine.getControls=" + Arrays.toString(sourceDataLine.getControls()));
        
        final Mixer mixer_input = AudioSystem.getMixer(mixerInfo_input);
        logger.debug("mixer_input=" + mixer_input);
        final TargetDataLine targetDataLine = (TargetDataLine) mixer_input.getLine(info_input);
        logger.debug("targetDataLine=" + targetDataLine);
        targetDataLine.open(audioFormat);
        logger.debug("targetDataLine.getControls=" + Arrays.toString(targetDataLine.getControls()));
        
        final File file_temp = File.createTempFile(AudioInputTest.class.getSimpleName() + "_", ".pcm");
        
        logger.debug("file_temp=" + file_temp);
        
        final OutputStream outputStream = new FileOutputStream(file_temp);
        
        final byte[] buffer = new byte[targetDataLine.getBufferSize() / 5];
        
        final AtomicBoolean stopped = new AtomicBoolean(false);
        int read = 0;
        
        Standard.async(() -> {
            Thread.sleep(8000);
            logger.info("Stopping capturing");
            stopped.set(true);
        });
        
        logger.info("Starting capturing");
        
        targetDataLine.start();
        sourceDataLine.start();
        
        while (!stopped.get() && read != -1) {
            read = targetDataLine.read(buffer, 0, buffer.length);
            outputStream.write(buffer, 0, read);
            sourceDataLine.write(buffer, 0, read);
        }
        
        targetDataLine.stop();
        sourceDataLine.stop();
        
        outputStream.close();
        
        
        Thread.sleep(1000);
        
        Standard.addShutdownHook(() -> {
            logger.debug("Shutting down targetDataLine \"" + targetDataLine + "\"");
            targetDataLine.stop(); //TODO Necessary?
            targetDataLine.close();
        });
        Standard.addShutdownHook(() -> {
            logger.debug("Shutting down sourceDataLine \"" + sourceDataLine + "\"");
            sourceDataLine.stop(); //TODO Necessary?
            sourceDataLine.close();
        });
    }
    
}
