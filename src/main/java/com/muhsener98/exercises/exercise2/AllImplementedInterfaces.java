package com.muhsener98.exercises.exercise2;
import java.util.*;

public class AllImplementedInterfaces {


    public static void main(String... args){
        System.out.println(findAllImplementedInterfaces(A.class));
    }


    public static Set<Class<?>> findAllImplementedInterfaces(Class<?> input){
        Set<Class<?>> allImplementedInterfaces = new HashSet<>();
        Class<?>[] inputInterfaces = input.getInterfaces();
        for(Class<?> currentInterface : inputInterfaces){
            allImplementedInterfaces.add(currentInterface);
            allImplementedInterfaces.addAll(findAllImplementedInterfaces(currentInterface));
        }

        return allImplementedInterfaces;
    }
}
