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

import de.codemakers.base.action.ReturningAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Optional;

public class SerializationUtil {
    
    private static final Logger logger = LogManager.getLogger(SerializationUtil.class);
    
    public static Optional<byte[]> objectToBytes(Serializable serializable) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.flush();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            return Optional.of(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            logger.error("Error while serializing object to bytes", e);
            return Optional.empty();
        }
    }
    
    public static ReturningAction<Optional<byte[]>> objectToBytesAction(Serializable serializable) {
        return new ReturningAction<>(() -> objectToBytes(serializable));
    }
    
    public static <R extends Serializable> Optional<R> bytesToObject(byte[] data) {
        try {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            final Object object = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return Optional.ofNullable((R) object);
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Error while serializing object from bytes", e);
            return Optional.empty();
        }
    }
    
    public static <R extends Serializable> ReturningAction<Optional<R>> bytesToObjectAction(byte[] data) {
        return new ReturningAction<>(() -> bytesToObject(data));
    }
    
}
