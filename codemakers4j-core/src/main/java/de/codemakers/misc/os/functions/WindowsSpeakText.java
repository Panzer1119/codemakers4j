/*
 *    Copyright 2018 - 2021 Paul Hagedorn (Panzer1119)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.codemakers.misc.os.functions;

import de.codemakers.base.os.OS;
import de.codemakers.base.os.functions.RegisterOSFunction;
import de.codemakers.io.file.AdvancedFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;

@RegisterOSFunction(supported = {OS.WINDOWS})
public class WindowsSpeakText extends SpeakText {
    
    private static final Logger logger = LogManager.getLogger();
    
    protected static final String FORMAT = "dim fname\nset voice=createobject(\"sapi.spvoice\")\nvoice.speak(\"%s\")";
    
    @Override
    public boolean speak(String text) throws Exception {
        if (!Desktop.isDesktopSupported()) {
            logger.warn("Desktop is not supported");
            return false;
        }
        final String temp = String.format(FORMAT, text);
        final AdvancedFile advancedFile = new AdvancedFile(File.createTempFile("" + ((int) (Math.random() * 10000000)), ".vbs"));
        advancedFile.writeBytes(temp.getBytes());
        Desktop.getDesktop().open(advancedFile.toFile());
        Thread.sleep(100);
        advancedFile.delete();
        return !advancedFile.exists();
    }
    
}
