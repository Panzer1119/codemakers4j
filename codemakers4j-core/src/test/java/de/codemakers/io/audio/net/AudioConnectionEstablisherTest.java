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
import java.net.InetAddress;
import java.util.Scanner;

public class AudioConnectionEstablisherTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final int PORT = 5463;
    
    public static void main(String[] args) throws Exception {
        final Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (int i = 0; i < mixers.length; i++) {
            logger.info(String.format("%d - %s", i, mixers[i]));
        }
        final Scanner scanner = new Scanner(System.in);
        Mixer.Info mixerInfo_input = null;
        if (args.length == 3) {
            mixerInfo_input = mixers[Integer.parseInt(args[1])];
        } else if (args.length == 4) {
            mixerInfo_input = mixers[Integer.parseInt(args[2])];
        } else {
            logger.info("Enter the Number you want to use as the Input Device:");
            mixerInfo_input = mixers[scanner.nextInt()];
        }
        logger.info("You selected \"" + mixerInfo_input + "\" as the Input Device");
        logger.info("");
        Mixer.Info mixerInfo_output = null;
        if (args.length == 3) {
            mixerInfo_output = mixers[Integer.parseInt(args[2])];
        } else if (args.length == 4) {
            mixerInfo_output = mixers[Integer.parseInt(args[3])];
        } else {
            logger.info("Enter the Number you want to use as the Output Device:");
            mixerInfo_output = mixers[scanner.nextInt()];
        }
        logger.info("You selected \"" + mixerInfo_output + "\" as the Output Device");
        logger.info("");
        final InetAddress inetAddress = InetAddress.getByName(args[0]);
        int port = PORT;
        if (args.length == 2 || args.length == 4) {
            port = Integer.parseInt(args[1]);
        }
        logger.info("Connecting");
        final AudioClientTest audioClientTest = new AudioClientTest(inetAddress, port);
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
