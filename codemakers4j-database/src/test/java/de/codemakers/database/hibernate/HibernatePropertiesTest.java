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

package de.codemakers.database.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class HibernatePropertiesTest {

    private static final Logger logger = LogManager.getLogger(HibernatePropertiesTest.class);

    @Test
    void test() {
        final HibernateProperties hibernateProperties = HibernateProperties.createPostgreSQLTemplate();
        hibernateProperties.setHost("TEST-HOST");
        hibernateProperties.setPort(1337);
        hibernateProperties.setDatabase("TEST-DB");
        hibernateProperties.setUsername("TEST-USER");
        hibernateProperties.setPassword("TEST-PASS");
        final Properties properties = hibernateProperties.create();
        Assertions.assertEquals("org.hibernate.dialect.PostgreSQLDialect", properties.getProperty("hibernate.dialect"));
        Assertions.assertEquals("audit_log_", properties.getProperty("org.hibernate.envers.audit_table_prefix"));
        Assertions.assertEquals("", properties.getProperty("org.hibernate.envers.audit_table_suffix"));
        Assertions.assertEquals("jdbc:postgresql://TEST-HOST:1337/TEST-DB", properties.getProperty("hibernate.connection.url"));
        Assertions.assertEquals("TEST-PASS", properties.getProperty("hibernate.connection.password"));
        Assertions.assertEquals("TEST-USER", properties.getProperty("hibernate.connection.username"));
        Assertions.assertEquals("5432", properties.getProperty("default.port"));
    }

}
