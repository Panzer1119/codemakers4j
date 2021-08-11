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

package de.codemakers.lang;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

public class LangTest {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final void main(String[] args) throws Exception {
        if (false) {
            test();
            return;
        }
        final Locale locale = Locale.getDefault();
        logger.info("locale=" + locale);
        logger.info("locale.getCountry()=" + locale.getCountry());
        logger.info("locale.getDisplayCountry()=" + locale.getDisplayCountry());
        logger.info("locale.getDisplayLanguage()=" + locale.getDisplayLanguage());
        logger.info("locale.getDisplayName()=" + locale.getDisplayName());
        logger.info("locale.getDisplayScript()=" + locale.getDisplayScript());
        logger.info("locale.getDisplayVariant()=" + locale.getDisplayVariant());
        logger.info("locale.getISO3Country()=" + locale.getISO3Country());
        logger.info("locale.getISO3Language()=" + locale.getISO3Language());
        logger.info("locale.getLanguage()=" + locale.getLanguage());
        logger.info("locale.getVariant()=" + locale.getVariant());
        logger.info("locale.toLanguageTag()=" + locale.toLanguageTag());
        logger.info(Locale.forLanguageTag("en"));
        /*
        final AdvancedLocalizer advancedLocalizer = LanguageUtil.getLocalizerDefault();
        logger.info("advancedLocalizer=" + advancedLocalizer);
        //logger.info("advancedLocalizer.localize(Localizer.KEY_LANGUAGE_NAME_ENGLISH)=" + advancedLocalizer.localize(Localizer.KEY_LANGUAGE_NAME_ENGLISH));
        logger.info("Standard.localize(\"test_123\")=" + Standard.localize("test_123"));
        logger.info("Standard.localize(\"test_123\", null)=" + Standard.localize("test_123", null));
        logger.info("Standard.localize(\"test_123\")=" + Standard.localize("test_1234"));
        //test();
        */
    }
    
    private static void test() {
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                logger.info("locale=" + locale);
                logger.info("locale.getCountry()=" + locale.getCountry());
                logger.info("locale.getDisplayCountry()=" + locale.getDisplayCountry());
                logger.info("locale.getDisplayLanguage()=" + locale.getDisplayLanguage());
                logger.info("locale.getDisplayName()=" + locale.getDisplayName());
                logger.info("locale.getDisplayScript()=" + locale.getDisplayScript());
                logger.info("locale.getDisplayVariant()=" + locale.getDisplayVariant());
                logger.info("locale.getISO3Country()=" + locale.getISO3Country());
                logger.info("locale.getISO3Language()=" + locale.getISO3Language());
                logger.info("locale.getLanguage()=" + locale.getLanguage());
                logger.info("locale.getVariant()=" + locale.getVariant());
                logger.info("locale.toLanguageTag()=" + locale.toLanguageTag());
                logger.info("\n\n");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
