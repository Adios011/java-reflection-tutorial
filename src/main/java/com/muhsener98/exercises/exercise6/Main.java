package com.muhsener98.exercises.exercise6;

import com.muhsener98.exercises.exercise2.F;

import java.io.File;
import java.lang.reflect.Field;

public class Main {

    public static void main(String... args) {
        printDeclaredFieldsOf(Movie.class);
    }


    public static void printDeclaredFieldsOf(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            System.out.printf("Field name: %s --- Field Type: %s%n", field.getName(), field.getType().getName());
            System.out.println("is synthetic -> " + field.isSynthetic());
            System.out.println();

        }
    }


    public static class Movie {
        private static final double MINIMUM_PRICE = 10.99;
        private String name;
        private double price;
        private CATEGORY category;

        public Movie(String name, double price, CATEGORY category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }
    }

    public static enum CATEGORY {
        ADVENTURE, COMEDY;
    }
}
