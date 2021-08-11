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

package de.codemakers.base.reflection;

import de.codemakers.base.util.tough.ToughConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class ReflectionUtil {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static Object getValue(String fieldName, Object object) throws IllegalAccessException, NoSuchFieldException {
        return getValue(null, fieldName, object);
    }
    
    public static Object getValue(String fieldName, Object object, ToughConsumer<Throwable> failure) {
        try {
            return getValue(fieldName, object);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public static Object getValueWithoutExeption(String fieldName, Object object) {
        return getValue(fieldName, object, null);
    }
    
    public static <T> T getValue(Class<T> clazz, String fieldName, Object object) throws IllegalAccessException, NoSuchFieldException {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(object);
        final Field field = object.getClass().getField(fieldName);
        final boolean accessible = field.isAccessible();
        if (!accessible) {
            field.setAccessible(true);
        }
        final Object value = field.get(object);
        if (!accessible) {
            field.setAccessible(false);
        }
        return (T) value;
    }
    
    public static <T> T getValue(Class<T> clazz, String fieldName, Object object, ToughConsumer<Throwable> failure) {
        try {
            return getValue(clazz, fieldName, object);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public static <T> T getValueWithoutExeption(Class<T> clazz, String fieldName, Object object) {
        return getValue(clazz, fieldName, object, null);
    }
    
    public static Object getStaticValue(String fieldName, Class<?> clazz) throws IllegalAccessException, NoSuchFieldException {
        return getStaticValue(null, fieldName, clazz);
    }
    
    public static Object getStaticValue(String fieldName, Class<?> clazz_, ToughConsumer<Throwable> failure) {
        try {
            return getStaticValue(fieldName, clazz_);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public static Object getStaticValueWithoutExeption(String fieldName, Class<?> clazz_) {
        return getStaticValue(fieldName, clazz_, null);
    }
    
    public static <T> T getStaticValue(Class<T> clazz, String fieldName, Class<?> clazz_) throws IllegalAccessException, NoSuchFieldException {
        Objects.requireNonNull(fieldName);
        Objects.requireNonNull(clazz_);
        final Field field = clazz_.getField(fieldName);
        final boolean accessible = field.isAccessible();
        if (!accessible) {
            field.setAccessible(true);
        }
        final Object value = field.get(null);
        if (!accessible) {
            field.setAccessible(false);
        }
        return (T) value;
    }
    
    public static <T> T getStaticValue(Class<T> clazz, String fieldName, Class<?> clazz_, ToughConsumer<Throwable> failure) {
        try {
            return getStaticValue(clazz, fieldName, clazz_);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public static <T> T getStaticValueWithoutExeption(Class<T> clazz, String fieldName, Class<?> clazz_) {
        return getStaticValue(clazz, fieldName, clazz_, null);
    }
    
    public static Object callMethod(String methodName, Object object, Object... parameters) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return callMethod(null, methodName, object, parameters);
    }
    
    public static Object callMethod(String methodName, Object object, ToughConsumer<Throwable> failure, Object... parameters) {
        try {
            return callMethod(methodName, object, parameters);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public static Object callMethodWithoutException(String methodName, Object object, Object... parameters) {
        return callMethod(methodName, object, null, parameters);
    }
    
    public static <T> T callMethod(Class<T> clazz, String methodName, Object object, Object... parameters) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Objects.requireNonNull(methodName);
        Objects.requireNonNull(object);
        final Method method = object.getClass().getMethod(methodName, objectsToClasses(parameters));
        final boolean accessible = method.isAccessible();
        if (!accessible) {
            method.setAccessible(true);
        }
        final Object value = method.invoke(object, parameters);
        if (!accessible) {
            method.setAccessible(false);
        }
        return (T) value;
    }
    
    public static <T> T callMethod(Class<T> clazz, String methodName, Object object, ToughConsumer<Throwable> failure, Object... parameters) {
        try {
            return callMethod(clazz, methodName, object, parameters);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public static <T> T callMethodWithoutException(Class<T> clazz, String methodName, Object object, Object... parameters) {
        return callMethod(clazz, methodName, object, null, parameters);
    }
    
    public static Object callStaticMethod(String methodName, Class<?> clazz_, Object... parameters) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return callStaticMethod(null, methodName, clazz_, parameters);
    }
    
    public static Object callStaticMethod(String methodName, Class<?> clazz_, ToughConsumer<Throwable> failure, Object... parameters) {
        try {
            return callStaticMethod(methodName, clazz_, parameters);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public static Object callStaticMethodWithoutException(String methodName, Class<?> clazz_, Object... parameters) {
        return callStaticMethod(methodName, clazz_, null, parameters);
    }
    
    public static <T> T callStaticMethod(Class<T> clazz, String methodName, Class<?> clazz_, Object... parameters) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Objects.requireNonNull(methodName);
        Objects.requireNonNull(clazz_);
        final Method method = clazz_.getMethod(methodName, objectsToClasses(parameters));
        final boolean accessible = method.isAccessible();
        if (!accessible) {
            method.setAccessible(true);
        }
        final Object value = method.invoke(null, parameters);
        if (!accessible) {
            method.setAccessible(false);
        }
        return (T) value;
    }
    
    public static <T> T callStaticMethod(Class<T> clazz, String methodName, Class<?> clazz_, ToughConsumer<Throwable> failure, Object... parameters) {
        try {
            return callStaticMethod(clazz, methodName, clazz_, parameters);
        } catch (Exception ex) {
            if (failure != null) {
                failure.acceptWithoutException(ex);
            } else {
                logger.error(ex);
            }
            return null;
        }
    }
    
    public static <T> T callStaticMethodWithoutException(Class<T> clazz, String methodName, Class<?> clazz_, Object... parameters) {
        return callStaticMethod(clazz, methodName, clazz_, null, parameters);
    }
    
    public static Class<?>[] objectsToClasses(Object... objects) {
        return Stream.of(objects).map(Object::getClass).toArray(Class<?>[]::new);
    }
    
    public static Reflections reflectionsOfCompleteJar() {
        return new Reflections();
    }
    
    public static Reflections reflectionsOfPackage(Package package_) {
        return new Reflections(package_.getName());
    }
    
    public static <T> Set<Class<? extends T>> getSubClasses(Class<T> clazz) {
        return getSubClasses(clazz, reflectionsOfCompleteJar());
    }
    
    public static <T> Set<Class<? extends T>> getSubClasses(Class<T> clazz, Reflections reflections) {
        return reflections.getSubTypesOf(clazz);
    }
    
    public static Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        return getTypesAnnotatedWith(annotation, reflectionsOfCompleteJar());
    }
    
    public static Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation, Reflections reflections) {
        return reflections.getTypesAnnotatedWith(annotation);
    }
    
    public static Set<Class<?>> getTypesAnnotatedWith(Annotation annotation) {
        return getTypesAnnotatedWith(annotation, reflectionsOfCompleteJar());
    }
    
    public static Set<Class<?>> getTypesAnnotatedWith(Annotation annotation, Reflections reflections) {
        return reflections.getTypesAnnotatedWith(annotation);
    }
    
}
