package org.minetweak.util;

import org.minetweak.plugins.Plugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Useful Reflection Methods
 */
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
     *
     * @param annotation annotation class
     * @param field      field to check
     * @return if it exists
     */
    public static boolean annotationExists(Class<? extends Annotation> annotation, Field field) {
        return field.isAnnotationPresent(annotation);
    }

    /**
     * Checks if an Annotation Exists on a Class
     *
     * @param annotation annotation class
     * @param clazz      class to check
     * @return if it exists
     */
    public static boolean annotationExists(Class<? extends Annotation> annotation, Class<?> clazz) {
        return clazz.isAnnotationPresent(annotation);
    }

    /**
     * Checks if an Annotation Exists on a Method
     *
     * @param annotation annotation class
     * @param method     method to check
     * @return if it exists
     */
    public static boolean annotationExists(Class<? extends Annotation> annotation, Method method) {
        return method.isAnnotationPresent(annotation);
    }

    /**
     * Runs a Method if it exists
     *
     * @param object     Object
     * @param methodName Name of Method
     * @param args       arguments to pass
     * @return if the method was invoked
     */
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

    /**
     * Executes the first Method with the Given Annotation
     *
     * @param annotation Annotation
     * @param object     Object
     * @param args       Arguments to pass
     */
    public static void runFirstAnnotation(Class<? extends Annotation> annotation, Object object, Object... args) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (annotationExists(annotation, method)) {
                runIfExists(object, method.getName(), args);
                return;
            }
        }
    }

    /**
     * Returns the First Method that is annotated with the Given Annotation
     *
     * @param annotation Annotation
     * @param object     Object
     * @return method
     */
    public static Method getFirstAnnotatedMethod(Class<? extends Annotation> annotation, Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (annotationExists(annotation, method)) {
                return method;
            }
        }
        return null;
    }

    /**
     * Gets all Method annotated with the given annotation
     *
     * @param annotation Annotation
     * @param object     Object
     * @return list of methods
     */
    public static ArrayList<Method> getAnnotatedMethods(Class<? extends Annotation> annotation, Object object) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (annotationExists(annotation, method)) {
                methods.add(method);
            }
        }
        return methods;
    }

    /**
     * Executes a Plugin Handler Event on the given Object
     *
     * @param object Plugin Object
     * @param event  Event to Execute
     */
    public static void executePluginEvent(Object object, Object event) {
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

    /**
     * Checks if the parameter type is equal
     *
     * @param index  index of parameter
     * @param method method to check
     * @param clazz  class of parameter
     * @return if the parameter exists
     */
    public static boolean paramEquals(int index, Method method, Class<?> clazz) {
        return index < method.getParameterTypes().length && method.getParameterTypes()[index].equals(clazz);
    }
}
