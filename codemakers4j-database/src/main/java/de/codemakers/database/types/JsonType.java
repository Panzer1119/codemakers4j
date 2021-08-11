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

package de.codemakers.database.types;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class JsonType<T extends Serializable> implements UserType<T> {
    
    private static final Logger logger = LogManager.getLogger(JsonType.class);
    
    private final Class<T> type;
    
    public JsonType(Class<T> type) {
        this.type = type;
    }
    
    public Class<T> getType() {
        return type;
    }
    
    @Override
    public int[] sqlTypes() {
        return new int[] {Types.JAVA_OBJECT};
    }
    
    @Override
    public Class<T> returnedClass() {
        return getType();
    }
    
    @Override
    public boolean equals(Object x, Object y) {
        return Objects.equals(x, y);
    }
    
    @Override
    public int hashCode(Object x) {
        return Objects.hashCode(x);
    }
    
    @Override
    public T nullSafeGet(ResultSet resultSet, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        final String cellContent = resultSet.getString(position);
        if (cellContent == null) {
            return null;
        }
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(cellContent.getBytes(StandardCharsets.UTF_8), returnedClass());
        } catch (IOException e) {
            logger.error(String.format("Failed to convert String to %s", returnedClass().getName()), e);
            return null;
        }
    }
    
    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, T value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.OTHER);
            return;
        }
        final ObjectMapper objectMapper = new ObjectMapper();
        final StringWriter stringWriter = new StringWriter();
        try {
            objectMapper.writeValue(stringWriter, value);
            stringWriter.flush();
            preparedStatement.setObject(index, stringWriter.toString(), Types.OTHER);
        } catch (IOException e) {
            logger.error(String.format("Failed to convert %s to String", returnedClass().getName()), e);
        }
    }
    
    @Override
    public Object deepCopy(Object value) {
        if (value instanceof Serializable serializable) {
            return SerializationUtils.clone(serializable);
        }
        throw new HibernateException("value is not Serializable");
    }
    
    @Override
    public boolean isMutable() {
        return true;
    }
    
    @Override
    public Serializable disassemble(Object value) {
        return (Serializable) deepCopy(value);
    }
    
    @Override
    public Object assemble(Serializable cached, Object owner) {
        return deepCopy(cached);
    }
    
    @Override
    public Object replace(Object original, Object target, Object owner) {
        return deepCopy(original);
    }
    
}
