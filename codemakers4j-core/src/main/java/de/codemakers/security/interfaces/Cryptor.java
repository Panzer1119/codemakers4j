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

package de.codemakers.security.interfaces;

import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.exceptions.NotSupportedRuntimeException;
import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public interface Cryptor {
    
    Logger logger = LogManager.getLogger();
    
    boolean usesIV();
    
    byte[] crypt(byte[] data, byte[] iv) throws Exception;
    
    default byte[] crypt(byte[] data, byte[] iv, ToughConsumer<Throwable> failure) {
        try {
            return crypt(data, iv);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] cryptWithoutException(byte[] data, byte[] iv) {
        return crypt(data, iv, null);
    }
    
    default ReturningAction<byte[]> cryptAction(byte[] data, byte[] iv) {
        return new ReturningAction<>(() -> crypt(data, iv));
    }
    
    default byte[] crypt(byte[] data) throws Exception {
        return crypt(data, (byte[]) null);
    }
    
    default byte[] crypt(byte[] data, ToughConsumer<Throwable> failure) {
        try {
            return crypt(data);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    default byte[] cryptWithoutException(byte[] data) {
        return crypt(data, (ToughConsumer<Throwable>) null);
    }
    
    default ReturningAction<byte[]> cryptAction(byte[] data) {
        return new ReturningAction<>(() -> crypt(data));
    }
    
    default Cipher createCipher(byte[] iv) {
        throw new NotSupportedRuntimeException("Algorithm for " + Cipher.class.getSimpleName() + " unknown");
    }
    
    default Cipher createCipher() {
        return createCipher(null);
    }
    
    default CipherOutputStream toCipherOutputStream(OutputStream outputStream, byte[] iv) {
        return new CipherOutputStream(outputStream, createCipher(iv));
    }
    
    default CipherOutputStream toCipherOutputStream(OutputStream outputStream) {
        return new CipherOutputStream(outputStream, createCipher());
    }
    
    default CipherOutputStream toCipherOutputStreamWithIV(OutputStream outputStream, byte[] iv) {
        try {
            final DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            if (iv != null) {
                dataOutputStream.writeInt(iv.length);
                dataOutputStream.write(iv);
            }
            return toCipherOutputStream(dataOutputStream, iv);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    default CipherInputStream toCipherInputStream(InputStream inputStream, byte[] iv) {
        return new CipherInputStream(inputStream, createCipher(iv));
    }
    
    default CipherInputStream toCipherInputStream(InputStream inputStream) {
        return new CipherInputStream(inputStream, createCipher());
    }
    
    default CipherInputStream toCipherInputStreamWithIV(InputStream inputStream) {
        try {
            final DataInputStream dataInputStream = new DataInputStream(inputStream);
            final byte[] iv = new byte[dataInputStream.readInt()];
            dataInputStream.read(iv);
            return toCipherInputStream(dataInputStream, iv);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
}
