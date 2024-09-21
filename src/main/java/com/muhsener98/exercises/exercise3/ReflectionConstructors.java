package com.muhsener98.exercises.exercise3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectionConstructors {

    public static void main(String... args) {

//        printAllConstructors(Post.class);
//        printConstructorBasedOnParameterTypes(Post.class, int.class);
        Post instance = (Post) createInstanceWithArguments(Post.class, "deneme" , 10);
        System.out.println(instance);
    }


    public static void printAllConstructors(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("constructor name -> " + constructor.getName());
            System.out.println("parameter count -> " + constructor.getParameterCount());
        }

    }


    public static void printConstructorBasedOnParameterTypes(Class<?> clazz, Class<?>... parameterType) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(parameterType);
            System.out.println(constructor);
        } catch (NoSuchMethodException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public static Object createInstanceWithArguments(Class<?> clazz, Object... args) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.getParameterCount() == args.length) {
                try {
                    return constructor.newInstance(args);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    System.err.println("IllegalAccessException -> " + e.getMessage());
                } catch (InvocationTargetException e) {
                    System.err.println("Exception thrown by constructor -> " + e.getCause().getMessage());
                }
            }
        }

        return null;
    }


}
