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

package de.codemakers.base.entities;

import de.codemakers.base.util.Require;
import de.codemakers.base.util.interfaces.Copyable;

import java.util.Objects;

public class IncrementalData extends Data {
    
    public IncrementalData(byte[] data) {
        super(data);
    }
    
    public DataDelta changeData(byte[] data) {
        final byte[] data_old = getData();
        setData(data);
        return new DataDelta(data_old, data);
    }
    
    public IncrementalData incrementData(DataDelta dataDelta) {
        Objects.requireNonNull(dataDelta);
        if (dataDelta.getLength() < 0 || getLength() < 0) {
            setData(null);
        } else {
            setData(dataDelta.getData(getData()));
        }
        return this;
    }
    
    public int getLength() {
        return data == null ? -1 : data.length;
    }
    
    @Override
    public IncrementalData setData(byte[] data) {
        super.setData(data);
        return this;
    }
    
    @Override
    public IncrementalData copy() {
        return new IncrementalData(getData());
    }
    
    @Override
    public void set(Copyable copyable) {
        final IncrementalData incrementalData = Require.clazz(copyable, IncrementalData.class);
        if (incrementalData != null) {
            setData(incrementalData.getData());
        }
    }
    
}
