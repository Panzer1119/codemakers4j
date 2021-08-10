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

package de.codemakers.io.streams.exceptions;

import de.codemakers.base.exceptions.ICJPException;

import java.io.IOException;

public class StreamClosedException extends IOException implements ICJPException {
    
    protected final boolean friendly;
    
    public StreamClosedException() {
        this.friendly = false;
    }
    
    public StreamClosedException(String message) {
        super(message);
        this.friendly = false;
    }
    
    public StreamClosedException(String message, Throwable cause) {
        super(message, cause);
        this.friendly = false;
    }
    
    public StreamClosedException(Throwable cause) {
        super(cause);
        this.friendly = false;
    }
    
    public StreamClosedException(boolean friendly) {
        this.friendly = friendly;
    }
    
    public StreamClosedException(String message, boolean friendly) {
        super(message);
        this.friendly = friendly;
    }
    
    public StreamClosedException(String message, Throwable cause, boolean friendly) {
        super(message, cause);
        this.friendly = friendly;
    }
    
    public StreamClosedException(Throwable cause, boolean friendly) {
        super(cause);
        this.friendly = friendly;
    }
    
    public boolean isFriendly() {
        return friendly;
    }
    
}
