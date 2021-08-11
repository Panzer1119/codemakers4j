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

import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.os.functions.OSFunction;
import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SpeakText extends OSFunction {
    
    private static final Logger logger = LogManager.getLogger();

    public abstract boolean speak(String text) throws Exception;
    
    public boolean speak(String text, ToughConsumer<Throwable> failure) {
        try {
            return speak(text);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    public boolean speakWithoutException(String text) {
        return speak(text, null);
    }
    
    public ReturningAction<Boolean> speakAction(String text) {
        return new ReturningAction<>(() -> speak(text));
    }

}
