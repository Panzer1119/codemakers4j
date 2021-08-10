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

package de.codemakers.base.entities.results;

import de.codemakers.base.exceptions.RethrownRuntimeException;
import de.codemakers.base.util.Require;
import de.codemakers.base.util.interfaces.ByteSerializable;
import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.base.util.tough.ToughRunnable;
import de.codemakers.io.SerializationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Objects;
import java.util.Optional;

public class Result implements ByteSerializable, Copyable {
    
    private static final Logger logger = LogManager.getLogger(Result.class);
    
    protected boolean successful;
    protected Throwable throwable;
    
    public Result(ToughRunnable runnable) {
        try {
            runnable.run();
            this.successful = true;
        } catch (Exception ex) {
            this.throwable = ex;
        }
    }
    
    public Result(boolean successful, Throwable throwable) {
        this.successful = successful;
        this.throwable = throwable;
    }
    
    public final boolean wasSuccessful() {
        return successful;
    }
    
    public final boolean wasNotSuccessful() {
        return !successful;
    }
    
    public final Throwable getThrowable() {
        return throwable;
    }
    
    public final boolean hasThrowable() {
        return throwable != null;
    }
    
    public final boolean hasNoThrowable() {
        return throwable == null;
    }
    
    public final void throwError() {
        if (throwable != null) {
            throw new RethrownRuntimeException(throwable);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Result)) {
            return false;
        }
        final Result result = (Result) o;
        return successful == result.successful && Objects.equals(throwable, result.throwable);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(successful, throwable);
    }
    
    @Override
    public String toString() {
        return "Result{" + "successful=" + successful + ", throwable=" + throwable + '}';
    }
    
    @Override
    public Result copy() {
        return new Result(successful, throwable);
    }
    
    @Override
    public void set(Copyable copyable) {
        final Result result = Require.clazz(copyable, Result.class);
        if (result != null) {
            successful = result.successful;
            throwable = result.throwable;
        }
    }
    
    @Override
    public Optional<byte[]> toBytes() {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeBoolean(successful);
            final Optional<byte[]> optional = throwable == null ? Optional.empty() : SerializationUtil.objectToBytes(throwable);
            if (optional.isEmpty()) {
                return Optional.empty();
            }
            final byte[] data = optional.get();
            dataOutputStream.writeInt(arrayLength(data));
            if (throwable != null) {
                dataOutputStream.write(data);
            }
            dataOutputStream.flush();
            return Optional.of(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            logger.error("Error while converting Result to bytes", e);
            return Optional.empty();
        }
    }
    
    @Override
    public boolean fromBytes(byte[] bytes) {
        try {
            Objects.requireNonNull(bytes);
            final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
            successful = dataInputStream.readBoolean();
            final int length = dataInputStream.readInt();
            if (length >= 0) {
                final byte[] temp = new byte[length];
                final int read = dataInputStream.read(temp);
                if (read == -1 || read != length) {
                    return false;
                }
                final Optional<Serializable> optional = SerializationUtil.bytesToObject(temp);
                if (optional.isEmpty()) {
                    return false;
                }
                throwable = Require.clazz(optional.get(), Throwable.class);
            }
            dataInputStream.close();
            return true;
        } catch (IOException e) {
            logger.error("Error while converting bytes to Result", e);
            return false;
        }
    }
    
}
