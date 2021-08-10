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

package de.codemakers.io.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class AdvancedFileTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        AdvancedFile.DEBUG_TO_STRING = true;
        final AdvancedFile advancedFile_3zip = new AdvancedFile("src/test/resources/1/2/3.zip");
        System.out.println("advancedFile_3zip                           = " + advancedFile_3zip);
        System.out.println("advancedFile_3zip.getPath()                 = " + advancedFile_3zip.getPath());
        System.out.println("advancedFile_3zip.getAbsolutePath()         = " + advancedFile_3zip.getAbsolutePath());
        System.out.println("advancedFile_3zip.getAbsoluteFile()         = " + advancedFile_3zip.getAbsoluteFile());
        System.out.println("advancedFile_3zip.exists()                  = " + advancedFile_3zip.exists());
        System.out.println("====================================================================================================================");
        final AdvancedFile advancedFile_3zip_testzip = new AdvancedFile("src/test/resources/1/2/3.zip/3.1/3.2/test.zip");
        System.out.println("advancedFile_3zip_testzip                   = " + advancedFile_3zip_testzip);
        System.out.println("advancedFile_3zip_testzip.getPath()         = " + advancedFile_3zip_testzip.getPath());
        System.out.println("advancedFile_3zip_testzip.getAbsolutePath() = " + advancedFile_3zip_testzip.getAbsolutePath());
        System.out.println("advancedFile_3zip_testzip.getAbsoluteFile() = " + advancedFile_3zip_testzip.getAbsoluteFile());
        System.out.println("advancedFile_3zip_testzip.exists()          = " + advancedFile_3zip_testzip.exists());
        System.out.println("====================================================================================================================");
        final AdvancedFile advancedFile_complete = new AdvancedFile("src/test/resources/1/2/3.zip/3.1/3.2/test.zip/test.txt");
        System.out.println("advancedFile_complete                       = " + advancedFile_complete);
        System.out.println("advancedFile_complete.getPath()             = " + advancedFile_complete.getPath());
        System.out.println("advancedFile_complete.getAbsolutePath()     = " + advancedFile_complete.getAbsolutePath());
        System.out.println("advancedFile_complete.getAbsoluteFile()     = " + advancedFile_complete.getAbsoluteFile());
        System.out.println("advancedFile_complete.exists()              = " + advancedFile_complete.exists());
        System.out.println("====================================================================================================================");
        System.out.println("====================================================================================================================");
        System.out.println("====================================================================================================================");
        final byte[] data_3zip = advancedFile_3zip.readBytes();
        System.out.println("advancedFile_3zip.readBytes() = " + Arrays.toString(data_3zip));
        final byte[] data_3zip_testzip = advancedFile_3zip_testzip.readBytes();
        System.out.println("data_3zip_testzip.readBytes() = " + Arrays.toString(data_3zip_testzip));
        final byte[] data_complete = advancedFile_complete.readBytes();
        System.out.println("data_complete.readBytes() = " + Arrays.toString(data_complete));
    }
    
}
