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

import de.codemakers.base.util.Require;
import de.codemakers.base.util.Returner;
import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.base.util.tough.*;
import de.codemakers.io.SerializationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Objects;
import java.util.Optional;

public class ReturningResult<T> extends Result {
    
    private static final Logger logger = LogManager.getLogger(ReturningResult.class);
    
    protected T result;
    
    public <D> ReturningResult(ToughMultiFunction<D, T> function, D... data) {
        super(false, null);
        try {
            this.result = function.apply(data);
            this.successful = true;
        } catch (Exception ex) {
            this.throwable = ex;
        }
    }
    
    public <D, E> ReturningResult(ToughBiFunction<D, E, T> function, D data_1, E data_2) {
        super(false, null);
        try {
            this.result = function.apply(data_1, data_2);
            this.successful = true;
        } catch (Exception ex) {
            this.throwable = ex;
        }
    }
    
    public <D> ReturningResult(ToughFunction<D, T> function, D data) {
        super(false, null);
        try {
            this.result = function.apply(data);
            this.successful = true;
        } catch (Exception ex) {
            this.throwable = ex;
        }
    }
    
    public ReturningResult(ToughSupplier<T> supplier) {
        super(false, null);
        try {
            this.result = supplier.get();
            this.successful = true;
        } catch (Exception ex) {
            this.throwable = ex;
        }
    }
    
    public ReturningResult(boolean successful, Throwable throwable, ToughSupplier<T> supplier) {
        this(successful, throwable, supplier.getWithoutException());
    }
    
    public ReturningResult(boolean successful, Throwable throwable, T result) {
        super(successful, throwable);
        this.result = result;
    }
    
    public final T getResult() {
        return result;
    }
    
    public final boolean hasResult() {
        return result != null;
    }
    
    public final boolean hasNoResult() {
        return result == null;
    }
    
    public final Returner<T> returner() {
        return new Returner<>(result);
    }
    
    public final void consume(ToughConsumer<T> consumer) {
        Objects.requireNonNull(consumer);
        consumer.acceptWithoutException(result);
    }
    
    public final <R> R apply(ToughFunction<T, R> function) {
        Objects.requireNonNull(function);
        return function.applyWithoutException(result);
    }
    
    public final boolean test(ToughPredicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return predicate.testWithoutException(result);
    }
    
    public final boolean test(ToughPredicate<T> predicate, boolean onError) {
        Objects.requireNonNull(predicate);
        return predicate.testWithoutException(result, onError);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof ReturningResult)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final ReturningResult<?> that = (ReturningResult<?>) o;
        return Objects.equals(result, that.result);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), result);
    }
    
    @Override
    public String toString() {
        return "ReturningResult{" + "result=" + result + ", successful=" + successful + ", throwable=" + throwable + '}';
    }
    
    @Override
    public ReturningResult copy() {
        return new ReturningResult(successful, throwable, result);
    }
    
    @Override
    public void set(Copyable copyable) {
        super.set(copyable);
        final ReturningResult<T> returningResult = Require.clazz(copyable, ReturningResult.class);
        if (returningResult != null) {
            result = returningResult.result;
        }
    }
    
    @Override
    public Optional<byte[]> toBytes() {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeBoolean(successful);
            Optional<byte[]> optional = throwable == null ? Optional.empty() : SerializationUtil.objectToBytes(throwable);
            if (optional.isEmpty()) {
                return Optional.empty();
            }
            byte[] data = optional.get();
            dataOutputStream.writeInt(arrayLength(data));
            if (throwable != null) {
                dataOutputStream.write(data);
            }
            optional = result == null ? Optional.empty() : SerializationUtil.objectToBytes((Serializable) result);
            if (optional.isEmpty()) {
                return Optional.empty();
            }
            data = optional.get();
            dataOutputStream.writeInt(arrayLength(data));
            if (result != null) {
                dataOutputStream.write(data);
            }
            dataOutputStream.flush();
            return Optional.of(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            logger.error("Error while converting ReturningResult to bytes", e);
            return Optional.empty();
        }
    }
    
    @Override
    public boolean fromBytes(byte[] bytes) {
        try {
            Objects.requireNonNull(bytes);
            final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));
            successful = dataInputStream.readBoolean();
            int length = dataInputStream.readInt();
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
            length = dataInputStream.readInt();
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
                result = (T) optional.get();
            }
            dataInputStream.close();
            return true;
        } catch (IOException e) {
            logger.error("Error while converting bytes to ReturningResult", e);
            return false;
        }
    }
    
}
