package com.muhsener98.exercises.exercise5;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DependencyInjection {

    public static void main(String... args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        createObjectRecursively(Class.forName("com.muhsener98.exercises.exercise5.somepackage.Class1"));
    }

    @SuppressWarnings("unchecked")
    public static <T> T createObjectRecursively(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = getFirstConstructor(clazz);
        List<Object> constructorArguments = new ArrayList<>();

        // If class has no-arg constructor, recursion will terminate.
        for (Class<?> argumentType : constructor.getParameterTypes()) {
            Object argumentValue = createObjectRecursively(argumentType);
            constructorArguments.add(argumentValue);
        }

        constructor.setAccessible(true);
        return (T) constructor.newInstance(constructorArguments.toArray());
    }


    private static Constructor<?> getFirstConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 0)
            throw new IllegalStateException(String.format("No constructor has been found for class %s", clazz.getName()));

        return constructors[0];
    }
}
