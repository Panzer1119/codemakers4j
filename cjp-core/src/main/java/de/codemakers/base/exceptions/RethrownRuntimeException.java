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

package de.codemakers.base.exceptions;

public class RethrownRuntimeException extends CJPRuntimeException {
    
    public RethrownRuntimeException() {
    }
    
    public RethrownRuntimeException(String message) {
        super(message);
    }
    
    public RethrownRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public RethrownRuntimeException(Throwable cause) {
        super(cause);
    }
    
    public RethrownRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
