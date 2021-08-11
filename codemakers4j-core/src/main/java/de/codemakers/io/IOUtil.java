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

package de.codemakers.io;

import de.codemakers.base.util.interfaces.Hasher;
import de.codemakers.base.util.tough.ToughBiConsumer;
import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class IOUtil {
    
    private static final Logger logger = LogManager.getLogger(IOUtil.class);
    
    public static final int STANDARD_BUFFER_SIZE = 1024;
    
    public static boolean processInputStream(InputStream inputStream, ToughBiConsumer<byte[], Integer> bufferConsumer) {
        return processInputStream(inputStream, STANDARD_BUFFER_SIZE, bufferConsumer);
    }
    
    public static boolean processInputStream(InputStream inputStream, int bufferSize, ToughBiConsumer<byte[], Integer> bufferConsumer) {
        return processInputStream(inputStream, new byte[bufferSize], bufferConsumer);
    }
    
    public static boolean processInputStream(InputStream inputStream, final byte[] buffer, ToughBiConsumer<byte[], Integer> bufferConsumer) {
        try {
            int read = -1;
            while ((read = inputStream.read(buffer)) != -1) {
                bufferConsumer.accept(buffer, read);
            }
            inputStream.close();
            return true;
        } catch (Exception e) {
            logger.error("Error while processing InputStream", e);
            return false;
        }
    }
    
    public static byte[] hashInputStream(InputStream inputStream, Hasher hasher) {
        return hashInputStream(inputStream, STANDARD_BUFFER_SIZE, hasher);
    }
    
    public static byte[] hashInputStream(InputStream inputStream, int bufferSize, Hasher hasher) {
        if (!processInputStream(inputStream, bufferSize, (buffer, read) -> hasher.update(buffer, 0, read))) {
            return null;
        }
        return hasher.hashWithoutException();
    }
    
    public static Hasher loadInputStreamToHasher(InputStream inputStream, Hasher hasher) {
        return loadInputStreamToHasher(inputStream, STANDARD_BUFFER_SIZE, hasher);
    }
    
    public static Hasher loadInputStreamToHasher(InputStream inputStream, int bufferSize, Hasher hasher) {
        if (!processInputStream(inputStream, bufferSize, (buffer, read) -> hasher.update(buffer, 0, read))) {
            return null;
        }
        return hasher;
    }
    
    public static void closeQuietly(AutoCloseable autoCloseable) {
        try {
            autoCloseable.close();
        } catch (Exception ex) {
            //Nothing
        }
    }
    
    public static void close(AutoCloseable autoCloseable) {
        try {
            autoCloseable.close();
        } catch (Exception e) {
            logger.error("Error while closing AutoCloseable: " + autoCloseable, e);
        }
    }
    
    public static void close(AutoCloseable autoCloseable, ToughConsumer<Throwable> failure) {
        try {
            autoCloseable.close();
        } catch (Exception e) {
            failure.acceptWithoutException(e);
        }
    }
    
    public static Optional<byte[]> toByteArray(InputStream inputStream) {
        if (inputStream == null) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(IOUtils.toByteArray(inputStream));
        } catch (IOException e) {
            logger.warn("IOException while reading InputStream", e);
            return Optional.empty();
        }
    }
    
    public static Optional<InputStream> getResourceAsStream(String name) {
        return Optional.ofNullable(IOUtil.class.getResourceAsStream(name));
    }
    
    public static Optional<byte[]> readResource(String name) {
        return getResourceAsStream(name).flatMap(IOUtil::toByteArray);
    }
    
    public static Optional<String> readResourceToString(String name) {
        return getResourceAsStream(name).flatMap(IOUtil::toByteArray)
                .map(String::new);
    }
    
}
