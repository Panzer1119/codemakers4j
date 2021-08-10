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
import de.codemakers.base.logging.LogLevel;
import de.codemakers.security.interfaces.Decryptor;
import de.codemakers.security.interfaces.Encryptor;
import de.codemakers.security.util.AESCryptUtil;
import de.codemakers.security.util.EasyCryptUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.io.*;
import java.security.SecureRandom;
import java.util.Arrays;

public class AudioStreamTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static Encryptor encryptor = null;
    public static Decryptor decryptor = null;
    public static final byte[] iv = new byte[AESCryptUtil.IV_BYTES_CBC];
    
    public static void main(String[] args) throws Exception {
        final AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        final Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (int i = 0; i < mixers.length; i++) {
            logger.log(LogLevel.FINE, String.format("%d - %s", i, mixers[i]));
        }
        final Mixer.Info mixerInfo_input = mixers[9];
        final Mixer.Info mixerInfo_output = mixers[1];
        final PipedInputStream pipedInputStream = new PipedInputStream();
        final PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
        InputStream inputStream = new BufferedInputStream(pipedInputStream);
        OutputStream outputStream = pipedOutputStream;
        if (true) {
            initSecure();
            //outputStream = encryptor.toCipherOutputStreamWithIV(outputStream, iv);
            outputStream = encryptor.toCipherOutputStream(outputStream, iv);
            //inputStream = decryptor.toCipherInputStreamWithIV(inputStream);
            inputStream = decryptor.toCipherInputStream(inputStream, iv);
            logger.info("STREAMS ARE SECURED!!!");
        }
        logger.debug("inputStream=" + inputStream);
        logger.debug("outputStream=" + outputStream);
        final AudioSenderTest audioSenderTest = new AudioSenderTest(audioFormat, AudioSystem.getMixer(mixerInfo_input), outputStream);
        logger.debug("audioSenderTest=" + audioSenderTest);
        final AudioReceiverTest audioReceiverTest = new AudioReceiverTest(audioFormat, AudioSystem.getMixer(mixerInfo_output), inputStream);
        logger.debug("audioReceiverTest=" + audioReceiverTest);
        audioSenderTest.init();
        logger.debug("audioSenderTest=" + audioSenderTest);
        audioReceiverTest.init();
        logger.debug("audioReceiverTest=" + audioReceiverTest);
        Standard.addShutdownHook(audioSenderTest::close);
        Standard.addShutdownHook(audioReceiverTest::close);
        audioReceiverTest.start();
        audioSenderTest.start();
        Thread.sleep(10000);
        logger.info("Shutting down");
        audioSenderTest.stop();
        audioReceiverTest.stop();
        System.exit(0);
    }
    
    public static void initSecure() throws Exception {
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(AESCryptUtil.ALGORITHM_AES);
        keyGenerator.init(256, SecureRandom.getInstanceStrong());
        final SecretKey secretKey = keyGenerator.generateKey();
        logger.info("secretKey=" + secretKey);
        System.arraycopy(AESCryptUtil.generateSecureRandomIVAESCBC(), 0, iv, 0, iv.length);
        logger.info("iv=" + Arrays.toString(iv));
        //encryptor = AESCryptUtil.createEncryptorAESGCMNoPadding(secretKey, 128);
        encryptor = AESCryptUtil.createEncryptorAESCBCPKCS5Padding(secretKey);
        //decryptor = AESCryptUtil.createDecryptorAESGCMNoPadding(secretKey, 128);
        decryptor = AESCryptUtil.createDecryptorAESCBCPKCS5Padding(secretKey);
        testSecure();
    }
    
    private static void testSecure() throws Exception {
        //final String text = "This is a Text for Testing purposes!";
        final byte[] output = EasyCryptUtil.generateSecureRandomBytes(16);
        logger.info("testSecure | output=" + Arrays.toString(output));
        final PipedInputStream pipedInputStream = new PipedInputStream();
        final PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
        //InputStream inputStream = new BufferedInputStream(pipedInputStream);
        InputStream inputStream = pipedInputStream;
        OutputStream outputStream = pipedOutputStream;
        inputStream = decryptor.toCipherInputStream(inputStream, iv);
        //final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        outputStream = encryptor.toCipherOutputStream(outputStream, iv);
        //final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        final InputStream inputStream_final = inputStream;
        final OutputStream outputStream_final = outputStream;
        outputStream_final.write(output);
        logger.info("output has been written");
        Standard.async(() -> {
            final byte[] buffer = new byte[1];
            int read = 0;
            while (read != -1) {
                read = inputStream_final.read(buffer, 0, buffer.length);
                logger.info(String.format("read=%d, buffer=%s", read, Arrays.toString(buffer)));
            }
            logger.info("END");
            final byte[] input = new byte[output.length];
            inputStream_final.read(input);
            logger.info("testSecure | input=" + Arrays.toString(input));
        });
        final byte[] cipherText = encryptor.encrypt(output, iv);
        logger.info("testSecure | cipherText=" + Arrays.toString(cipherText));
        final byte[] test = decryptor.decrypt(cipherText, iv);
        logger.info("testSecure | test=" + Arrays.toString(test));
        Thread.sleep(5000);
    }
    
}
