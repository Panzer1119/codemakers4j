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

package de.codemakers.security.entities;

import de.codemakers.base.util.ConvertUtil;
import de.codemakers.base.util.tough.ToughPredicate;
import de.codemakers.security.interfaces.Decryptor;
import de.codemakers.security.interfaces.Encryptor;
import de.codemakers.security.interfaces.Signer;
import de.codemakers.security.interfaces.Verifier;
import de.codemakers.security.util.EasyCryptUtil;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ExpiringTrustedSecureData extends TrustedSecureData {
    
    protected TrustedSecureData timestamp = null;
    
    public ExpiringTrustedSecureData(byte[] data) {
        super(data);
    }
    
    public ExpiringTrustedSecureData(byte[] data, TrustedSecureData timestamp) {
        super(data);
        this.timestamp = timestamp;
    }
    
    public ExpiringTrustedSecureData(byte[] data, Encryptor encryptor) {
        super(data, encryptor);
    }
    
    public ExpiringTrustedSecureData(byte[] data, Encryptor encryptor, TrustedSecureData timestamp) {
        super(data, encryptor);
        this.timestamp = timestamp;
    }
    
    public ExpiringTrustedSecureData(byte[] data, Decryptor decryptor) {
        super(data, decryptor);
    }
    
    public ExpiringTrustedSecureData(byte[] data, Decryptor decryptor, TrustedSecureData timestamp) {
        super(data, decryptor);
        this.timestamp = timestamp;
    }
    
    public ExpiringTrustedSecureData(byte[] data, Encryptor encryptor, Signer signer) {
        super(data, encryptor, signer);
    }
    
    public ExpiringTrustedSecureData(byte[] data, Encryptor encryptor, Signer signer, TrustedSecureData timestamp) {
        super(data, encryptor, signer);
        this.timestamp = timestamp;
    }
    
    public ExpiringTrustedSecureData(byte[] data, Decryptor decryptor, byte[] signature, Verifier verifier) {
        super(data, decryptor, signature, verifier);
    }
    
    public ExpiringTrustedSecureData(byte[] data, Decryptor decryptor, byte[] signature, Verifier verifier, TrustedSecureData timestamp) {
        super(data, decryptor, signature, verifier);
        this.timestamp = timestamp;
    }
    
    public ExpiringTrustedSecureData(byte[] data, Signer signer) {
        super(data, signer);
    }
    
    public ExpiringTrustedSecureData(byte[] data, Signer signer, TrustedSecureData timestamp) {
        super(data, signer);
        this.timestamp = timestamp;
    }
    
    public ExpiringTrustedSecureData(byte[] data, byte[] signature, Verifier verifier) {
        super(data, signature, verifier);
    }
    
    public ExpiringTrustedSecureData(byte[] data, byte[] signature, Verifier verifier, TrustedSecureData timestamp) {
        super(data, signature, verifier);
        this.timestamp = timestamp;
    }
    
    public ExpiringTrustedSecureData(byte[] data, byte[] signature) {
        super(data, signature);
    }
    
    public ExpiringTrustedSecureData(byte[] data, byte[] signature, TrustedSecureData timestamp) {
        super(data, signature);
        this.timestamp = timestamp;
    }
    
    public TrustedSecureData getTimestamp() {
        return timestamp;
    }
    
    public ExpiringTrustedSecureData setTimestamp(TrustedSecureData timestamp) {
        this.timestamp = timestamp;
        return this;
    }
    
    @Override
    public ExpiringTrustedSecureData setSignature(byte[] signature) {
        super.setSignature(signature);
        return this;
    }
    
    @Override
    public ExpiringTrustedSecureData setData(byte[] data) {
        super.setData(data);
        return this;
    }
    
    @Override
    public ExpiringTrustedSecureData copy() {
        return new ExpiringTrustedSecureData(data, signature, timestamp);
    }
    
    @Override
    public ExpiringTrustedSecureData signThis(Signer signer) {
        super.signThis(signer);
        return this;
    }
    
    @Override
    public ExpiringTrustedSecureData verifyThis(Verifier verifier) {
        super.verifyThis(verifier);
        return this;
    }
    
    @Override
    public ExpiringTrustedSecureData verifyThis(Verifier verifier, byte[] data_signature) {
        super.verifyThis(verifier, data_signature);
        return this;
    }
    
    public ExpiringTrustedSecureData toTrustedSecureData(Encryptor encryptor) {
        Objects.requireNonNull(encryptor);
        return new ExpiringTrustedSecureData(getData(), encryptor, timestamp).setSignature(getSignature());
    }
    
    public ExpiringTrustedSecureData toTrustedSecureData(Encryptor encryptor, Signer signer) {
        Objects.requireNonNull(encryptor);
        Objects.requireNonNull(signer);
        return new ExpiringTrustedSecureData(getData(), encryptor, signer, timestamp);
    }
    
    public ExpiringTrustedSecureData toTrustedSecureData(Decryptor decryptor) {
        Objects.requireNonNull(decryptor);
        return new ExpiringTrustedSecureData(getData(), decryptor, timestamp).setSignature(getSignature());
    }
    
    public ExpiringTrustedSecureData toTrustedSecureData(Decryptor decryptor, Verifier verifier) {
        Objects.requireNonNull(decryptor);
        Objects.requireNonNull(verifier);
        return new ExpiringTrustedSecureData(getData(), decryptor, getSignature(), verifier, timestamp);
    }
    
    public ExpiringTrustedSecureData createTimestamp(Signer signer) {
        return createTimestamp(null, signer);
    }
    
    public ExpiringTrustedSecureData createTimestamp(Encryptor encryptor, Signer signer) {
        return setTimestamp(EasyCryptUtil.createTimestamp(encryptor, signer));
    }
    
    public ExpiringTrustedSecureData createTimestamp(long timestamp, Signer signer) {
        return createTimestamp(timestamp, null, signer);
    }
    
    public ExpiringTrustedSecureData createTimestamp(long timestamp, Encryptor encryptor, Signer signer) {
        return setTimestamp(EasyCryptUtil.createTimestamp(timestamp, encryptor, signer));
    }
    
    public boolean isExpired(long max_time_error, TimeUnit unit) {
        return EasyCryptUtil.isExpired(timestamp, EasyCryptUtil.createTimePredicateOfMaximumError(max_time_error, unit));
    }
    
    public boolean isExpired(ToughPredicate<Long> timeTester) {
        return EasyCryptUtil.isExpired(timestamp, timeTester);
    }
    
    public boolean isExpired(long max_time_error, TimeUnit unit, Verifier verifier) {
        return isExpired(max_time_error, unit, null, verifier);
    }
    
    public boolean isExpired(long max_time_error, TimeUnit unit, Decryptor decryptor, Verifier verifier) {
        return EasyCryptUtil.isExpired(timestamp, EasyCryptUtil.createTimePredicateOfMaximumError(max_time_error, unit), decryptor, verifier);
    }
    
    public boolean isExpired(ToughPredicate<Long> timeTester, Verifier verifier) {
        return isExpired(timeTester, null, verifier);
    }
    
    public boolean isExpired(ToughPredicate<Long> timeTester, Decryptor decryptor, Verifier verifier) {
        return EasyCryptUtil.isExpired(timestamp, timeTester, decryptor, verifier);
    }
    
    public long getTimestampAsLong() {
        return getTimestampAsLong(null);
    }
    
    public long getTimestampAsLong(Decryptor decryptor) {
        return timestamp == null ? -1 : ConvertUtil.byteArrayToLong(decryptor == null ? timestamp.getData() : timestamp.decryptWithoutException(decryptor));
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ExpiringTrustedSecureData)) {
            return false;
        }
        final ExpiringTrustedSecureData that = (ExpiringTrustedSecureData) object;
        return Arrays.equals(data, that.data) && Arrays.equals(signature, that.signature) && Objects.equals(timestamp, that.timestamp);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timestamp);
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "timestamp=" + timestamp + ", signature=" + Arrays.toString(signature) + ", data=" + Arrays.toString(data) + '}';
    }
    
}
