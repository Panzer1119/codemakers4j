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

public interface Verifiable {
    
    Logger logger = LogManager.getLogger();
    
    boolean verify(Verifier verifier) throws Exception;
    
    default boolean verify(Verifier verifier, ToughConsumer<Throwable> failure) {
        try {
            return verify(verifier);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    default boolean verifyWithoutException(Verifier verifier) {
        return verify(verifier, (ToughConsumer<Throwable>) null);
    }
    
    default ReturningAction<Boolean> verifyAction(Verifier verifier) {
        return new ReturningAction<>(() -> verify(verifier));
    }
    
    boolean verify(Verifier verifier, byte[] data_signature) throws Exception;
    
    default boolean verify(Verifier verifier, byte[] data_signature, ToughConsumer<Throwable> failure) {
        try {
            return verify(verifier, data_signature);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    default boolean verifyWithoutException(Verifier verifier, byte[] data_signature) {
        return verify(verifier, data_signature, null);
    }
    
    default ReturningAction<Boolean> verifyAction(Verifier verifier, byte[] data_signature) {
        return new ReturningAction<>(() -> verify(verifier, data_signature));
    }
    
    Verifiable verifyThis(Verifier verifier) throws Exception;
    
    default Verifiable verifyThis(Verifier verifier, ToughConsumer<Throwable> failure) {
        try {
            return verifyThis(verifier);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return this;
        }
    }
    
    default Verifiable verifyThisWithoutException(Verifier verifier) {
        return verifyThis(verifier, (ToughConsumer<Throwable>) null);
    }
    
    default ReturningAction<Verifiable> verifyThisAction(Verifier verifier) {
        return new ReturningAction<>(() -> verifyThis(verifier));
    }
    
    Verifiable verifyThis(Verifier verifier, byte[] signature) throws Exception;
    
    default Verifiable verifyThis(Verifier verifier, byte[] signature, ToughConsumer<Throwable> failure) {
        try {
            return verifyThis(verifier, signature);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return this;
        }
    }
    
    default Verifiable verifyThisWithoutException(Verifier verifier, byte[] signature) {
        return verifyThis(verifier, signature, null);
    }
    
    default ReturningAction<Verifiable> verifyThisAction(Verifier verifier, byte[] data_signature) {
        return new ReturningAction<>(() -> verifyThis(verifier, data_signature));
    }
    
}
