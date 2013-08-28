package org.minetweak.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {
    /**
     * Checks if a Class Exists
     *
     * @param name Class name
     * @return if class exists
     */
    public static boolean classExists(String name) {
        try {
            Class.forName(name);
        } catch (ClassNotFoundException ignored) {
            return false;
        }
        return true;
    }

    /**
     * Checks if an Annotation Exists on a Field
     * @param annotation annotation class
     * @param field field to check
     * @return if it exists
     */
    public static boolean annotationExists(Class<? extends Annotation> annotation, Field field) {
        return field.isAnnotationPresent(annotation);
    }

    /**
     * Checks if an Annotation Exists on a Class
     * @param annotation annotation class
     * @param clazz class to check
     * @return if it exists
     */
    public static boolean annotationExists(Class<? extends Annotation> annotation, Class<?> clazz) {
        return clazz.isAnnotationPresent(annotation);
    }

    /**
     * Checks if an Annotation Exists on a Method
     * @param annotation annotation class
     * @param method method to check
     * @return if it exists
     */
    public static boolean annotationExists(Class<? extends Annotation> annotation, Method method) {
        return method.isAnnotationPresent(annotation);
    }

    public static boolean runIfExists(Object object, String methodName, Object... args) {
        try {
            Method method = object.getClass().getMethod(methodName);
            method.invoke(object, args);
        } catch (NoSuchMethodException e) {
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
        return true;
    }

    public static void runFirstAnnotation(Class<? extends Annotation> annotation, Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (annotationExists(annotation, method)) {
                runIfExists(object, method.getName());
                return;
            }
        }
    }
}
