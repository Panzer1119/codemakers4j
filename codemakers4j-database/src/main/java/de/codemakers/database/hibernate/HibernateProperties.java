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

import de.codemakers.base.util.DefaultSettings;
import de.codemakers.base.util.Settings;
import de.codemakers.io.IOUtil;
import org.apache.commons.text.StringSubstitutor;
import org.hibernate.dialect.Dialect;

import java.util.Properties;

public class HibernateProperties {
    
    public static final String RESOURCE_PATH_TEMPLATE_MYSQL = "/de/codemakers/database/hibernate-mysql.properties";
    public static final String RESOURCE_PATH_TEMPLATE_POSTGRESQL = "/de/codemakers/database/hibernate-postgresql.properties";
    
    private static final DefaultSettings TEMPLATE_MYSQL = new DefaultSettings();
    private static final DefaultSettings TEMPLATE_POSTGRESQL = new DefaultSettings();
    
    // // Config Keys
    // Misc Stuff
    public static final String KEY_HIBERNATE_DIALECT = "hibernate.dialect";
    // Audit Table Stuff
    public static final String KEY_ORG_HIBERNATE_ENVERS_AUDIT_TABLE_PREFIX = "org.hibernate.envers.audit_table_prefix";
    public static final String KEY_ORG_HIBERNATE_ENVERS_AUDIT_TABLE_SUFFIX = "org.hibernate.envers.audit_table_suffix";
    // Connection Stuff
    public static final String KEY_HIBERNATE_CONNECTION_URL = "hibernate.connection.url";
    public static final String KEY_HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
    public static final String KEY_HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
    public static final String KEY_DEFAULT_PORT = "default.port";
    // // Config Default Values
    // Misc Stuff
    public static final String DEFAULT_HIBERNATE_DIALECT = null;
    // Audit Table Stuff
    public static final String DEFAULT_ORG_HIBERNATE_ENVERS_AUDIT_TABLE_PREFIX = "audit_log_";
    public static final String DEFAULT_ORG_HIBERNATE_ENVERS_AUDIT_TABLE_SUFFIX = "";
    // Connection Stuff
    public static final String DEFAULT_HIBERNATE_CONNECTION_URL = null;
    public static final String DEFAULT_HIBERNATE_CONNECTION_USERNAME = null;
    public static final String DEFAULT_HIBERNATE_CONNECTION_PASSWORD = null;
    public static final String DEFAULT_DEFAULT_PORT = null;
    
    // // Substitution Keys
    // Connection Stuff
    public static final String KEY_HOST = "host";
    public static final String KEY_PORT = "port";
    public static final String KEY_DATABASE = "database";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    
    static {
        loadTemplate(RESOURCE_PATH_TEMPLATE_MYSQL, TEMPLATE_MYSQL);
        loadTemplate(RESOURCE_PATH_TEMPLATE_POSTGRESQL, TEMPLATE_POSTGRESQL);
    }
    
    private static void loadTemplate(String resourcePath, Settings settings) {
        IOUtil.getResourceAsStream(resourcePath).ifPresent(settings::loadSettings);
    }
    
    public static HibernateProperties createMySQLTemplate() {
        return new HibernateProperties((Properties) TEMPLATE_MYSQL.getProperties().clone());
    }
    
    public static HibernateProperties createPostgreSQLTemplate() {
        return new HibernateProperties((Properties) TEMPLATE_POSTGRESQL.getProperties().clone());
    }
    
    public static Properties createSubstitutedProperties(Properties template, Properties substitutions) {
        final Properties properties = new Properties();
        properties.putAll(template);
        properties.stringPropertyNames().forEach(key -> substitute(properties, key, substitutions));
        return properties;
    }
    
    private static void substitute(Properties properties, String key, Properties substitutions) {
        final String value = properties.getProperty(key);
        final String replaced = StringSubstitutor.replace(value, substitutions);
        properties.setProperty(key, replaced);
    }
    
    private static void checkDefaultValues(Properties properties) {
        checkDefaultValue(properties, KEY_HIBERNATE_DIALECT, DEFAULT_HIBERNATE_DIALECT);
        checkDefaultValue(properties, KEY_ORG_HIBERNATE_ENVERS_AUDIT_TABLE_PREFIX, DEFAULT_ORG_HIBERNATE_ENVERS_AUDIT_TABLE_PREFIX);
        checkDefaultValue(properties, KEY_ORG_HIBERNATE_ENVERS_AUDIT_TABLE_SUFFIX, DEFAULT_ORG_HIBERNATE_ENVERS_AUDIT_TABLE_SUFFIX);
        checkDefaultValue(properties, KEY_HIBERNATE_CONNECTION_URL, DEFAULT_HIBERNATE_CONNECTION_URL);
        checkDefaultValue(properties, KEY_HIBERNATE_CONNECTION_USERNAME, DEFAULT_HIBERNATE_CONNECTION_USERNAME);
        checkDefaultValue(properties, KEY_HIBERNATE_CONNECTION_PASSWORD, DEFAULT_HIBERNATE_CONNECTION_PASSWORD);
    }
    
    private static void checkDefaultValue(Properties properties, String key, String defaultValue) {
        if (!properties.containsKey(key)) {
            properties.setProperty(key, defaultValue);
        }
    }
    
    private final Properties basic;
    private final Properties substitutions;
    
    public HibernateProperties(Properties basic) {
        this(basic, new Properties());
    }
    
    public HibernateProperties(Properties basic, Properties substitutions) {
        this.basic = basic;
        this.substitutions = substitutions;
    }
    
    public Properties getBasic() {
        return basic;
    }
    
    public Properties getSubstitutions() {
        return basic;
    }
    
    public Properties create() {
        if (!substitutions.containsKey(KEY_PORT)) {
            if (!basic.containsKey(KEY_DEFAULT_PORT)) {
                throw new IllegalArgumentException("Missing port definition and default port definition");
            }
            setSubstitution(KEY_PORT, getBasicProperty(KEY_DEFAULT_PORT));
        }
        final Properties substitutedProperties = createSubstitutedProperties(basic, substitutions);
        checkDefaultValues(substitutedProperties);
        return substitutedProperties;
    }
    
    public HibernateProperties setBasicProperty(String key, String value) {
        basic.setProperty(key, value);
        return this;
    }
    
    public String getBasicProperty(String key) {
        return basic.getProperty(key);
    }
    
    public HibernateProperties setDialect(Class<? extends Dialect> dialect) {
        return setBasicProperty(KEY_HIBERNATE_DIALECT, dialect.getName());
    }
    
    public HibernateProperties setSubstitution(String key, String value) {
        substitutions.setProperty(key, value);
        return this;
    }
    
    public String getSubstitution(String key) {
        return substitutions.getProperty(key);
    }
    
    public HibernateProperties setHost(String host) {
        return setSubstitution(KEY_HOST, host);
    }
    
    public HibernateProperties setPort(Integer port) {
        if (port == null || port < 0) {
            if (!basic.containsKey(KEY_DEFAULT_PORT)) {
                throw new IllegalArgumentException("Empty port definition and missing default port definition");
            }
            port = Integer.parseInt(basic.getProperty(KEY_PORT));
        }
        return setSubstitution(KEY_PORT, "" + port);
    }
    
    public HibernateProperties setDatabase(String database) {
        return setSubstitution(KEY_DATABASE, database);
    }
    
    public HibernateProperties setUsername(String username) {
        return setSubstitution(KEY_USERNAME, username);
    }
    
    public HibernateProperties setPassword(String password) {
        return setSubstitution(KEY_PASSWORD, password);
    }
    
}
