package com.muhsener98.exercises.exercise9;

import java.lang.reflect.Array;

public class Main {
    public static void main(String[] args){
        int[] oneDimensionalArray = {1,2,3,4,5};
        int[][] twoDimensionalArray = {{1,2,3},{4,5,6}};
        inspectArrayValues(twoDimensionalArray);
    }

    public static void inspectArrayObject(Object arrayObject){
        Class<?> clazz = arrayObject.getClass();
        System.out.println(String.format("Is array: %s" , clazz.isArray()));
        System.out.println(String.format("This is an array of type : %s" , clazz.getComponentType()));
    }

    public static void inspectArrayValues(Object arrayObject){
        Class<?> clazz = arrayObject.getClass();
        if(!clazz.isArray()){
            System.err.println("Argument is not an array!");
            return;
        }

        System.out.print("[");
        int length = Array.getLength(arrayObject);
        for(int i = 0 ; i < length ; i++){
            Object element = Array.get(arrayObject , i);

            if(element.getClass().isArray())
                inspectArrayValues(element);
            else
                System.out.print(element);

            if(i != length -1)
                System.out.print(", ");
        }

        System.out.print("]");


    }
}
