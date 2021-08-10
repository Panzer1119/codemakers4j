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

package de.codemakers.io.audio.net;

import de.codemakers.base.util.interfaces.Closeable;
import de.codemakers.base.util.interfaces.Startable;
import de.codemakers.base.util.interfaces.Stoppable;
import de.codemakers.io.audio.AudioReceiverTest;
import de.codemakers.io.audio.AudioSenderTest;
import de.codemakers.io.audio.IAudioTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class AudioClientTest implements IAudioTest, Closeable, Startable, Stoppable {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final AudioFormat AUDIO_FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
    
    protected final InetAddress inetAddress;
    protected final int port;
    protected AudioSenderTest audioSenderTest;
    protected AudioReceiverTest audioReceiverTest;
    protected Socket socket;
    protected InputStream inputStream;
    protected OutputStream outputStream;
    
    protected Mixer.Info mixerInfo_input;
    protected Mixer.Info mixerInfo_output;
    
    public AudioClientTest(InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        this.port = port;
        initSocket();
    }
    
    public AudioClientTest(Socket socket) {
        this.inetAddress = socket.getInetAddress();
        this.port = socket.getPort();
        this.socket = socket;
        initSocket();
    }
    
    protected void initStreams() {
        //Override this if you want to do stuff with the Streams
    }
    
    private void initSocket() {
        try {
            if (socket == null) {
                socket = new Socket(inetAddress, port);
            }
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
    @Override
    public void init() throws Exception {
        initStreams();
        audioSenderTest = new AudioSenderTest(AUDIO_FORMAT, AudioSystem.getMixer(mixerInfo_input), outputStream);
        audioReceiverTest = new AudioReceiverTest(AUDIO_FORMAT, AudioSystem.getMixer(mixerInfo_output), inputStream);
        audioSenderTest.init();
        audioReceiverTest.init();
    }
    
    @Override
    public boolean start() throws Exception {
        boolean worked = true;
        if (audioSenderTest != null && !audioSenderTest.start()) {
            worked = false;
        }
        if (audioReceiverTest != null && !audioReceiverTest.start()) {
            worked = false;
        }
        return worked;
    }
    
    @Override
    public boolean stop() throws Exception {
        boolean worked = true;
        if (audioSenderTest != null && !audioSenderTest.stop()) {
            worked = false;
        }
        if (audioReceiverTest != null && !audioReceiverTest.stop()) {
            worked = false;
        }
        return worked;
    }
    
    @Override
    public void closeIntern() throws Exception {
        if (socket != null) {
            socket.close();
            socket = null;
            inputStream = null;
            outputStream = null;
        }
        if (audioSenderTest != null) {
            audioSenderTest.closeWithoutException();
        }
        if (audioReceiverTest != null) {
            audioReceiverTest.closeWithoutException();
        }
    }
    
    public Mixer.Info getMixerInfo_input() {
        return mixerInfo_input;
    }
    
    public AudioClientTest setMixerInfo_input(Mixer.Info mixerInfo_input) {
        this.mixerInfo_input = mixerInfo_input;
        return this;
    }
    
    public Mixer.Info getMixerInfo_output() {
        return mixerInfo_output;
    }
    
    public AudioClientTest setMixerInfo_output(Mixer.Info mixerInfo_output) {
        this.mixerInfo_output = mixerInfo_output;
        return this;
    }
    
    @Override
    public String toString() {
        return "AudioClientTest{" + "inetAddress=" + inetAddress + ", port=" + port + ", audioSenderTest=" + audioSenderTest + ", audioReceiverTest=" + audioReceiverTest + ", socket=" + socket + ", inputStream=" + inputStream + ", outputStream=" + outputStream + '}';
    }
    
}
