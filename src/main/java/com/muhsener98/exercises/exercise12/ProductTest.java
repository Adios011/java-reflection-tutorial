package com.muhsener98.exercises.exercise12;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ProductTest {

    public static void main(String[] args) {
        getAllFieldsRecursively(ClothingProduct.class)
                .forEach(field -> System.out.println(field.getName()));

    }


    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();

        Class<?> temp = clazz;
        while (temp != null) {
            fieldList.addAll(List.of(temp.getDeclaredFields()));
            temp = temp.getSuperclass();
        }

        return fieldList;

    }


    private static void testSetters(Class<?> clazz) {
        List<Field> fields = getAllFields(clazz);
        for (Field field : fields) {
            String setterName = "set" + capitalizeFirstLetter(field.getName());

            Method setterMethod = null;
            try {
                setterMethod = clazz.getMethod(setterName, field.getType());
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(String.format("Setter: %s() not found", setterName));
            }

            if (!setterMethod.getReturnType().equals(void.class))
                throw new IllegalStateException(String.format("Setter: %s() has  to return void", setterName));

        }

    }

    private static void testGetters(Class<?> clazz) {
        List<Field> fields = getAllFields(clazz);
        Map<String, Method> methods = mapMethodNameToMethod(clazz);

        for (Field field : fields) {
            String getterName = "get" + capitalizeFirstLetter(field.getName());
            if (!methods.containsKey(getterName))
                throw new IllegalStateException(String.format("Field : %s does not have a getter method", field.getName()));

            Method getter = methods.get(getterName);
            if (!getter.getReturnType().equals(field.getType()))
                throw new IllegalStateException(String.format("Getter method: %s() has return type %s but expected %s",
                        getterName, getter.getReturnType().getTypeName(), field.getType().getTypeName()));

            if (getter.getParameterCount() > 0)
                throw new IllegalStateException(String.format("Getter: %s() has %d parameters", getterName, getter.getParameterCount()));

        }
    }

    private static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase().concat(str.substring(1));
    }


    private static Map<String, Method> mapMethodNameToMethod(Class<?> clazz) {
        Method[] allMethods = clazz.getMethods();

        Map<String, Method> map = new HashMap<>();
        for (Method method : allMethods) {
            map.put(method.getName(), method);
        }

        return map;
    }

    private static void printMethods(Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            System.out.println(method.getName());
        }
    }


    private static List<Field> getAllFieldsRecursively(Class<?> clazz) {
        if (clazz == null || clazz.equals(Object.class))
            return Collections.emptyList();

        Field[] currentClassFields = clazz.getDeclaredFields();
        List<Field> inheritedFields = getAllFieldsRecursively(clazz.getSuperclass());

        List<Field> allFields = new ArrayList<>();
        allFields.addAll(Arrays.asList(currentClassFields));
        allFields.addAll(inheritedFields);

        return allFields;




    }
}
