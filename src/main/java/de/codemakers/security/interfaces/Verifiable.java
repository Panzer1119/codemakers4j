/*
 *     Copyright 2018 Paul Hagedorn (Panzer1119)
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

package de.codemakers.security.interfaces;

import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.logger.Logger;
import de.codemakers.base.util.tough.ToughConsumer;

public interface Verifiable {
    
    default boolean verify(Verifier verifier) throws Exception {
        return verify(verifier, (byte[]) null);
    }
    
    boolean verify(Verifier verifier, byte[] data_signature) throws Exception;
    
    default boolean verify(Verifier verifier, ToughConsumer<Throwable> failure) {
        return verify(verifier, null, failure);
    }
    
    default boolean verify(Verifier verifier, byte[] data_signature, ToughConsumer<Throwable> failure) {
        try {
            return verify(verifier, data_signature);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                Logger.handleError(ex);
            }
            return false;
        }
    }
    
    default boolean verifyWithoutException(Verifier verifier) {
        return verifyWithoutException(verifier, null);
    }
    
    default boolean verifyWithoutException(Verifier verifier, byte[] data_signature) {
        return verify(verifier, data_signature, null);
    }
    
    default ReturningAction<Boolean> verifyAction(Verifier verifier) {
        return verifyAction(verifier, null);
    }
    
    default ReturningAction<Boolean> verifyAction(Verifier verifier, byte[] data_signature) {
        return new ReturningAction<>(() -> verify(verifier, data_signature));
    }
    
    default Verifiable verifyThis(Verifier verifier) throws Exception {
        return verifyThis(verifier, (byte[]) null);
    }
    
    Verifiable verifyThis(Verifier verifier, byte[] signature) throws Exception;
    
    default Verifiable verifyThis(Verifier verifier, ToughConsumer<Throwable> failure) {
        return verifyThis(verifier, null, failure);
    }
    
    default Verifiable verifyThis(Verifier verifier, byte[] signature, ToughConsumer<Throwable> failure) {
        try {
            return verifyThis(verifier, signature);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                Logger.handleError(ex);
            }
            return this;
        }
    }
    
    default Verifiable verifyThisWithoutException(Verifier verifier) {
        return verifyThisWithoutException(verifier, null);
    }
    
    default Verifiable verifyThisWithoutException(Verifier verifier, byte[] signature) {
        return verifyThis(verifier, signature, null);
    }
    
    default ReturningAction<Verifiable> verifyThisAction(Verifier verifier) {
        return verifyThisAction(verifier, null);
    }
    
    default ReturningAction<Verifiable> verifyThisAction(Verifier verifier, byte[] data_signature) {
        return new ReturningAction<>(() -> verifyThis(verifier, data_signature));
    }
    
}
