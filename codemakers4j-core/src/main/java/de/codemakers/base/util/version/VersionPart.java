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

public interface VersionPart extends ComparableVersion<VersionPart> {
    
    int getPriority();
    
    default boolean isThisPriorityHigherThanThat(VersionPart that) {
        if (that == null) {
            return false;
        }
        return getPriority() < that.getPriority();
    }
    
    default boolean isThisPriorityHigherOrEqualToThat(VersionPart that) {
        if (isThisPriorityHigherThanThat(that)) {
            return true;
        }
        return isThisPriorityEqualToThat(that);
    }
    
    default boolean isThisPriorityEqualToThat(VersionPart that) {
        if (that == null) {
            return false;
        }
        return getPriority() == that.getPriority();
    }
    
    default boolean isThisPriorityLowerOrEqualToThat(VersionPart that) {
        if (isThisPriorityLowerThanThat(that)) {
            return true;
        }
        return isThisPriorityEqualToThat(that);
    }
    
    default boolean isThisPriorityLowerThanThat(VersionPart that) {
        if (that == null) {
            return false;
        }
        return getPriority() > that.getPriority();
    }
    
    boolean isSameType(VersionPart that);
    
    default boolean isDifferentType(VersionPart that) {
        return !isSameType(that);
    }
    
    Integer getAsInteger();
    
    String getAsString();
    
}
