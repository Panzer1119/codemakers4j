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

public class IntegerVersionPart extends AbstractVersionPart {
    
    protected final int versionPart;
    
    public IntegerVersionPart(int priority, int versionPart) {
        super(priority);
        this.versionPart = versionPart;
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
        return getAsInteger() > that.getAsInteger();
    }
    
    @Override
    public boolean isThisEqualToThat(VersionPart that) {
        if (!isThisPriorityEqualToThat(that)) {
            return false;
        }
        if (isDifferentType(that)) {
            return true; //TODO What should we return if this and that are different Types of VersionParts?
        }
        return getAsInteger().equals(that.getAsInteger());
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
        return getAsInteger() < that.getAsInteger();
    }
    
    @Override
    public boolean isSameType(VersionPart that) {
        return that instanceof IntegerVersionPart;
    }
    
    @Override
    public Integer getAsInteger() {
        return versionPart;
    }
    
    @Override
    public String getAsString() {
        return "" + versionPart;
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
        return getAsInteger().compareTo(that.getAsInteger());
    }
    
    @Override
    public String toString() {
        return "IntegerVersionPart{" + "priority=" + priority + ", versionPart=" + versionPart + '}';
    }
    
}
