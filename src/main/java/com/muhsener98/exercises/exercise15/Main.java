package com.muhsener98.exercises.exercise15;

import com.muhsener98.exercises.exercise15.annotation.MySimpleAnnotation;

import java.lang.reflect.Field;

@MySimpleAnnotation
public class Main {

    @MySimpleAnnotation
    private String aField;

    public static void main(String[] args) throws NoSuchFieldException {
        Class<?> clazz = Main.class;
        Field field = clazz.getDeclaredField("aField");
        boolean isPresent = field.isAnnotationPresent(MySimpleAnnotation.class);
        System.out.println("isAnnotationPresent: " + isPresent);
    }
}
