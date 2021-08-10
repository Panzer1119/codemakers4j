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

package de.codemakers.io.monitor;

import de.codemakers.base.Standard;
import de.codemakers.io.file.AdvancedFile;
import de.codemakers.io.listeners.AdvancedFileChangeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancedFileMonitorTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        final AdvancedFile root = new AdvancedFile(args[0]);
        logger.info("root=" + root);
        final AdvancedFileMonitor advancedFileMonitor = new AdvancedFileMonitor(root);
        logger.info("advancedFileMonitor=" + advancedFileMonitor);
        advancedFileMonitor.addAdvancedFileChangeListeners(new AdvancedFileChangeListener() {
            @Override
            public void onFileCreated(AdvancedFile file) {
                logger.debug("onFileCreated:" + file);
            }
            
            @Override
            public void onFileModified(AdvancedFile file) {
                logger.debug("onFileModified:" + file);
            }
            
            @Override
            public void onFileDeleted(AdvancedFile file) {
                logger.debug("onFileDeleted:" + file);
            }
            
            @Override
            public void onFileRenamed(AdvancedFile file) {
                logger.debug("onFileRenamed:" + file);
            }
            
            @Override
            public void onDirectoryCreated(AdvancedFile directory) {
                logger.debug("onDirectoryCreated:" + directory);
            }
            
            @Override
            public void onDirectoryModified(AdvancedFile directory) {
                logger.debug("onDirectoryModified:" + directory);
            }
            
            @Override
            public void onDirectoryDeleted(AdvancedFile directory) {
                logger.debug("onDirectoryDeleted:" + directory);
            }
            
            @Override
            public void onDirectoryRenamed(AdvancedFile directory) {
                logger.debug("onDirectoryRenamed:" + directory);
            }
        });
        logger.info("advancedFileMonitor=" + advancedFileMonitor);
        advancedFileMonitor.start();
        logger.info("advancedFileMonitor=" + advancedFileMonitor);
        Standard.addShutdownHook(advancedFileMonitor::stop);
        Standard.async(() -> {
            Thread.sleep(20000);
            logger.info("Exiting");
            System.exit(0);
        });
    }
    
}
