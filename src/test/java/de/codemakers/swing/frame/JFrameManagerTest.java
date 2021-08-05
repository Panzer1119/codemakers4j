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

package de.codemakers.swing.frame;

import de.codemakers.base.Standard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class JFrameManagerTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        final JFrameManager frameManager = new JFrameManager("Test Name", "0.0");
        logger.info("frameManager=" + frameManager);
        frameManager.setDefaultSettings();
        frameManager.setPreferredSize(new Dimension(600, 300));
        frameManager.show(null);
        frameManager.addPrefix("Prefix");
        frameManager.addSuffix("Suffix");
        Standard.async(() -> {
            Thread.sleep(2000);
            frameManager.removePrefix("Prefix");
        });
    }
    
}
