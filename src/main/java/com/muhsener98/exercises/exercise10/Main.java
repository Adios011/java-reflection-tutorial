package com.muhsener98.exercises.exercise10;

import com.muhsener98.exercises.exercise2.F;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Employee employee = readAndCreateObject(
                "/Users/muhammetsener/Desktop/roadmap-projects/java-reflection-tutorial/src/main/resources/application.properties",
                Employee.class
        );

        System.out.println(employee);
    }


    public static <T> T readAndCreateObject(String filePath, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(new File(filePath)));
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = (T) constructor.newInstance();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
//            System.out.println(line);
            if (!line.startsWith(clazz.getSimpleName().toLowerCase()))
                continue;

            String lineWithoutPrefix = line.split("\\.")[1];
//            System.out.println(lineWithoutPrefix);
            String[] keyValue = lineWithoutPrefix.split("=");
            String key = keyValue[0];
            String value = keyValue[1];
            System.out.println("key -> " + key);
            System.out.println("value -> " + value);

            Field field;
            try {
                field = clazz.getDeclaredField(key);
            } catch (NoSuchFieldException e) {
                System.err.println("there is no such field: " + key);
                continue;
            }
            field.setAccessible(true);

            Object parsedValue;

            if (field.getType().isArray()) {
                parsedValue = parseArray(field.getType().getComponentType(), value);
            } else
                parsedValue = parseValue(field.getType(), value);

            field.set(instance, parsedValue);


        }

        return instance;
    }


    private static Object parseArray(Class<?> componentType, String value) {
        String[] elements = value.split(",");
        Object arrayObject = Array.newInstance(componentType, elements.length);

        for (int i = 0; i < elements.length; i++) {
            Array.set(arrayObject, i, elements[i]);
        }

        return arrayObject;

    }

    private static Object parseValue(Class<?> valueType, String value) {
        if (valueType.equals(String.class))
            return value;
        else if (valueType.equals(int.class))
            return Integer.parseInt(value);
        else if (valueType.equals(double.class))
            return Double.parseDouble(value);
        else if (valueType.equals(boolean.class))
            return Boolean.parseBoolean(value);
        else
            throw new IllegalStateException("unsupported type: " + valueType.getSimpleName());

    }


}
