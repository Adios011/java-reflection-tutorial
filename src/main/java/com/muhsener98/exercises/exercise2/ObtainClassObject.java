package com.muhsener98.exercises.exercise2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObtainClassObject {


    public static void main(String... args) throws ClassNotFoundException {
        Class<String> clazz = String.class;
        Map<String,Object> map = new HashMap<>();
        boolean bol = false;
        Class<?> hashMapClass = map.getClass();

        List<String> names = new ArrayList<>();
        Integer ten = 10;

        Drawable drawable = new Drawable() {
            @Override
            public int getCornerNumbers() {
                return 10 ;
            }
        };

        printClassInfo(clazz , int.class , boolean.class, Class.forName("com.muhsener98.exercises.ObtainClassObject$Square") ,
                drawable.getClass() ,
                Drawable.class,
                hashMapClass,
                Map.class,
                boolean.class,
                names.getClass(),
                ten.getClass()

        );

    }


    public static void printClassInfo(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            System.out.println("Class name -> " + clazz.getName());
            System.out.println("Package name -> " + clazz.getPackageName());
            System.out.println("Simple name -> " + clazz.getSimpleName());
            System.out.println("Canonical name -> " + clazz.getCanonicalName());
            System.out.println("is Anonymous -> " + clazz.isAnonymousClass());
            System.out.println("is Primitive -> " + clazz.isPrimitive());
            System.out.println("is member class -> " + clazz.isMemberClass());
            System.out.println("is Interface -> " + clazz.isInterface());
            System.out.println("is Array -> " + clazz.isArray());
            System.out.println("is Enum -> " + clazz.isEnum());

            Class<?>[] implementedClasses = clazz.getInterfaces();
            for (Class<?> implementedClass : implementedClasses) {
                System.out.println("implements -> " + implementedClass.getSimpleName());
            }


            System.out.println();
            System.out.println();

        }
    }


    private static class Square implements Drawable{
        public int getCornerNumbers(){
            return 4 ;
        }
    }

    private static interface Drawable{
        int getCornerNumbers();
    }

    private enum Color{
        BLUE,
        RED,
        GREEN;
    }
}
