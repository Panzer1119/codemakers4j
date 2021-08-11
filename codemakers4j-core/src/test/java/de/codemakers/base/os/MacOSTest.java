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

package de.codemakers.base.os;

import de.codemakers.base.Standard;
import de.codemakers.misc.os.functions.SpeakText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class MacOSTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static void main(String[] args) throws Exception {
        final ProcessBuilder processBuilder = new ProcessBuilder("osascript -e 'say \"Hello World!\"'");
        //final ProcessBuilder processBuilder = new ProcessBuilder("clear");
        final Process process = processBuilder.start();
        Standard.async(() -> {
            final InputStream inputStream = process.getInputStream();
            final byte[] buffer = new byte[16];
            int read = -1;
            while ((read = inputStream.read(buffer)) != -1) {
                System.out.write(buffer, 0, read);
            }
        });
        Standard.async(() -> {
            final InputStream inputStream = process.getErrorStream();
            final byte[] buffer = new byte[16];
            int read = -1;
            while ((read = inputStream.read(buffer)) != -1) {
                System.err.write(buffer, 0, read);
            }
        });
        /*
        Standard.async(() -> {
            final OutputStream outputStream = process.getOutputStream();
            final byte[] buffer = new byte[16];
            int read = -1;
            while ((read = System.in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        });
        */
        process.waitFor(1, TimeUnit.MINUTES);
        if (true) {
            return;
        }
        final SpeakText speakText = OSUtil.getFunction(SpeakText.class);
        logger.debug("Test 1: " + speakText.speak("Hello World!"));
        logger.debug("Test 2: " + speakText.speak("Second Test, how are you today?"));
    }
    
}
