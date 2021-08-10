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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.StringReader;

public class XMLUtilTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static void main(String[] args) throws Exception {
        if (false) {
            logger.debug("XMLUtil.DOCUMENT_BUILDER=" + XMLUtil.DOCUMENT_BUILDER);
            logger.info("args[0]=" + args[0]);
            final Document document = XMLUtil.parseToMemory(new InputSource(new StringReader(args[0])));
            logger.info("document=" + document);
            final Element root = document.getDocumentElement();
            logger.info("root=" + root);
            final String test_tagName = root.getTagName();
            logger.info("test_tagName=" + test_tagName);
            final String test_attributeName = root.getAttribute("name");
            logger.info("test_attributeName=" + test_attributeName);
            final String test_textContent = root.getTextContent();
            logger.info("test_textContent=" + test_textContent);
        }
        /*
        final AdvancedFile langFile = new AdvancedFile(LanguageUtil.LANG_FOLDER, "de-DE.xml");
        logger.info("langFile=" + langFile);
        logger.info("langFile.exists()=" + langFile.exists());
        final XMLLocalizer xmlLocalizer = new XMLLocalizer(langFile);
        logger.info("xmlLocalizer=" + xmlLocalizer);
        logger.info("xmlLocalizer.getLanguageTag()=" + xmlLocalizer.getLanguageTag());
        logger.info("xmlLocalizer.getLanguageNameEnglish()=" + xmlLocalizer.getLanguageNameEnglish());
        logger.info("xmlLocalizer.getLanguageNameLocal()=" + xmlLocalizer.getLanguageNameLocal());
        logger.info("xmlLocalizer=" + xmlLocalizer);
        logger.info("xmlLocalizer.localize(\"test_sentence\")=" + xmlLocalizer.localize("test_sentence"));
        */
    }
    
}
