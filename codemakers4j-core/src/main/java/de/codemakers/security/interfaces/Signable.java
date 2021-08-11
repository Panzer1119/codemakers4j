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

package de.codemakers.security.interfaces;

import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Signable {
    
    Logger logger = LogManager.getLogger();
    
    byte[] sign(Signer signer) throws Exception;
    
    default byte[] sign(Signer signer, ToughConsumer<Throwable> failure) {
        try {
            return sign(signer);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] signWithoutException(Signer signer) {
        return sign(signer, null);
    }
    
    default ReturningAction<byte[]> signAction(Signer signer) {
        return new ReturningAction<>(() -> sign(signer));
    }
    
    Signable signThis(Signer signer) throws Exception;
    
    default Signable signThis(Signer signer, ToughConsumer<Throwable> failure) {
        try {
            return signThis(signer);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return this;
        }
    }
    
    default Signable signThisWithoutException(Signer signer) {
        return signThis(signer, null);
    }
    
    default ReturningAction<Signable> signThisAction(Signer signer) {
        return new ReturningAction<>(() -> signThis(signer));
    }
    
}
