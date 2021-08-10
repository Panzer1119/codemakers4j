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

package de.codemakers.base.util.version;


import de.codemakers.base.Standard;

import java.util.Objects;

public class StringVersionPart extends AbstractVersionPart {
    
    protected final String versionPart;
    protected final Integer parsed;
    
    public StringVersionPart(int priority, String versionPart) {
        super(priority);
        this.versionPart = Objects.requireNonNull(versionPart);
        this.parsed = Standard.silentError(() -> Integer.parseInt(versionPart));
    }
    
    @Override
    public boolean isThisHigherThanThat(VersionPart that) {
        if (isThisPriorityLowerThanThat(that)) {
            return false;
        } else if (isThisPriorityHigherThanThat(that)) {
            return true;
        }
        if (isDifferentType(that)) {
            return false; //TODO What should we return if this and that are different Types of VersionParts?
        }
        return getAsString().compareTo(that.getAsString()) > 0;
    }
    
    @Override
    public boolean isThisEqualToThat(VersionPart that) {
        if (!isThisPriorityEqualToThat(that)) {
            return false;
        }
        if (isDifferentType(that)) {
            return true; //TODO What should we return if this and that are different Types of VersionParts?
        }
        return getAsString().equals(that.getAsString());
    }
    
    @Override
    public boolean isThisLowerThanThat(VersionPart that) {
        if (isThisPriorityHigherThanThat(that)) {
            return false;
        } else if (isThisPriorityLowerThanThat(that)) {
            return true;
        }
        if (isDifferentType(that)) {
            return false; //TODO What should we return if this and that are different Types of VersionParts?
        }
        return getAsString().compareTo(that.getAsString()) < 0;
    }
    
    @Override
    public boolean isSameType(VersionPart that) {
        return that instanceof StringVersionPart;
    }
    
    @Override
    public Integer getAsInteger() {
        return parsed;
    }
    
    @Override
    public String getAsString() {
        return versionPart;
    }
    
    @Override
    public String getCompleteString(String delimiter) {
        return getAsString();
    }
    
    @Override
    public int compareTo(VersionPart that) {
        if (!isThisPriorityEqualToThat(that)) {
            return Integer.compare(getPriority(), that.getPriority());
        }
        if (isDifferentType(that)) {
            return 0; //TODO What should we return if this and that are different Types of VersionParts?
        }
        return getAsString().compareTo(that.getAsString());
    }
    
    @Override
    public String toString() {
        return "StringVersionPart{" + "priority=" + priority + ", versionPart='" + versionPart + '\'' + '}';
    }
    
}
