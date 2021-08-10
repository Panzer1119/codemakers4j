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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Version implements ComparableVersion<Version> {
    
    protected final VersionPart[] versionParts;
    
    public Version(Collection<VersionPart> versionParts) {
        this(versionParts.toArray(new VersionPart[0]));
    }
    
    public Version(VersionPart... versionParts) {
        this.versionParts = versionParts;
    }
    
    public VersionPart[] getVersionParts() {
        return versionParts;
    }
    
    public List<VersionPart> getVersionPartsAsList() {
        return Arrays.asList(versionParts);
    }
    
    public VersionPart getVersionPart(int priority) {
        return versionParts[priority];
    }
    
    public Version setVersionPart(VersionPart versionPart) {
        return setVersionPart(versionPart, versionPart.getPriority());
    }
    
    public Version setVersionPart(VersionPart versionPart, int priority) {
        versionParts[priority] = versionPart;
        return this;
    }
    
    public int getLength() {
        return versionParts.length;
    }
    
    @Override
    public boolean isThisHigherThanThat(Version that) {
        final int length_min = Math.min(getLength(), that.getLength());
        for (int priority = 0; priority < length_min; priority++) {
            if (getVersionPart(priority).isThisHigherThanThat(that.getVersionPart(priority))) {
                return true;
            }
        }
        return getLength() > that.getLength();
    }
    
    @Override
    public boolean isThisEqualToThat(Version that) {
        if (getLength() != that.getLength()) {
            return false;
        }
        for (int priority = 0; priority < getLength(); priority++) {
            if (!getVersionPart(priority).isThisEqualToThat(that.getVersionPart(priority))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean isThisLowerThanThat(Version that) {
        final int length_min = Math.min(getLength(), that.getLength());
        for (int priority = 0; priority < length_min; priority++) {
            if (getVersionPart(priority).isThisLowerThanThat(that.getVersionPart(priority))) {
                return true;
            }
        }
        return getLength() < that.getLength();
    }
    
    @Override
    public String getCompleteString(String delimiter) {
        return getVersionPartsAsList().stream().map(ComparableVersion::getCompleteString).collect(Collectors.joining(delimiter));
    }
    
    @Override
    public int compareTo(Version that) {
        if (isThisEqualToThat(that)) {
            return 0;
        }
        return isThisHigherThanThat(that) ? 1 : -1;
    }
    
    @Override
    public String toString() {
        return "Version{" + "versionParts=" + Arrays.toString(versionParts) + '}';
    }
    
}
