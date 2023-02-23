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

import de.codemakers.base.Standard;
import de.codemakers.base.util.tough.ToughConsumer;
import de.codemakers.base.util.tough.ToughFunction;
import de.codemakers.base.util.tough.ToughTriConsumer;
import de.codemakers.database.entities.IEntity;
import de.codemakers.database.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

public abstract class DatabaseConnector {

    private final Object lock = new Object();
    private final SessionFactory sessionFactory;
    private final int shutdownHookId = Standard.addShutdownHook(this::closeAll);
    private Session session = null;

    public DatabaseConnector(Class<?>[] annotatedClasses, Properties properties) {
        final StandardServiceRegistryBuilder registryBuilder = HibernateUtil.createStandardServiceRegistryBuilder(properties);
        final StandardServiceRegistry registry = HibernateUtil.createStandardServiceRegistry(registryBuilder);
        final MetadataSources metadataSources = HibernateUtil.createMetadataSources(registry, annotatedClasses);
        final Metadata metadata = HibernateUtil.createMetadata(metadataSources);
        this.sessionFactory = HibernateUtil.createSessionFactory(metadata);
        init();
    }

    private void init() {
        initDefaultData();
        closeSession();
    }

    protected abstract void initDefaultData();

    protected <I, T extends IEntity<I, ?>> void initData(T entity, Function<I, Optional<T>> function) {
        if (function.apply(entity.getId()).isEmpty()) {
            HibernateUtil.add(this, entity);
        }
    }

    public Object getLock() {
        return lock;
    }

    private Session openSession() {
        synchronized (lock) {
            return sessionFactory.openSession();
        }
    }

    protected Session getSession() {
        return session;
    }

    public Session getOrOpenSession() {
        synchronized (lock) {
            if (session == null || !session.isOpen()) {
                session = openSession();
            }
            return session;
        }
    }

    protected void closeAll() {
        closeSession();
        closeSessionFactory();
        Standard.removeShutdownHook(shutdownHookId);
    }

    private void closeSessionFactory() {
        synchronized (lock) {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    public void closeSession() {
        synchronized (lock) {
            if (session != null) {
                session.close();
            }
        }
    }

    public void useSession(ToughConsumer<Session> sessionConsumer) {
        HibernateUtil.useSession(this, sessionConsumer);
    }

    public void useSession(ToughConsumer<Session> sessionConsumer, boolean silent) {
        HibernateUtil.useSession(this, sessionConsumer, silent);
    }

    public <R> Optional<R> processSession(ToughFunction<Session, R> sessionFunction) {
        return HibernateUtil.processSession(this, sessionFunction);
    }

    public <R> Optional<R> processSession(ToughFunction<Session, R> sessionFunction, boolean silent) {
        return HibernateUtil.processSession(this, sessionFunction, silent);
    }

    public <T> Optional<T> processCriteriaQuerySingleResult(Class<T> clazz, ToughTriConsumer<HibernateCriteriaBuilder, JpaCriteriaQuery<T>, JpaRoot<T>> triConsumer) {
        return HibernateUtil.processCriteriaQuerySingleResult(this, clazz, triConsumer);
    }

    public <T> Optional<List<T>> processCriteriaQuery(Class<T> clazz, ToughTriConsumer<HibernateCriteriaBuilder, JpaCriteriaQuery<T>, JpaRoot<T>> triConsumer) {
        return HibernateUtil.processCriteriaQuery(this, clazz, triConsumer);
    }

    public <T, R> Optional<R> processCriteriaQuery(Class<T> clazz, ToughTriConsumer<HibernateCriteriaBuilder, JpaCriteriaQuery<T>, JpaRoot<T>> triConsumer, Function<Query<T>, R> resultMapper) {
        return HibernateUtil.processCriteriaQuery(this, clazz, triConsumer, resultMapper);
    }

    public <T> Optional<T> get(Class<T> clazz, Object id) {
        return HibernateUtil.get(this, clazz, id);
    }

    public <T> Optional<List<T>> getAll(Class<T> clazz) {
        return HibernateUtil.getAll(this, clazz);
    }

    public <T> Optional<T> getWhere(Class<T> clazz, String column, String value) {
        return HibernateUtil.getWhere(this, clazz, column, value);
    }

    public <T> Optional<List<T>> getAllWhere(Class<T> clazz, String column, String value) {
        return HibernateUtil.getAllWhere(this, clazz, column, value);
    }

    public void add(Object object) {
        HibernateUtil.add(this, object);
    }

    public void add(Object object, boolean silent) {
        HibernateUtil.add(this, object, silent);
    }

    public void set(Object object) {
        HibernateUtil.set(this, object);
    }

    /**
     * Either save(Object) or update(Object) the given instance, depending upon resolution of the unsaved-value checks (see the manual for discussion of unsaved-value checking).
     *
     * @param object a transient or detached instance containing new or updated state
     */
    public void addOrSet(Object object) {
        HibernateUtil.addOrSet(this, object);
    }

    public void delete(Object object) {
        HibernateUtil.delete(this, object);
    }

    public <I, T extends IEntity<I, T>> Optional<T> addOrUpgradeById(T entity, Function<I, Optional<T>> entityGetterFunction) {
        return HibernateUtil.addOrUpgradeById(this, entity, entityGetterFunction);
    }

    public <I, T extends IEntity<I, T>> Optional<T> addOrUpgradeById(T entity, Function<I, Optional<T>> entityGetterFunction, Class<I> idClazz) {
        return HibernateUtil.addOrUpgradeById(this, entity, entityGetterFunction, idClazz);
    }

    public <I, T extends IEntity<I, T>> Optional<T> addOrUpgradeById(T entity, Function<I, Optional<T>> entityGetterFunction, Class<I> idClazz, Function<T, I> idGetterFunction) {
        return HibernateUtil.addOrUpgradeById(this, entity, entityGetterFunction, idClazz, idGetterFunction);
    }

    public <I, M, T extends IEntity<I, T>> Optional<T> addOrUpgrade(T entity, Function<M, Optional<T>> entityGetterFunction, Class<M> middleClazz, Function<T, M> middleGetterFunction) {
        return HibernateUtil.addOrUpgrade(this, entity, entityGetterFunction, middleGetterFunction, middleClazz);
    }

}
