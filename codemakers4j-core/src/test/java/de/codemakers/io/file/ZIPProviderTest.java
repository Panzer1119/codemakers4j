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

import java.io.File;

public class ZIPProviderTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) {
        AdvancedFile.DEBUG = true;
        AdvancedFile.DEBUG_TO_STRING = false;
        AdvancedFile.DEBUG_FILE_PROVIDER = false;
        if (true) {
            testJar();
            return;
        }
        final AdvancedFile zipFile = new AdvancedFile("test", "1", "2", "3.zip");
        System.out.println("zipFile=\"" + zipFile + "\"");
        System.out.println();
        System.out.println("zipFile.mayListFiles()=" + zipFile.mayListFiles());
        System.out.println("zipFile.isDirectory()=" + zipFile.isDirectory());
        System.out.println();
        System.out.println("zipFile.listFiles(true)=" + zipFile.listFiles(true));
        System.out.println();
        System.out.println("zipFile.listFiles(false)=" + zipFile.listFiles(false));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        final AdvancedFile folder_in_zipFile = new AdvancedFile(zipFile, "3.1");
        System.out.println("folder_in_zipFile=" + folder_in_zipFile);
        System.out.println();
        System.out.println("folder_in_zipFile.mayListFiles()=" + folder_in_zipFile.mayListFiles());
        System.out.println("folder_in_zipFile.isDirectory()=" + folder_in_zipFile.isDirectory());
        System.out.println();
        System.out.println("folder_in_zipFile.listFiles(true)=" + folder_in_zipFile.listFiles(true));
        System.out.println();
        System.out.println("folder_in_zipFile.listFiles(false)=" + folder_in_zipFile.listFiles(false));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        if (false) {
            return;
        }
        final AdvancedFile zipFile_in_zipFile = new AdvancedFile(folder_in_zipFile, "3.2", "test.zip");
        System.out.println("zipFile_in_zipFile=\"" + zipFile_in_zipFile + "\"");
        System.out.println();
        System.out.println("zipFile_in_zipFile.mayListFiles()=" + zipFile_in_zipFile.mayListFiles());
        System.out.println("zipFile_in_zipFile.isDirectory()=" + zipFile_in_zipFile.isDirectory());
        System.out.println();
        System.out.println("zipFile_in_zipFile.listFiles(true)=" + zipFile_in_zipFile.listFiles(true));
        System.out.println();
        System.out.println("zipFile_in_zipFile.listFiles(false)=" + zipFile_in_zipFile.listFiles(false));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        final AdvancedFile folder_in_zipFile_in_zipFile = new AdvancedFile(zipFile_in_zipFile, "test_1");
        System.out.println("folder_in_zipFile_in_zipFile=\"" + folder_in_zipFile_in_zipFile + "\"");
        System.out.println();
        System.out.println("folder_in_zipFile_in_zipFile.mayListFiles()=" + folder_in_zipFile_in_zipFile.mayListFiles());
        System.out.println("folder_in_zipFile_in_zipFile.isDirectory()=" + folder_in_zipFile_in_zipFile.isDirectory());
        System.out.println();
        System.out.println("folder_in_zipFile_in_zipFile.listFiles(true)=" + folder_in_zipFile_in_zipFile.listFiles(true));
        System.out.println();
        System.out.println("folder_in_zipFile_in_zipFile.listFiles(false)=" + folder_in_zipFile_in_zipFile.listFiles(false));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        final AdvancedFile file_in_zipFile_in_zipFile = new AdvancedFile(zipFile_in_zipFile, "test.txt");
        System.out.println("file_in_zipFile_in_zipFile=\"" + file_in_zipFile_in_zipFile + "\"");
        System.out.println();
        System.out.println("file_in_zipFile_in_zipFile.isFile()=" + file_in_zipFile_in_zipFile.isFile());
        System.out.println();
        System.out.println("file_in_zipFile_in_zipFile.readBytesWithoutException()=" + new String(file_in_zipFile_in_zipFile.readBytesWithoutException()));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        if (true) {
            return;
        }
        final File file_1 = new File("test/1/2/3.zip");
        final AdvancedFile advancedFile_file_1 = new AdvancedFile(new AdvancedFile(file_1), "3", "win.txt.zip", "win.txt");
        System.out.println("advancedFile_file_1: " + advancedFile_file_1);
        System.out.println("advancedFile_file_1 exists: " + advancedFile_file_1.exists());
        System.out.println("advancedFile_file_1 readBytes: " + new String(advancedFile_file_1.readBytesWithoutException()));
        System.out.println();
        System.out.println();
        System.out.println();
        final File file_2 = new File("test/1/2/3.zip");
        final AdvancedFile advancedFile_file_2 = new AdvancedFile(new AdvancedFile(file_2), "3.1");
        System.out.println("advancedFile_file_2: " + advancedFile_file_2);
        System.out.println("advancedFile_file_2 exists: " + advancedFile_file_2.exists());
        System.out.println("advancedFile_file_2 listFiles: " + advancedFile_file_2.listFilesWithoutException(true));
    }
    
    private static void testJar() {
        final AdvancedFile jarFile = new AdvancedFile("test", "DummyMod.jar");
        System.out.println("jarFile=\"" + jarFile + "\"");
        System.out.println();
        System.out.println("jarFile.mayListFiles()=" + jarFile.mayListFiles());
        System.out.println("jarFile.isDirectory()=" + jarFile.isDirectory());
        System.out.println();
        System.out.println("jarFile.listFiles(true)=" + jarFile.listFiles(true));
        System.out.println();
        System.out.println("jarFile.listFiles(false)=" + jarFile.listFiles(false));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        final AdvancedFile folder_in_jarFile = new AdvancedFile(jarFile, "res");
        System.out.println("folder_in_jarFile=" + folder_in_jarFile);
        System.out.println();
        System.out.println("folder_in_jarFile.mayListFiles()=" + folder_in_jarFile.mayListFiles());
        System.out.println("folder_in_jarFile.isDirectory()=" + folder_in_jarFile.isDirectory());
        System.out.println();
        System.out.println("folder_in_jarFile.listFiles(true)=" + folder_in_jarFile.listFiles(true));
        System.out.println();
        System.out.println("folder_in_jarFile.listFiles(false)=" + folder_in_jarFile.listFiles(false));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        if (true) {
            final AdvancedFile file_in_jarFile = new AdvancedFile(folder_in_jarFile, "planets_1.png");
            System.out.println("file_in_jarFile=\"" + file_in_jarFile + "\"");
            System.out.println();
            System.out.println("file_in_jarFile.isFile()=" + file_in_jarFile.isFile());
            System.out.println();
            System.out.println("file_in_jarFile.readBytesWithoutException()=" + new String(file_in_jarFile.readBytesWithoutException()).hashCode());
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }
    
}
