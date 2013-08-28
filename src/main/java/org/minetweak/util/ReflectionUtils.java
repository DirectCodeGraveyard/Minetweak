package org.minetweak.util;

import org.minetweak.plugins.Plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

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

    public static void runFirstAnnotation(Class<? extends Annotation> annotation, Object object, Object... args) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (annotationExists(annotation, method)) {
                runIfExists(object, method.getName(), args);
                return;
            }
        }
    }

    public static Method getFirstAnnotatedMethod(Class<? extends Annotation> annotation, Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (annotationExists(annotation, method)) {
                return method;
            }
        }
        return null;
    }

    public static ArrayList<Method> getAnnotatedMethods(Class<? extends Annotation> annotation, Object object) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (annotationExists(annotation, method)) {
                methods.add(method);
            }
        }
        return methods;
    }

    public static void executeEvent(Object object, Object event) {
        ArrayList<Method> methods = getAnnotatedMethods(Plugin.Handler.class, object);
        for (Method method : methods) {
            if (paramEquals(0, method, event.getClass())) {
                try {
                    method.invoke(object, event);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean paramEquals(int index, Method method, Class<?> clazz) {
        return method.getParameterTypes().length >= (index + 1) && method.getParameterTypes()[index].equals(clazz);
    }
}
