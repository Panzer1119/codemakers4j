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

import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.base.util.tough.ToughPredicate;
import de.codemakers.database.connectors.DatabaseConnector;
import de.codemakers.database.minio.MinIOConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class HibernateUtil {
    
    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);
    
    public static StandardServiceRegistryBuilder createStandardServiceRegistryBuilder(Properties properties) {
        Objects.requireNonNull(properties);
        final StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
        standardServiceRegistryBuilder.applySettings(properties);
        return standardServiceRegistryBuilder;
    }
    
    public static StandardServiceRegistry createStandardServiceRegistry(StandardServiceRegistryBuilder builder) {
        Objects.requireNonNull(builder);
        return builder.build();
    }
    
    public static MetadataSources createMetadataSources(StandardServiceRegistry registry, Class<?>[] annotatedClasses) {
        Objects.requireNonNull(registry);
        Objects.requireNonNull(annotatedClasses);
        final MetadataSources metadataSources = new MetadataSources(registry);
        for (Class<?> annotatedClass : annotatedClasses) {
            metadataSources.addAnnotatedClass(annotatedClass);
        }
        return metadataSources;
    }
    
    public static Metadata createMetadata(MetadataSources metadataSources) {
        Objects.requireNonNull(metadataSources);
        return metadataSources.buildMetadata();
    }
    
    public static SessionFactory createSessionFactory(Metadata metadata) {
        Objects.requireNonNull(metadata);
        return metadata.buildSessionFactory();
    }
    
    public static void useSession(DatabaseConnector databaseConnector, ToughConsumer<Session> sessionConsumer) {
        useSession(databaseConnector, sessionConsumer, false);
    }
    
    public static void useSession(DatabaseConnector databaseConnector, ToughConsumer<Session> sessionConsumer, boolean silent) {
        Objects.requireNonNull(databaseConnector);
        Objects.requireNonNull(sessionConsumer);
        final Session session = databaseConnector.getOrOpenSession();
        synchronized (databaseConnector.getLock()) {
            final Transaction transaction = session.beginTransaction();
            try {
                sessionConsumer.accept(session);
                transaction.commit();
            } catch (Exception e) {
                if (!silent) {
                    logger.error("Error while using Session", e);
                }
            } finally {
                transaction.rollback();
            }
        }
    }
    
    public static <R> Optional<R> processSession(DatabaseConnector databaseConnector, ToughFunction<Session, R> sessionFunction) {
        return processSession(databaseConnector, sessionFunction, false);
    }
    
    public static <R> Optional<R> processSession(DatabaseConnector databaseConnector, ToughFunction<Session, R> sessionFunction, boolean silent) {
        Objects.requireNonNull(databaseConnector);
        Objects.requireNonNull(sessionFunction);
        final Session session = databaseConnector.getOrOpenSession();
        synchronized (databaseConnector.getLock()) {
            final Transaction transaction = session.beginTransaction();
            try {
                final R result = sessionFunction.apply(session);
                transaction.commit();
                return Optional.ofNullable(result);
            } catch (Exception e) {
                if (!silent) {
                    logger.error("Error while processing Session", e);
                }
            } finally {
                transaction.rollback();
            }
            return Optional.empty();
        }
    }
    
    public static boolean add(DatabaseConnector databaseConnector, Object object) {
        return processSession(databaseConnector, session -> session.save(object)).isPresent();
    }
    
    public static boolean add(DatabaseConnector databaseConnector, Object object, boolean silent) {
        return processSession(databaseConnector, session -> session.save(object), silent).isPresent();
    }
    
    public static void set(DatabaseConnector databaseConnector, Object object) {
        useSession(databaseConnector, session -> session.update(object));
    }
    
    /**
     * Either save(Object) or update(Object) the given instance, depending upon resolution of the unsaved-value checks (see the manual for discussion of unsaved-value checking).
     *
     * @param object a transient or detached instance containing new or updated state
     */
    public static void addOrSet(DatabaseConnector databaseConnector, Object object) {
        useSession(databaseConnector, (session) -> session.saveOrUpdate(object));
    }
    
    public static void delete(DatabaseConnector databaseConnector, Object object) {
        useSession(databaseConnector, (session) -> session.delete(object));
    }
    
    public static synchronized <T extends IEntity<Integer, T>> Optional<T> addOrUpgradeById(DatabaseConnector databaseConnector, T entity, Function<Integer, Optional<T>> entityGetterFunction) {
        return addOrUpgradeById(databaseConnector, entity, entityGetterFunction, Integer.class);
    }
    
    public static synchronized <I, T extends IEntity<I, T>> Optional<T> addOrUpgradeById(DatabaseConnector databaseConnector, T entity, Function<I, Optional<T>> entityGetterFunction, Class<I> idClazz) {
        return addOrUpgradeById(databaseConnector, entity, entityGetterFunction, idClazz, IEntity::getId);
    }
    
    public static synchronized <I, T extends IEntity<I, T>> Optional<T> addOrUpgradeById(DatabaseConnector databaseConnector, T entity, Function<I, Optional<T>> entityGetterFunction, Class<I> idClazz, Function<T, I> idGetterFunction) {
        return addOrUpgrade(databaseConnector, entity, entityGetterFunction, idClazz, idGetterFunction);
    }
    
    public static synchronized <I, M, T extends IEntity<I, T>> Optional<T> addOrUpgrade(DatabaseConnector databaseConnector, T entity, Function<M, Optional<T>> entityGetterFunction, Class<M> middleClazz, Function<T, M> middleGetterFunction) {
        Objects.requireNonNull(entity, "entity may not be null");
        Objects.requireNonNull(entityGetterFunction, "entityGetterFunction may not be null");
        Objects.requireNonNull(middleGetterFunction, "middleGetterFunction may not be null");
        final M m1 = middleGetterFunction.apply(entity);
        Objects.requireNonNull(m1, "m1 may not be null");
        final Optional<T> existingEntity = entityGetterFunction.apply(m1);
        if (existingEntity.isEmpty()) {
            add(databaseConnector, entity);
            final M m2 = middleGetterFunction.apply(entity);
            Objects.requireNonNull(m2, "m2 may not be null");
            return entityGetterFunction.apply(m2);
        }
        entity.fillExisting(existingEntity.get());
        set(databaseConnector, existingEntity.get());
        final M m3 = middleGetterFunction.apply(entity);
        Objects.requireNonNull(m3, "m3 may not be null");
        return existingEntity;
    }
    
}
