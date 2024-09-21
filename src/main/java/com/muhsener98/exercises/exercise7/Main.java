package com.muhsener98.exercises.exercise7;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Locale;

public class Main {
    public static void main(String... args) throws NoSuchFieldException, IllegalAccessException {
        Address address1 = new Address("Dogu", 8);
        Address address2 = new Address("Ataturk", 13);
        Person person = new Person("Muhammet", 25, 41.227124, new Address[]{address1, address2}, new String[]{"Ahmet", "Mehmet"});
        System.out.println(objectToJson(person, 0));

    }


    private static String objectToJson(Object instance, int indentSize) throws IllegalAccessException {
        Field[] fields = instance.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();

        sb.append(indent(indentSize));
        sb.append("{");
        sb.append("\n");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (field.isSynthetic())
                continue;

            sb.append(indent(indentSize + 1));
            sb.append(formatStringValue(field.getName()));
            sb.append(":");

            if (field.getType().isPrimitive()) {
                sb.append(formatPrimitiveValue(field.get(instance), field.getType()));
            } else if (field.getType().equals(String.class))
                sb.append(formatStringValue(field.get(instance).toString()));
            else if (field.getType().isArray()) {
                sb.append(arrayToJson(field.get(instance), indentSize + 1));
            } else
                sb.append(objectToJson(field.get(instance), indentSize + 1));

            if (i != fields.length - 1)
                sb.append(",");

            sb.append("\n");

        }

        sb.append(indent(indentSize));
        sb.append("}");

        return sb.toString();
    }


    private static String indent(int indentSize) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentSize; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    private static String formatPrimitiveValue(Object instance, Class<?> type) throws IllegalAccessException {
        if (type.equals(boolean.class)
                || type.equals(short.class)
                || type.equals(int.class)
                || type.equals(long.class)) {
            return instance.toString();
        } else if (type.equals(double.class) || type.equals(float.class)) {

            return String.format(Locale.US, "%.2f", instance);
        } else if (type.equals(String.class))
            return formatStringValue(instance.toString());
        throw new RuntimeException(String.format("Type: %s is not supported", type.getName()));
    }

    private static String formatStringValue(String value) {
        return String.format("\"%s\"", value);
    }

    private static String arrayToJson(Object array, int indentSize) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        int length = Array.getLength(array);
        Class<?> componentType = array.getClass().getComponentType();
        sb.append("[");
        sb.append("\n");

        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);


            if (componentType.isPrimitive()) {
                sb.append(indent(indentSize + 1));
                sb.append(formatPrimitiveValue(element, componentType));
            } else if (componentType.equals(String.class)) {
                sb.append(indent(indentSize + 1));
                sb.append(formatStringValue(element.toString()));
            } else {
                sb.append(objectToJson(element, indentSize + 1));
            }

            if (i != length - 1)
                sb.append(",");

            sb.append("\n");
        }
        sb.append(indent(indentSize));
        sb.append("]");
        return sb.toString();
    }


}
