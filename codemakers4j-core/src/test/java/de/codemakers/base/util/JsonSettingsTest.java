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

package de.codemakers.base.util;

import com.google.gson.JsonObject;
import de.codemakers.io.file.AdvancedFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class JsonSettingsTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static void main(String[] args) {
        final AdvancedFile advancedFile = new AdvancedFile(args[0]);
        logger.debug("advancedFile=" + advancedFile);
        logger.debug("advancedFile.exists()=" + advancedFile.exists());
        final JsonSettings jsonSettings_1 = new JsonSettings(advancedFile);
        logger.debug("jsonSettings_1=" + jsonSettings_1);
        jsonSettings_1.setProperty("debug", true);
        jsonSettings_1.setProperty("port", 1234);
        jsonSettings_1.setProperty("inetAddress", "127.0.0.1");
        logger.debug("jsonSettings_1=" + jsonSettings_1);
        logger.debug("jsonSettings_1.saveSettings()=" + jsonSettings_1.saveSettings());
        logger.debug("jsonSettings_1=" + jsonSettings_1);
        final JsonSettings jsonSettings_1_1 = jsonSettings_1.getOrCreateSubSettings("subSettings_1");
        logger.debug("jsonSettings_1_1=" + jsonSettings_1_1);
        jsonSettings_1_1.setProperty("subKey", "subValue");
        logger.debug("jsonSettings_1_1=" + jsonSettings_1_1);
        logger.debug("jsonSettings_1_1.saveSettings()=" + jsonSettings_1_1.saveSettings());
        logger.debug("jsonSettings_1_1=" + jsonSettings_1_1);
        logger.debug("jsonSettings_1=" + jsonSettings_1);
        //
        List<String> arguments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arguments.add("argument_" + i);
        }
        logger.debug("arguments=" + arguments);
        jsonSettings_1.setPropertyList("arguments", arguments);
        logger.debug("jsonSettings_1=" + jsonSettings_1);
        jsonSettings_1.saveSettings();
        arguments = jsonSettings_1.getPropertyListStrings("arguments");
        logger.debug("arguments=" + arguments);
        //
        List<JsonObject> objects_1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", "object_" + i);
            jsonObject.addProperty("random", Math.random());
            objects_1.add(jsonObject);
        }
        logger.debug("objects_1=" + objects_1);
        jsonSettings_1.setPropertyList("objects_1", objects_1);
        logger.debug("jsonSettings_1=" + jsonSettings_1);
        jsonSettings_1.saveSettings();
        objects_1 = jsonSettings_1.getPropertyListJsonObjects("objects_1");
        logger.debug("objects_1=" + objects_1);
    }
    
}
