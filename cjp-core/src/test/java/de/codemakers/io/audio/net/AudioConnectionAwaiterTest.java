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

import de.codemakers.base.Standard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.net.ServerSocket;
import java.net.Socket;

public class AudioConnectionAwaiterTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final int PORT = 5463;
    
    public static void main(String[] args) throws Exception {
        final Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (int i = 0; i < mixers.length; i++) {
            logger.info(String.format("%d - %s", i, mixers[i]));
        }
        final Mixer.Info mixerInfo_input = mixers[6];
        final Mixer.Info mixerInfo_output = mixers[1];
        final ServerSocket serverSocket = new ServerSocket(PORT);
        Standard.addShutdownHook(serverSocket::close);
        logger.info("Awaiting Connection");
        final Socket socket = serverSocket.accept();
        logger.info(String.format("Accepted Connection from %s:%d", socket.getInetAddress(), socket.getPort()));
        final AudioClientTest audioClientTest = new AudioClientTest(socket);
        Standard.addShutdownHook(audioClientTest::close);
        audioClientTest.setMixerInfo_input(mixerInfo_input);
        audioClientTest.setMixerInfo_output(mixerInfo_output);
        logger.info("audioClientTest=" + audioClientTest);
        audioClientTest.init();
        logger.info("audioClientTest=" + audioClientTest);
        audioClientTest.start();
        logger.info("audioClientTest=" + audioClientTest);
        Standard.async(() -> {
            Thread.sleep(20000);
            logger.info("Shutting down");
            Standard.silentError(() -> {
                audioClientTest.stop();
                Thread.sleep(500);
            });
            System.exit(0);
        });
    }
    
}
