package com.muhsener98.exercises.exercise11;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayFlattening {

    public static void main(String... args) {
        int[] result = concat(int.class, 1, 2, 3, 4, 5, new int[]{6, 7} , 9);
        System.out.println(Arrays.toString(result));
    }


        public static <T> T concat(Class<?> type, Object... arguments) {
            if (arguments.length == 0)
                return null;

            List<Object> elements = new ArrayList<>();
            for(Object arg : arguments){
                if(arg.getClass().isArray()){
                    for(int i = 0 ; i < Array.getLength(arg) ; i++){
                        elements.add(Array.get(arg , i));
                    }
                }else
                    elements.add(arg);
            }

            Object flattenedArray = Array.newInstance(type , elements.size());
            for(int i = 0 ; i < elements.size() ; i++){
                Array.set(flattenedArray , i , elements.get(i));
            }

            return (T) flattenedArray;

        }
}
