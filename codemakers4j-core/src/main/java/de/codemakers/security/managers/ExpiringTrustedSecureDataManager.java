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

package de.codemakers.security.managers;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import de.codemakers.base.util.tough.ToughPredicate;
import de.codemakers.base.util.tough.ToughSupplier;
import de.codemakers.security.entities.ExpiringTrustedSecureData;
import de.codemakers.security.interfaces.Decryptor;
import de.codemakers.security.interfaces.Verifier;
import de.codemakers.security.util.EasyCryptUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ExpiringTrustedSecureDataManager {
    
    private static final Logger logger = LogManager.getLogger();
    
    private final Multimap<Long, ExpiringTrustedSecureData> expiringTrustedSecureDatas = Multimaps.synchronizedMultimap(HashMultimap.create());
    protected ToughSupplier<Long> timeSupplier = null;
    protected long max_time_error = 0L;
    protected TimeUnit unit = null;
    private ToughPredicate<Long> timeTester = null;
    private long updatePeriod = 1000;
    private Timer timer = null;
    
    public ExpiringTrustedSecureDataManager(long max_time_error, TimeUnit unit) {
        this(() -> System.currentTimeMillis(), max_time_error, unit);
    }
    
    public ExpiringTrustedSecureDataManager(ToughSupplier<Long> timeSupplier, long max_time_error, TimeUnit unit) {
        Objects.requireNonNull(timeSupplier);
        this.timeSupplier = timeSupplier;
        this.max_time_error = max_time_error;
        this.unit = unit;
        createTimeTester();
    }
    
    public ToughSupplier<Long> getTimeSupplier() {
        return timeSupplier;
    }
    
    public ExpiringTrustedSecureDataManager setTimeSupplier(ToughSupplier<Long> timeSupplier) {
        this.timeSupplier = timeSupplier;
        return this;
    }
    
    public long getMaxTimeError() {
        return max_time_error;
    }
    
    public ExpiringTrustedSecureDataManager setMaxTimeError(long max_time_error) {
        this.max_time_error = max_time_error;
        createTimeTester();
        return this;
    }
    
    public final TimeUnit getUnit() {
        return unit;
    }
    
    public final ExpiringTrustedSecureDataManager setUnit(TimeUnit unit) {
        this.unit = unit;
        createTimeTester();
        return this;
    }
    
    public final ToughPredicate<Long> getTimeTester() {
        return timeTester;
    }
    
    void createTimeTester() {
        timeTester = EasyCryptUtil.createTimePredicateOfMaximumError(max_time_error, unit, timeSupplier);
    }
    
    public long getUpdatePeriod() {
        return updatePeriod;
    }
    
    public ExpiringTrustedSecureDataManager setUpdatePeriod(long updatePeriod) {
        this.updatePeriod = updatePeriod;
        return this;
    }
    
    public boolean isRunning() {
        return timer != null;
    }
    
    public boolean start() {
        if (isRunning()) {
            if (!stop()) {
                return false;
            }
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, updatePeriod);
        return true;
    }
    
    public boolean stop() {
        if (!isRunning()) {
            return false;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        return true;
    }
    
    public boolean clear() {
        if (isRunning()) {
            return false;
        }
        expiringTrustedSecureDatas.clear();
        return true;
    }
    
    protected void update() {
        try {
            expiringTrustedSecureDatas.keySet().removeIf((timestamp_) -> !timeTester.testWithoutException(timestamp_));
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
    public boolean acceptExpiringTrustedSecureData(ExpiringTrustedSecureData expiringTrustedSecureData) {
        return acceptExpiringTrustedSecureData(expiringTrustedSecureData, null, null, null);
    }
    
    public boolean acceptExpiringTrustedSecureData(ExpiringTrustedSecureData expiringTrustedSecureData, Decryptor decryptor) {
        return acceptExpiringTrustedSecureData(expiringTrustedSecureData, decryptor, null, null);
    }
    
    public boolean acceptExpiringTrustedSecureData(ExpiringTrustedSecureData expiringTrustedSecureData, Verifier verifier) {
        return acceptExpiringTrustedSecureData(expiringTrustedSecureData, null, verifier, verifier);
    }
    
    public boolean acceptExpiringTrustedSecureData(ExpiringTrustedSecureData expiringTrustedSecureData, Decryptor decryptor, Verifier verifier) {
        return acceptExpiringTrustedSecureData(expiringTrustedSecureData, decryptor, verifier, verifier);
    }
    
    public boolean acceptExpiringTrustedSecureData(ExpiringTrustedSecureData expiringTrustedSecureData, Verifier verifierForData, Verifier verifierForTimestamp) {
        return acceptExpiringTrustedSecureData(expiringTrustedSecureData, null, verifierForData, verifierForTimestamp);
    }
    
    public boolean acceptExpiringTrustedSecureData(ExpiringTrustedSecureData expiringTrustedSecureData, Decryptor decryptor, Verifier verifierForData, Verifier verifierForTimestamp) {
        if (expiringTrustedSecureData == null || expiringTrustedSecureData.getTimestamp() == null || (verifierForData != null && !expiringTrustedSecureData.verifyWithoutException(verifierForData))) {
            return false;
        }
        final long timestamp = expiringTrustedSecureData.getTimestampAsLong(decryptor);
        if (!timeTester.testWithoutException(timestamp)) {
            return false;
        }
        if (verifierForTimestamp != null && expiringTrustedSecureData.getTimestamp().verifyWithoutException(verifierForTimestamp)) {
            if (expiringTrustedSecureDatas.containsEntry(timestamp, expiringTrustedSecureData)) {
                return false;
            }
        } else if (verifierForTimestamp != null) {
            return false;
        }
        expiringTrustedSecureDatas.put(timestamp, expiringTrustedSecureData);
        return !expiringTrustedSecureData.isExpired(timeTester, decryptor, verifierForTimestamp);
    }
    
    @Override
    public String toString() {
        return "ExpiringTrustedSecureDataManager{" + "expiringTrustedSecureDatas=" + expiringTrustedSecureDatas + ", timeSupplier=" + timeSupplier + ", max_time_error=" + max_time_error + ", unit=" + unit + ", timeTester=" + timeTester + ", updatePeriod=" + updatePeriod + ", timer=" + timer + '}';
    }
    
}
