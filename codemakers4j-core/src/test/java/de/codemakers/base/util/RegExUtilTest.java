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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegExUtilTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static void main(String[] args) {
        final String text = "This is a text s$archFor.this! haha";
        logger.info("text=" + text);
        final String textToSearchFor = "s$archFor.this!";
        logger.info("textToSearchFor=" + textToSearchFor);
        final String replacement = "I replace the old Text (hehe)!";
        logger.info("replacement=" + replacement);
        final String result_1 = text.replaceAll(textToSearchFor, replacement);
        logger.info("result_1=" + result_1);
        final String regEx = RegExUtil.escapeToRegEx(textToSearchFor);
        logger.info("regEx=" + regEx);
        final String result_2 = text.replaceAll(regEx, replacement);
        logger.info("result_2=" + result_2);
    }
    
}
