package com.muhsener98.exercises.exercise17;

import com.muhsener98.exercises.exercise2.A;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Throwable {

        SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder(List.of("1" , "2") , 1 , "products" , List.of("name" , "price"));
        String sqlQuery = execute(sqlQueryBuilder);
        System.out.println(sqlQuery);

    }


    private static Map<String, Field> getInputToField(Class<?> clazz){
        Map<String,Field> map = new HashMap<>();
        for(Field field : clazz.getDeclaredFields()){
            Input input = field.getAnnotation(Input.class);
            if(input != null)
                map.put(input.value() , field);
        }

        return map;
    }

    @SuppressWarnings("unchecked")
    private static <T> T execute(Object instance) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = instance.getClass();
        Map<String, Method> operationToMethod = getOperationNameToMethod(clazz);
        Method finalResultMethod = findFinalResultMethod(clazz);
        Map<String,Field> inputToField= getInputToField(clazz);

        return (T) executeWithDependencies(instance, finalResultMethod, operationToMethod , inputToField);
    }

    public static Object executeWithDependencies(Object instance, Method currentMethod, Map<String, Method> operationToMethod,
                                                 Map<String,Field> inputToField) throws InvocationTargetException, IllegalAccessException {
        List<Object> parameterValues = new ArrayList<>(currentMethod.getParameterCount());

        for (Parameter parameter : currentMethod.getParameters()) {
            Object value = null;
            if (parameter.isAnnotationPresent(DependsOn.class)) {
                String dependencyOperationName = parameter.getAnnotation(DependsOn.class).value();
                Method dependencyMethod = operationToMethod.get(dependencyOperationName);

                value = executeWithDependencies(instance, dependencyMethod, operationToMethod , inputToField);
            }else if(parameter.isAnnotationPresent(Input.class)){
                String inputName = parameter.getAnnotation(Input.class).value();
                Field inputField = inputToField.get(inputName);
                inputField.setAccessible(true);
                value = inputField.get(instance);
            }
            parameterValues.add(value);
        }

        return currentMethod.invoke(instance, parameterValues.toArray());
    }


    private static Map<String, Method> getOperationNameToMethod(Class<?> clazz) {
        Map<String, Method> map = new HashMap<>();
        for (Method method : clazz.getDeclaredMethods()) {
            Operation operation = method.getAnnotation(Operation.class);
            if (operation != null)
                map.put(operation.value(), method);
        }

        return map;
    }


    private static Method findFinalResultMethod(Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(FinalResult.class))
                return method;
        }
        throw new RuntimeException(String.format("Class: %s does not have any final result method", clazz.getSimpleName()));
    }


    private static void approach1() {
        BestGameFinder bestGameFinder = new BestGameFinder();
        Set<String> games = bestGameFinder.getAllGames();
        Map<String, Float> gamesToPrice = bestGameFinder.getGameToPrice(games);
        Map<String, Float> gamesToRating = bestGameFinder.getGameToRating(games);

        SortedMap<Double, String> gameScores = bestGameFinder.scoreGames(gamesToPrice, gamesToRating);
        List<String> gamesInDescendingOrder = bestGameFinder.getTopGames(gameScores);

        System.out.println(gamesInDescendingOrder);
    }
}
