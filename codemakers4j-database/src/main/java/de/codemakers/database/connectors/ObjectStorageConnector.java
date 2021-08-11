/*
 *    Copyright 2021 Paul Hagedorn (Panzer1119)
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

package de.codemakers.database.connectors;

import de.codemakers.base.util.tough.ToughFunction;

import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

public abstract class ObjectStorageConnector<C, T> {
    
    protected final C connector;
    
    protected ObjectStorageConnector(C connector) {
        this.connector = Objects.requireNonNull(connector, "connector may not be null");
    }
    
    protected C getConnector() {
        return connector;
    }
    
    protected void checkParameter(String bucket, String object) {
        Objects.requireNonNull(bucket, "bucket may not be null");
        Objects.requireNonNull(object, "object may not be null");
    }
    
    public abstract boolean existsObject(String bucket, String object);
    
    public abstract Optional<byte[]> readObjectBytes(String bucket, String object);
    
    public abstract Optional<InputStream> readObject(String bucket, String object);
    
    public abstract <R> Optional<R> readObject(String bucket, String object, ToughFunction<T, R> converter);
    
    public abstract boolean writeObject(String bucket, String object, byte[] data);
    
    public abstract boolean writeObject(String bucket, String object, InputStream inputStream);
    
    public abstract boolean removeObject(String bucket, String object);
    
    public boolean copyObject(String srcBucket, String srcObject, String destObject) {
        return copyObject(srcBucket, srcObject, srcBucket, destObject);
    }
    
    public abstract boolean copyObject(String srcBucket, String srcObject, String destBucket, String destObject);
    
    public boolean moveObject(String srcBucket, String srcObject, String destObject) {
        return moveObject(srcBucket, srcObject, srcBucket, destObject);
    }
    
    public abstract boolean moveObject(String srcBucket, String srcObject, String destBucket, String destObject);
    
}
