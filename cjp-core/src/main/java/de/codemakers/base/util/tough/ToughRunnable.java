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

package de.codemakers.base.util.tough;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FunctionalInterface
public interface ToughRunnable extends Tough<Void, Void> {
    
    Logger logger = LogManager.getLogger();
    
    void run() throws Exception;
    
    default void run(ToughConsumer<Throwable> failure) {
        try {
            run();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
        }
    }
    
    default void runWithoutException() {
        run(null);
    }
    
    @Override
    default Void action(Void v) throws Exception {
        run();
        return null;
    }
    
    @Override
    default boolean canConsume() {
        return false;
    }
    
    @Override
    default boolean canSupply() {
        return false;
    }
    
}
