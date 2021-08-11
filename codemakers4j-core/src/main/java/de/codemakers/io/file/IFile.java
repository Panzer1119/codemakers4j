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

package de.codemakers.io.file;

import de.codemakers.base.action.ClosingAction;
import de.codemakers.base.action.ReturningAction;
import de.codemakers.base.entities.data.Data;
import de.codemakers.base.util.interfaces.Hashable;
import de.codemakers.base.util.interfaces.Hasher;
import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.io.IOUtil;
import de.codemakers.io.file.exceptions.is.*;
import de.codemakers.io.file.exceptions.isnot.*;
import de.codemakers.security.entities.SecureData;
import de.codemakers.security.entities.TrustedSecureData;
import de.codemakers.security.interfaces.*;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class IFile<T extends IFile<?, ?>, P extends Predicate<T>> implements Decryptable, Encryptable, Hashable, Serializable, Signable, Verifiable {
    
    private static final Logger logger = LogManager.getLogger();
    
    public abstract String getName();
    
    public abstract String getPath();
    
    public abstract String getAbsolutePath();
    
    public abstract String toExactString();
    
    public abstract T getAbsoluteFile();
    
    public abstract T getParentFile();
    
    public abstract T getRoot();
    
    public boolean isRoot() {
        return equals(getRoot());
    }
    
    public abstract String getSeparator();
    
    public abstract char getSeparatorChar();
    
    public abstract String getSeparatorRegEx();
    
    public abstract boolean isFile();
    
    public abstract boolean isDirectory();
    
    public abstract boolean mayListFiles();
    
    public abstract boolean exists();
    
    public abstract boolean isAbsolute();
    
    public abstract boolean isRelative();
    
    public abstract boolean isIntern();
    
    public abstract boolean isExtern();
    
    public abstract Path toPath() throws Exception;
    
    public Path toPath(ToughConsumer<Throwable> failure) {
        try {
            return toPath();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public Path toPathWithoutException() {
        return toPath(null);
    }
    
    public abstract URI toURI() throws Exception;
    
    public URI toURI(ToughConsumer<Throwable> failure) {
        try {
            return toURI();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public URI toURIWithoutException() {
        return toURI(null);
    }
    
    public abstract URL toURL() throws Exception;
    
    public URL toURL(ToughConsumer<Throwable> failure) {
        try {
            return toURL();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public URL toURLWithoutException() {
        return toURL(null);
    }
    
    public abstract File toFile();
    
    public abstract boolean mkdir() throws Exception;
    
    public boolean mkdir(ToughConsumer<Throwable> failure) {
        try {
            return mkdir();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    public boolean mkdirWithoutException() {
        return mkdir(null);
    }
    
    public ReturningAction<Boolean> mkdirAction() {
        return new ReturningAction<>(this::mkdir);
    }
    
    public abstract boolean mkdirs() throws Exception;
    
    public boolean mkdirs(ToughConsumer<Throwable> failure) {
        try {
            return mkdirs();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    public boolean mkdirsWithoutException() {
        return mkdirs(null);
    }
    
    public ReturningAction<Boolean> mkdirsAction() {
        return new ReturningAction<>(this::mkdirs);
    }
    
    public abstract boolean delete() throws Exception;
    
    public boolean delete(ToughConsumer<Throwable> failure) {
        try {
            return delete();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    public boolean deleteWithoutException() {
        return delete(null);
    }
    
    public ReturningAction<Boolean> deleteAction() {
        return new ReturningAction<>(this::delete);
    }
    
    public abstract boolean createNewFile() throws Exception;
    
    public boolean createNewFile(ToughConsumer<Throwable> failure) {
        try {
            return createNewFile();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    public boolean createNewFileWithoutException() {
        return createNewFile(null);
    }
    
    public ReturningAction<Boolean> createNewFileAction() {
        return new ReturningAction<>(this::createNewFile);
    }
    
    public BufferedReader createBufferedReader() throws Exception {
        return new BufferedReader(new InputStreamReader(createInputStream()));
    }
    
    public BufferedReader createBufferedReader(ToughConsumer<Throwable> failure) {
        try {
            return createBufferedReader();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public BufferedReader createBufferedReaderWithoutException() {
        return createBufferedReader(null);
    }
    
    public ReturningAction<BufferedReader> createBufferedReaderAction() {
        return new ReturningAction<>(this::createBufferedReader);
    }
    
    public ClosingAction<BufferedReader> createBufferedReaderClosingAction() {
        return new ClosingAction<>(this::createBufferedReader);
    }
    
    public abstract InputStream createInputStream() throws Exception;
    
    public InputStream createInputStream(ToughConsumer<Throwable> failure) {
        try {
            return createInputStream();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public InputStream createInputStreamWithoutException() {
        return createInputStream(null);
    }
    
    public ReturningAction<InputStream> createInputStreamAction() {
        return new ReturningAction<>(this::createInputStream);
    }
    
    public ClosingAction<InputStream> createInputStreamClosingAction() {
        return new ClosingAction<>(this::createInputStream);
    }
    
    public abstract byte[] readBytes() throws Exception;
    
    public byte[] readBytes(ToughConsumer<Throwable> failure) {
        try {
            return readBytes();
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public byte[] readBytesWithoutException() {
        return readBytes(null);
    }
    
    public ReturningAction<byte[]> readBytesAction() {
        return new ReturningAction<>(this::readBytes);
    }
    
    public BufferedWriter createBufferedWriter() throws Exception {
        return createBufferedWriter(false);
    }
    
    public BufferedWriter createBufferedWriter(boolean append) throws Exception {
        return new BufferedWriter(new OutputStreamWriter(createOutputStream(append)));
    }
    
    public BufferedWriter createBufferedWriter(ToughConsumer<Throwable> failure) {
        return createBufferedWriter(false, failure);
    }
    
    public BufferedWriter createBufferedWriter(boolean append, ToughConsumer<Throwable> failure) {
        try {
            return createBufferedWriter(append);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public BufferedWriter createBufferedWriterWithoutException() {
        return createBufferedWriterWithoutException(false);
    }
    
    public BufferedWriter createBufferedWriterWithoutException(boolean append) {
        return createBufferedWriter(append, null);
    }
    
    public ReturningAction<BufferedWriter> createBufferedWriterAction() {
        return createBufferedWriterAction(false);
    }
    
    public ReturningAction<BufferedWriter> createBufferedWriterAction(boolean append) {
        return new ReturningAction<>(() -> createBufferedWriter(append));
    }
    
    public ClosingAction<BufferedWriter> createBufferedWriterClosingAction() {
        return new ClosingAction<>(this::createBufferedWriter);
    }
    
    public ClosingAction<BufferedWriter> createBufferedWriterClosingAction(boolean append) {
        return new ClosingAction<>(() -> createBufferedWriter(append));
    }
    
    public OutputStream createOutputStream() throws Exception {
        return createOutputStream(false);
    }
    
    public abstract OutputStream createOutputStream(boolean append) throws Exception;
    
    public OutputStream createOutputStream(ToughConsumer<Throwable> failure) {
        return createOutputStream(false, failure);
    }
    
    public OutputStream createOutputStream(boolean append, ToughConsumer<Throwable> failure) {
        try {
            return createOutputStream(append);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public OutputStream createOutputStreamWithoutException() {
        return createOutputStreamWithoutException(false);
    }
    
    public OutputStream createOutputStreamWithoutException(boolean append) {
        return createOutputStream(append, null);
    }
    
    public ReturningAction<OutputStream> createOutputStreamAction() {
        return new ReturningAction<>(this::createOutputStream);
    }
    
    public ReturningAction<OutputStream> createOutputStreamAction(boolean append) {
        return new ReturningAction<>(() -> createOutputStream(append));
    }
    
    public ClosingAction<OutputStream> createOutputStreamClosingAction() {
        return new ClosingAction<>(this::createOutputStream);
    }
    
    public ClosingAction<OutputStream> createOutputStreamClosingAction(boolean append) {
        return new ClosingAction<>(() -> createOutputStream(append));
    }
    
    public abstract boolean writeBytes(byte[] data) throws Exception;
    
    public boolean writeBytes(byte[] data, ToughConsumer<Throwable> failure) {
        try {
            return writeBytes(data);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    public boolean writeBytesWithoutException(byte[] data) {
        return writeBytes(data, null);
    }
    
    public ReturningAction<Boolean> writeBytesAction(byte[] data) {
        return new ReturningAction<>(() -> writeBytes(data));
    }
    
    public <R> R use(Function<T, R> function) {
        if (function == null) {
            return null;
        }
        return function.apply((T) this);
    }
    
    public <R> R use(Function<T, R> function, ToughConsumer<Throwable> failure) {
        try {
            return use(function);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public <R> R useWithoutException(Function<T, R> function) {
        return use(function, null);
    }
    
    public <R> ReturningAction<R> useAction(Function<T, R> function) {
        return new ReturningAction<>(() -> use(function));
    }
    
    // listFiles BEGIN =================================================================================================
    
    public List<T> listFiles() {
        return listFiles(false);
    }
    
    public abstract List<T> listFiles(boolean recursive);
    
    public List<T> listFiles(ToughConsumer<Throwable> failure) {
        return listFiles(false, failure);
    }
    
    public List<T> listFiles(boolean recursive, ToughConsumer<Throwable> failure) {
        try {
            return listFiles(recursive);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public List<T> listFilesWithoutException() {
        return listFilesWithoutException(false);
    }
    
    public List<T> listFilesWithoutException(boolean recursive) {
        return listFiles(recursive, (ToughConsumer<Throwable>) null);
    }
    
    public ReturningAction<List<T>> listFilesAction() {
        return new ReturningAction<>(this::listFiles);
    }
    
    public ReturningAction<List<T>> listFilesAction(boolean recursive) {
        return new ReturningAction<>(() -> listFiles(recursive));
    }
    
    // listFiles MID ============================================================
    
    public List<T> listFiles(P fileFilter) {
        return listFiles(false, fileFilter);
    }
    
    public abstract List<T> listFiles(boolean recursive, P fileFilter);
    
    public List<T> listFiles(P fileFilter, ToughConsumer<Throwable> failure) {
        return listFiles(false, fileFilter, failure);
    }
    
    public List<T> listFiles(boolean recursive, P fileFilter, ToughConsumer<Throwable> failure) {
        try {
            return listFiles(recursive, fileFilter);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public List<T> listFilesWithoutException(P fileFilter) {
        return listFilesWithoutException(false, fileFilter);
    }
    
    public List<T> listFilesWithoutException(boolean recursive, P fileFilter) {
        return listFiles(recursive, fileFilter, null);
    }
    
    public ReturningAction<List<T>> listFilesAction(P fileFilter) {
        return new ReturningAction<>(() -> listFiles(fileFilter));
    }
    
    public ReturningAction<List<T>> listFilesAction(boolean recursive, P fileFilter) {
        return new ReturningAction<>(() -> listFiles(recursive, fileFilter));
    }
    
    // listFiles END ===================================================================================================
    
    public boolean forChildren(ToughConsumer<T> consumer) {
        return forChildren(consumer, false);
    }
    
    public boolean forChildren(ToughConsumer<T> consumer, boolean recursive) {
        if (consumer == null) {
            return false;
        }
        listFiles(recursive).forEach((advancedFile) -> consumer.acceptWithoutException(advancedFile));
        return true;
    }
    
    public boolean forChildren(ToughConsumer<T> consumer, ToughConsumer<Throwable> failure) {
        return forChildren(consumer, false, failure);
    }
    
    public boolean forChildren(ToughConsumer<T> consumer, boolean recursive, ToughConsumer<Throwable> failure) {
        try {
            return forChildren(consumer, recursive);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    public boolean forChildrenWithoutException(ToughConsumer<T> consumer) {
        return forChildren(consumer, null);
    }
    
    public boolean forChildrenWithoutException(ToughConsumer<T> consumer, boolean recursive) {
        return forChildren(consumer, recursive, null);
    }
    
    public ReturningAction<Boolean> forChildrenAction(ToughConsumer<T> consumer) {
        return new ReturningAction<>(() -> forChildren(consumer));
    }
    
    public ReturningAction<Boolean> forChildrenAction(ToughConsumer<T> consumer, boolean recursive) {
        return new ReturningAction<>(() -> forChildren(consumer, recursive));
    }
    
    public boolean forChildrenParallel(ToughConsumer<T> consumer) {
        return forChildrenParallel(consumer, false);
    }
    
    public boolean forChildrenParallel(ToughConsumer<T> consumer, boolean recursive) {
        if (consumer == null) {
            return false;
        }
        listFiles(recursive).parallelStream().forEach((advancedFile) -> consumer.acceptWithoutException(advancedFile));
        return true;
    }
    
    public boolean forChildrenParallel(ToughConsumer<T> consumer, ToughConsumer<Throwable> failure) {
        return forChildrenParallel(consumer, false, failure);
    }
    
    public boolean forChildrenParallel(ToughConsumer<T> consumer, boolean recursive, ToughConsumer<Throwable> failure) {
        try {
            return forChildrenParallel(consumer, recursive);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return false;
        }
    }
    
    public boolean forChildrenParallelWithoutException(ToughConsumer<T> consumer) {
        return forChildrenParallelWithoutException(consumer, false);
    }
    
    public boolean forChildrenParallelWithoutException(ToughConsumer<T> consumer, boolean recursive) {
        return forChildrenParallel(consumer, recursive, null);
    }
    
    public ReturningAction<Boolean> forChildrenParallelAction(ToughConsumer<T> consumer) {
        return new ReturningAction<>(() -> forChildrenParallel(consumer));
    }
    
    public ReturningAction<Boolean> forChildrenParallelAction(ToughConsumer<T> consumer, boolean recursive) {
        return new ReturningAction<>(() -> forChildrenParallel(consumer, recursive));
    }
    
    protected boolean checkAndErrorIfFile(boolean throwException) {
        if (isFile()) {
            if (throwException) {
                throw new FileIsFileRuntimeException(getPath() + " is a file");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfNotFile(boolean throwException) {
        if (!isFile()) {
            if (throwException) {
                throw new FileIsNotFileRuntimeException(getPath() + " is not a file");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfDirectory(boolean throwException) {
        if (isDirectory()) {
            if (throwException) {
                throw new FileIsDirectoryRuntimeException(getPath() + " is a directory");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfNotDirectory(boolean throwException) {
        if (!isDirectory()) {
            if (throwException) {
                throw new FileIsNotDirectoryRuntimeException(getPath() + " is not a directory");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfExisting(boolean throwException) {
        if (exists()) {
            if (throwException) {
                throw new FileIsExistingRuntimeException(getPath() + " does exist");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfNotExisting(boolean throwException) {
        if (!exists()) {
            if (throwException) {
                throw new FileIsNotExistingRuntimeException(getPath() + " does not exist");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfAbsolute(boolean throwException) {
        if (isAbsolute()) {
            if (throwException) {
                throw new FileIsAbsoluteRuntimeException(getPath() + " is absolute");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfNotAbsolute(boolean throwException) {
        if (!isAbsolute()) {
            if (throwException) {
                throw new FileIsNotAbsoluteRuntimeException(getPath() + " is not absolute");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfRelative(boolean throwException) {
        if (isRelative()) {
            if (throwException) {
                throw new FileIsRelativeRuntimeException(getPath() + " is relative");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfNotRelative(boolean throwException) {
        if (!isRelative()) {
            if (throwException) {
                throw new FileIsNotRelativeRuntimeException(getPath() + " is not relative");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfIntern(boolean throwException) {
        if (isIntern()) {
            if (throwException) {
                throw new FileIsInternRuntimeException(getPath() + " is intern");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfNotIntern(boolean throwException) {
        if (!isIntern()) {
            if (throwException) {
                throw new FileIsNotInternRuntimeException(getPath() + " is not intern");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfExtern(boolean throwException) {
        if (isExtern()) {
            if (throwException) {
                throw new FileIsExternRuntimeException(getPath() + " is extern");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    protected boolean checkAndErrorIfNotExtern(boolean throwException) {
        if (!isExtern()) {
            if (throwException) {
                throw new FileIsNotExternRuntimeException(getPath() + " is not extern");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (object instanceof IFile) {
            return Objects.equals(getPath(), ((IFile<?, ?>) object).getPath());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final String path = getPath();
        if (path == null) {
            return super.hashCode();
        }
        return path.hashCode();
    }
    
    @Override
    public byte[] crypt(Cryptor cryptor) throws Exception {
        Objects.requireNonNull(cryptor);
        if (cryptor.usesIV()) {
            return IOUtils.toByteArray(cryptor.toCipherInputStreamWithIV(createInputStream()));
        } else {
            return IOUtils.toByteArray(cryptor.toCipherInputStream(createInputStream()));
        }
    }
    
    @Override
    public IFile<T, P> cryptThis(Cryptor cryptor) throws Exception {
        Objects.requireNonNull(cryptor);
        if (cryptor instanceof Encryptor) {
            return (IFile<T, P>) encryptThis((Encryptor) cryptor);
        } else if (cryptor instanceof Decryptor) {
            return (IFile<T, P>) decryptThis((Decryptor) cryptor);
        } else {
            throw new UnsupportedOperationException();
        }
    }
    
    @Override
    public byte[] decrypt(Decryptor decryptor) throws Exception {
        return crypt(decryptor);
    }
    
    @Override
    public byte[] encrypt(Encryptor encryptor) throws Exception {
        return crypt(encryptor);
    }
    
    @Override
    public byte[] sign(Signer signer) throws Exception {
        Objects.requireNonNull(signer);
        return signer.sign(createInputStream());
    }
    
    @Override
    public IFile<T, P> signThis(Signer signer) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean verify(Verifier verifier) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean verify(Verifier verifier, byte[] data_signature) throws Exception {
        Objects.requireNonNull(verifier);
        return verifier.verify(createInputStream(), data_signature);
    }
    
    @Override
    public IFile<T, P> verifyThis(Verifier verifier) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public IFile<T, P> verifyThis(Verifier verifier, byte[] signature) throws Exception {
        verify(verifier, signature);
        return this;
    }
    
    @Override
    public InputStream toInputStream() {
        return createInputStreamWithoutException();
    }
    
    @Override
    public OutputStream toOutputStream() {
        return createOutputStreamWithoutException();
    }
    
    public Data toData() {
        return new Data(readBytesWithoutException());
    }
    
    public SecureData toSecureData() {
        return new SecureData(readBytesWithoutException());
    }
    
    public SecureData toSecureData(Encryptor encryptor) {
        return new SecureData(readBytesWithoutException(), encryptor);
    }
    
    public SecureData toSecureData(Decryptor decryptor) {
        return new SecureData(readBytesWithoutException(), decryptor);
    }
    
    public TrustedSecureData toTrustedSecureData() {
        return new TrustedSecureData(readBytesWithoutException());
    }
    
    public TrustedSecureData toTrustedSecureData(Encryptor encryptor) {
        return new TrustedSecureData(readBytesWithoutException(), encryptor);
    }
    
    public TrustedSecureData toTrustedSecureData(Decryptor decryptor) {
        return new TrustedSecureData(readBytesWithoutException(), decryptor);
    }
    
    public TrustedSecureData toTrustedSecureData(Encryptor encryptor, Signer signer) {
        return new TrustedSecureData(readBytesWithoutException(), encryptor, signer);
    }
    
    public TrustedSecureData toTrustedSecureData(Decryptor decryptor, Verifier verifier, byte[] signature) {
        return new TrustedSecureData(readBytesWithoutException(), decryptor, signature, verifier);
    }
    
    @Override
    public byte[] hash(Hasher hasher) throws Exception {
        return IOUtil.hashInputStream(createInputStream(), hasher);
    }
    
}
