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

package de.codemakers.base.entities.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.codemakers.base.util.Require;
import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.io.SerializationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

public class KeepingIncrementalData extends IncrementalData {
    
    private static final Logger logger = LogManager.getLogger(KeepingIncrementalData.class);
    
    private final BiMap<Long, DeltaData> deltaDatas = HashBiMap.create();
    
    public KeepingIncrementalData(byte[] data) {
        super(data);
    }
    
    public long getVersion(DeltaData deltaData) {
        return deltaDatas.inverse().get(deltaData);
    }
    
    public DeltaData getDeltaData(long version) {
        return deltaDatas.get(version);
    }
    
    public Collection<DeltaData> getDeltaDatas() {
        return deltaDatas.values();
    }
    
    @Override
    public DeltaData changeData(byte[] data_new) {
        final DeltaData deltaData = super.changeData(data_new);
        if (deltaData != null) {
            deltaDatas.put(deltaData.getVersion(), deltaData);
        }
        return deltaData;
    }
    
    public KeepingIncrementalData decrement(long steps) {
        if (steps == 0) {
            return this;
        } else if (steps < 0) {
            return increment(steps);
        }
        for (long l = 0; l < steps; l++) {
            decrement();
        }
        return this;
    }
    
    public KeepingIncrementalData decrement() {
        if (deltaDatas.isEmpty()) {
            throw new NoSuchElementException("This is already the first Version");
        }
        DeltaData deltaData = null;
        while (deltaData == null) {
            if (version.get() == Long.MIN_VALUE) {
                break;
            }
            deltaData = deltaDatas.get(version.decrementAndGet());
        }
        if (deltaData == null) {
            throw new NoSuchElementException("This is already the first Version");
        }
        incrementData(deltaData);
        return this;
    }
    
    public KeepingIncrementalData increment(long steps) {
        if (steps == 0) {
            return this;
        } else if (steps < 0) {
            return decrement(steps);
        }
        for (long l = 0; l < steps; l++) {
            increment();
        }
        return this;
    }
    
    public KeepingIncrementalData increment() {
        if (deltaDatas.isEmpty()) {
            throw new NoSuchElementException("This is already the latest Version");
        }
        DeltaData deltaData = null;
        while (deltaData == null) {
            if (version.get() == Long.MAX_VALUE) {
                break;
            }
            deltaData = deltaDatas.get(version.incrementAndGet());
        }
        if (deltaData == null) {
            throw new NoSuchElementException("This is already the latest Version");
        }
        incrementData(deltaData);
        return this;
    }
    
    public KeepingIncrementalData firstVersion() {
        if (deltaDatas.isEmpty()) {
            return this;
        }
        final Long version_min = deltaDatas.keySet().stream().min(Long::compareTo).orElse(null);
        if (version_min == getVersion()) {
            return this;
        } else if (version_min < getVersion()) {
            decrement(getVersion() - version_min);
        } else {
            throw new UnsupportedOperationException("The minimum Version can not be greater then the current version");
        }
        return this;
    }
    
    public KeepingIncrementalData latestVersion() {
        if (deltaDatas.isEmpty()) {
            return this;
        }
        final Long version_max = deltaDatas.keySet().stream().max(Long::compareTo).orElse(null);
        if (version_max == getVersion()) {
            return this;
        } else if (version_max > getVersion()) {
            increment(version_max - getVersion());
        } else {
            throw new UnsupportedOperationException("The maximum Version can not be smaller then the current version");
        }
        return this;
    }
    
    public KeepingIncrementalData selectVersion(long version) {
        return selectVersion(version, true);
    }
    
    public KeepingIncrementalData selectVersion(long version, boolean selectNextVersionIfNotExisting) {
        if (deltaDatas.isEmpty()) {
            throw new NoSuchElementException("There are no saved Versions");
        }
        if (version == getVersion()) {
            return this;
        } else {
            if (!selectNextVersionIfNotExisting && !deltaDatas.containsKey(version)) {
                throw new NoSuchElementException("There is no such Version called \"" + version + "\"");
            }
            if (version < getVersion()) {
                return decrement(getVersion() - version);
            } else {
                return increment(version - getVersion());
            }
        }
    }
    
    @Override
    public KeepingIncrementalData incrementData(DeltaData deltaData) {
        super.incrementData(deltaData);
        if (deltaData != null) {
            deltaDatas.put(deltaData.getVersion(), deltaData);
        }
        return this;
    }
    
    @Override
    public KeepingIncrementalData incrementData(DeltaData deltaData, boolean forceIncrement) {
        super.incrementData(deltaData, forceIncrement);
        if (deltaData != null) {
            deltaDatas.put(deltaData.getVersion(), deltaData);
        }
        return this;
    }
    
    @Override
    public KeepingIncrementalData setData(byte[] data) {
        super.setData(data);
        return this;
    }
    
    @Override
    public KeepingIncrementalData copy() {
        final KeepingIncrementalData keepingIncrementalData = new KeepingIncrementalData(getData());
        keepingIncrementalData.deltaDatas.putAll(deltaDatas);
        return keepingIncrementalData;
    }
    
    @Override
    public void set(Copyable copyable) {
        final KeepingIncrementalData keepingIncrementalData = Require.clazz(copyable, KeepingIncrementalData.class);
        if (keepingIncrementalData != null) {
            setData(keepingIncrementalData.getData());
            deltaDatas.clear();
            deltaDatas.putAll(keepingIncrementalData.deltaDatas);
        }
    }
    
    @Override
    public Optional<byte[]> toBytes() {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeLong(version.get());
            dataOutputStream.writeInt(arrayLength(data));
            if (data != null) {
                dataOutputStream.write(data);
            }
            if (!deltaDatas.isEmpty()) {
                deltaDatas.values().forEach((deltaData) -> {
                    try {
                        final Optional<byte[]> optional = SerializationUtil.objectToBytes(deltaData);
                        if (optional.isEmpty()) {
                            logger.warn("Could not serialize deltaData");
                            return;
                        }
                        final byte[] data = optional.get();
                        dataOutputStream.writeInt(data.length);
                        dataOutputStream.write(data);
                    } catch (IOException e) {
                        logger.error("Error while writing deltaData to dataOutputStream", e);
                    }
                });
            }
            dataOutputStream.flush();
            return Optional.of(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            logger.error("Error while converting KeepingIncrementalData to bytes", e);
            return Optional.empty();
        }
    }
    
    @Override
    public boolean fromBytes(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        this.version.set(byteBuffer.getLong());
        int temp = byteBuffer.getInt();
        if (temp >= 0) {
            this.data = new byte[temp];
            byteBuffer.get(this.data);
        } else {
            this.data = null;
        }
        deltaDatas.clear();
        boolean errored = false;
        while (byteBuffer.remaining() > 0) {
            try {
                temp = byteBuffer.getInt();
                final byte[] bytes_ = new byte[temp];
                byteBuffer.get(bytes_);
                final Optional<Serializable> optional = SerializationUtil.bytesToObject(bytes_);
                if (optional.isEmpty()) {
                    errored = false;
                    logger.warn("Could not deserialize bytes_");
                    break;
                }
                final DeltaData deltaData = Require.clazz(optional.get(), DeltaData.class);
                deltaDatas.put(deltaData.getVersion(), deltaData);
            } catch (Exception e) {
                errored = true;
                logger.error("Error while deserializing deltaData", e);
            }
        }
        return !errored;
    }
    
    @Override
    public String toString() {
        return "KeepingIncrementalData{" + "deltaDatas=" + deltaDatas + ", version=" + version + ", data=" + Arrays.toString(data) + '}';
    }
    
}
