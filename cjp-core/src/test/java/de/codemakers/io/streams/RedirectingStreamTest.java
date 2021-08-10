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

import de.codemakers.io.file.AdvancedFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class RedirectingStreamTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        final AdvancedFile advancedFile_input = new AdvancedFile("test/test.txt");
        final AdvancedFile advancedFile_output = new AdvancedFile("test/test.txt.bak");
        final FileInputStream fileInputStream = (FileInputStream) advancedFile_input.createInputStream();
        final FileOutputStream fileOutputStream = (FileOutputStream) advancedFile_output.createOutputStream(false);
        final RedirectingStream<FileInputStream, FileOutputStream> redirectingStream = new RedirectingStream<>(fileInputStream, fileOutputStream);
        logger.info("redirectingStream=" + redirectingStream);
        redirectingStream.setStopOnClose(true);
        logger.info("redirectingStream=" + redirectingStream);
        redirectingStream.start();
        logger.info("RedirectingStream started");
        redirectingStream.createWaiter().waitFor();
        logger.info("RedirectingStream finished");
        logger.info("redirectingStream=" + redirectingStream);
    }
    
}
