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

import de.codemakers.base.util.Require;
import de.codemakers.base.util.interfaces.Copyable;

import java.util.Arrays;
import java.util.Objects;

public class TrustedSecureData extends SecureData implements Signable, Verifiable {
    
    protected byte[] signature = null;
    
    public TrustedSecureData(byte[] data) {
        this(data, (byte[]) null);
    }
    
    public TrustedSecureData(byte[] data, Encryptor encryptor) {
        this(encryptor.encryptWithoutException(data));
    }
    
    public TrustedSecureData(byte[] data, Decryptor decryptor) {
        this(decryptor.decryptWithoutException(data));
    }
    
    public TrustedSecureData(byte[] data, Encryptor encryptor, Signer signer) {
        this(encryptor.encryptWithoutException(data), signer);
    }
    
    public TrustedSecureData(byte[] data, Decryptor decryptor, byte[] signature, Verifier verifier) {
        this(decryptor.decryptWithoutException(data), signature, verifier);
    }
    
    public TrustedSecureData(byte[] data, Signer signer) {
        this(data, signer.signWithoutException(data));
    }
    
    public TrustedSecureData(byte[] data, byte[] signature, Verifier verifier) {
        this(data, signature);
        verifyThis(verifier);
    }
    
    public TrustedSecureData(byte[] data, byte[] signature) {
        super(data);
        this.signature = signature;
    }
    
    public byte[] getSignature() {
        return signature;
    }
    
    public TrustedSecureData setSignature(byte[] signature) {
        this.signature = signature;
        return this;
    }
    
    @Override
    public TrustedSecureData setData(byte[] data) {
        super.setData(data);
        return this;
    }
    
    @Override
    public void set(Copyable copyable) {
        Objects.requireNonNull(copyable);
        final TrustedSecureData trustedSecureData = Require.clazz(copyable, TrustedSecureData.class);
        if (data != null) {
            setData(trustedSecureData.getData());
            setSignature(trustedSecureData.getSignature());
        }
    }
    
    @Override
    public TrustedSecureData copy() {
        return new TrustedSecureData(data, signature);
    }
    
    @Override
    public byte[] sign(Signer signer) throws Exception {
        Objects.requireNonNull(signer);
        return signer.sign(getData());
    }
    
    @Override
    public TrustedSecureData signThis(Signer signer) {
        Objects.requireNonNull(signer);
        setSignature(signer.signWithoutException(getData()));
        return this;
    }
    
    @Override
    public boolean verify(Verifier verifier) throws Exception {
        Objects.requireNonNull(verifier);
        return verifier.verify(getData(), getSignature());
    }
    
    @Override
    public boolean verify(Verifier verifier, byte[] data_signature) throws Exception {
        Objects.requireNonNull(verifier);
        Objects.requireNonNull(data_signature);
        return verifier.verify(getData(), data_signature);
    }
    
    @Override
    public TrustedSecureData verifyThis(Verifier verifier) {
        Objects.requireNonNull(verifier);
        if (!verifier.verifyWithoutException(getData(), getSignature())) {
            throw new SecurityException();
        }
        return this;
    }
    
    @Override
    public TrustedSecureData verifyThis(Verifier verifier, byte[] data_signature) {
        Objects.requireNonNull(verifier);
        Objects.requireNonNull(data_signature);
        if (!verifier.verifyWithoutException(getData(), data_signature)) {
            throw new SecurityException();
        }
        return this;
    }
    
    public TrustedSecureData toTrustedSecureData(Encryptor encryptor) {
        Objects.requireNonNull(encryptor);
        return new TrustedSecureData(getData(), encryptor).setSignature(getSignature());
    }
    
    public TrustedSecureData toTrustedSecureData(Encryptor encryptor, Signer signer) {
        Objects.requireNonNull(encryptor);
        Objects.requireNonNull(signer);
        return new TrustedSecureData(getData(), encryptor, signer);
    }
    
    public TrustedSecureData toTrustedSecureData(Decryptor decryptor) {
        Objects.requireNonNull(decryptor);
        return new TrustedSecureData(getData(), decryptor).setSignature(getSignature());
    }
    
    public TrustedSecureData toTrustedSecureData(Decryptor decryptor, Verifier verifier) {
        Objects.requireNonNull(decryptor);
        Objects.requireNonNull(verifier);
        return new TrustedSecureData(getData(), decryptor, getSignature(), verifier);
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof TrustedSecureData)) {
            return false;
        }
        final TrustedSecureData that = (TrustedSecureData) object;
        return Arrays.equals(data, that.data) && Arrays.equals(signature, that.signature);
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(signature);
        return result;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "data=" + Arrays.toString(data) + ", signature=" + Arrays.toString(signature) + '}';
    }
    
}
