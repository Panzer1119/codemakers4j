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

import java.util.stream.Collectors;

public class AdvancedFileInternTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) {
        AdvancedFile.DEBUG = true;
        AdvancedFile.DEBUG_FILE_PROVIDER = false;
        AdvancedFile.DEBUG_TO_STRING = true;
        AdvancedFile.DEBUG_TO_STRING_BIG = false;
        if (true) {
            logger.debug(new AdvancedFile(""));
            logger.debug(new AdvancedFile("").getPath());
            logger.debug(new AdvancedFile("").getAbsoluteFile().getPath());
            logger.debug(new AdvancedFile("").getAbsoluteFile().exists());
            logger.debug(new AdvancedFile("").getAbsoluteFile().listFiles(false));
        /*
        //Not useful on Windows (Because windows has not THE root folder, it uses multiple drive letters)
        logger.debug(new AdvancedFile("/"));
        logger.debug(new AdvancedFile("/").getPath());
        logger.debug(new AdvancedFile("/").exists());
        logger.debug(new AdvancedFile("/").listFiles(false));
        */
            logger.debug(new AdvancedFile("intern:/"));
            logger.debug(new AdvancedFile("intern:/").getPath());
            logger.debug(new AdvancedFile("intern:/").listFiles(false));
            logger.debug(new AdvancedFile("intern:/").listFiles(false)
                    .stream()
                    .map(AdvancedFile::getPath)
                    .collect(Collectors.joining(", ", "[", "]")));
            logger.debug(new AdvancedFile("intern:/de").listFiles(false));
            logger.debug(new AdvancedFile("intern:/de").listFiles(false)
                    .stream()
                    .map(AdvancedFile::getPath)
                    .collect(Collectors.joining(", ", "[", "]")));
            logger.debug(new AdvancedFile("intern:/de/codemakers").listFiles(false));
            logger.debug(new AdvancedFile("intern:/de/codemakers").listFiles(false)
                    .stream()
                    .map(AdvancedFile::getPath)
                    .collect(Collectors.joining(", ", "[", "]")));
            final AdvancedFile advancedFile_file_1 = new AdvancedFile("intern:/de/codemakers/io/file/" + AdvancedFileInternTest.class.getSimpleName() + ".class");
            System.out.println("advancedFile_file_1: " + advancedFile_file_1);
            System.out.println("advancedFile_file_1 exists: " + advancedFile_file_1.exists());
            final byte[] data_file_1 = advancedFile_file_1.readBytesWithoutException();
            System.out.println("data_file_1: " + data_file_1);
            //System.out.println("data_file_1: " + Arrays.toString(data_file_1));
            //System.out.println("advancedFile_file_1 readBytes: " + new String(data_file_1));
            final AdvancedFile advancedFile_folder_1 = new AdvancedFile("intern:/de/codemakers/io/file");
            System.out.println("advancedFile_folder_1: " + advancedFile_folder_1);
            System.out.println("advancedFile_folder_1 exists: " + advancedFile_folder_1.exists());
            System.out.println("advancedFile_folder_1 listFiles(false): " + advancedFile_folder_1.listFiles(false));
            System.out.println("advancedFile_folder_1 listFiles(true): " + advancedFile_folder_1.listFiles(true));
            final AdvancedFile advancedFile_file_2 = new AdvancedFile("intern:" + AdvancedFileTest.class.getSimpleName() + ".class");
            System.out.println("advancedFile_file_2: " + advancedFile_file_2);
            System.out.println("advancedFile_file_2 absoluteFile: " + advancedFile_file_2.getAbsoluteFile());
            System.out.println("advancedFile_file_2 exists: " + advancedFile_file_2.exists());
            System.out.println("advancedFile_file_2 isFile: " + advancedFile_file_2.isFile());
            System.out.println("advancedFile_file_2 isDirectory: " + advancedFile_file_2.isDirectory());
            final AdvancedFile advancedFile_folder_2 = new AdvancedFile("test/test_1");
            System.out.println("advancedFile_folder_2: " + advancedFile_folder_2);
            advancedFile_folder_2.mkdirsWithoutException();
            System.out.println("advancedFile_folder_2 exists: " + advancedFile_folder_2.exists());
            System.out.println("advancedFile_folder_2 listFiles: " + advancedFile_folder_2.listFiles());
            advancedFile_folder_2.listFiles()
                    .stream()
                    .filter(AdvancedFile::isFile)
                    .forEach((advancedFile) -> System.out.println(new String(advancedFile.readBytesWithoutException())));
            System.out.println("########");
            final AdvancedFile advancedFile_folder_3 = new AdvancedFile("../CJP-Base/test/test_1");
            System.out.println("advancedFile_folder_3: " + advancedFile_folder_3);
            System.out.println("advancedFile_folder_3 exists: " + advancedFile_folder_3.exists());
            System.out.println("advancedFile_folder_3 listFiles: " + advancedFile_folder_3.listFiles());
            advancedFile_folder_3.listFiles()
                    .stream()
                    .filter(AdvancedFile::isFile)
                    .forEach((advancedFile) -> System.out.println(new String(advancedFile.readBytesWithoutException())));
            System.out.println("########");
            final AdvancedFile advancedFile_file_3 = new AdvancedFile(advancedFile_folder_2, "test.txt");
            advancedFile_file_3.writeBytesWithoutException(("Test " + Math.random()).getBytes());
            System.out.println(advancedFile_file_3);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("=====================================================================================================================================================================================================================================");
        System.out.println("=====================================================================================================================================================================================================================================");
        System.out.println("=====================================================================================================================================================================================================================================");
        System.out.println();
        final AdvancedFile advancedFile_test_1231 = new AdvancedFile(AdvancedFile.PREFIX_INTERN, "test.txt");
        logger.debug("advancedFile_test_1231=" + advancedFile_test_1231);
        logger.debug("advancedFile_test_1231.exists()=" + advancedFile_test_1231.exists());
        logger.debug("advancedFile_test_1231.getPath()=" + advancedFile_test_1231.getPath());
        logger.debug("advancedFile_test_1231.getAbsolutePath()=" + advancedFile_test_1231.getAbsolutePath());
        logger.debug("advancedFile_test_1231.getAbsoluteFile()=" + advancedFile_test_1231.getAbsoluteFile());
        logger.debug("advancedFile_test_1231.getAbsoluteFile().exists()=" + advancedFile_test_1231.getAbsoluteFile().exists());
        logger.debug("advancedFile_test_1231.readBytesWithoutException()=\"" + new String(advancedFile_test_1231.readBytesWithoutException()) + "\"");
        System.out.println("=====================================================================================================================================================================================================================================");
        System.out.println("=====================================================================================================================================================================================================================================");
        System.out.println("=====================================================================================================================================================================================================================================");
        System.out.println();
        System.out.println();
        System.out.println();
    }
    
}
