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

package de.codemakers.database.minio;

import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.database.connectors.ObjectStorageConnector;
import de.codemakers.io.IOUtil;
import io.minio.*;
import io.minio.errors.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

public class MinIOConnector extends ObjectStorageConnector<MinioClient, InputStream> {
    
    private static final Logger logger = LogManager.getLogger(MinIOConnector.class);
    
    public static final int DEFAULT_PART_SIZE = ObjectWriteArgs.MIN_MULTIPART_SIZE * 20;
    
    public MinIOConnector(MinioClient connector) {
        super(connector);
    }
    
    @Override
    public boolean existsObject(String bucket, String object) {
        checkParameter(bucket, object);
        try {
            return connector.statObject(StatObjectArgs.builder().bucket(bucket).object(object).build()) != null;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            logger.error(String.format("Error while checking existence of \"%s\" in \"%s\"", object, bucket), e);
            return false;
        }
    }
    
    @Override
    public Optional<byte[]> readObjectBytes(String bucket, String object) {
        checkParameter(bucket, object);
        return readObject(bucket, object, InputStream::readAllBytes);
    }
    
    @Override
    public Optional<InputStream> readObject(String bucket, String object) {
        checkParameter(bucket, object);
        try {
            return Optional.ofNullable(connector.getObject(GetObjectArgs.builder().bucket(bucket).object(object).build()));
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            logger.error(String.format("Error while getting \"%s\" from \"%s\"", object, bucket), e);
            return Optional.empty();
        }
    }
    
    @Override
    public <R> Optional<R> readObject(String bucket, String object, ToughFunction<InputStream, R> converter) {
        checkParameter(bucket, object);
        Objects.requireNonNull(converter, "converter may not be null");
        try (final GetObjectResponse response = connector.getObject(GetObjectArgs.builder().bucket(bucket).object(object).build())) {
            return Optional.ofNullable(response).map(converter::applyWithoutException);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            logger.error(String.format("Error while getting \"%s\" from \"%s\"", object, bucket), e);
            return Optional.empty();
        }
    }
    
    @Override
    public boolean writeObject(String bucket, String object, byte[] data) {
        checkParameter(bucket, object);
        Objects.requireNonNull(data, "data may not be null");
        try {
            return connector.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .stream(new ByteArrayInputStream(data), data.length, -1)
                    .build()) != null;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            logger.error(String.format("Error while writing \"%s\" to \"%s\"", object, bucket), e);
            return false;
        }
    }
    
    @Override
    public boolean writeObject(String bucket, String object, InputStream inputStream) {
        checkParameter(bucket, object);
        Objects.requireNonNull(inputStream, "inputStream may not be null");
        try {
            return connector.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(object)
                    .stream(inputStream, -1, DEFAULT_PART_SIZE)
                    .build()) != null;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            logger.error(String.format("Error while writing \"%s\" from InputStream to \"%s\"", object, bucket), e);
            return false;
        } finally {
            IOUtil.closeQuietly(inputStream);
        }
    }
    
    @Override
    public boolean removeObject(String bucket, String object) {
        checkParameter(bucket, object);
        try {
            connector.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(object).build());
            return true;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            logger.error(String.format("Error while removing \"%s\" from \"%s\"", object, bucket), e);
            return false;
        }
    }
    
    @Override
    public boolean copyObject(String srcBucket, String srcObject, String destBucket, String destObject) {
        checkParameter(srcBucket, srcObject);
        checkParameter(destBucket, destObject);
        try {
            return connector.copyObject(CopyObjectArgs.builder()
                    .source(CopySource.builder().bucket(srcBucket).object(srcObject).build())
                    .bucket(destBucket)
                    .object(destObject)
                    .build()) != null;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            logger.error(String.format("Error while copying \"%s\" from \"%s\" to \"%s\" as \"%s\"", srcObject, srcBucket, destBucket, destObject), e);
            return false;
        }
    }
    
    @Override
    public boolean moveObject(String srcBucket, String srcObject, String destBucket, String destObject) {
        checkParameter(srcBucket, srcObject);
        checkParameter(destBucket, destObject);
        if (!copyObject(srcBucket, srcObject, destBucket, destObject)) {
            return false;
        }
        return removeObject(srcBucket, srcObject);
    }
    
}
