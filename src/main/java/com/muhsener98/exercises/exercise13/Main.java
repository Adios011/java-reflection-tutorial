package com.muhsener98.exercises.exercise13;

import com.muhsener98.exercises.exercise13.database.DatabaseClient;
import com.muhsener98.exercises.exercise13.http.HttpClient;
import com.muhsener98.exercises.exercise13.logging.FileLogger;
import com.muhsener98.exercises.exercise13.udp.UdpClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        DatabaseClient databaseClient = new DatabaseClient();
        HttpClient httpClient = new HttpClient("112.123.12.31");
        HttpClient httpClient1 = new HttpClient("34.11.23.12");
        FileLogger fileLogger = new FileLogger();
        UdpClient udpClient = new UdpClient();


        String requestBody = "request";
        Map<Object,Method> map = groupExecutors(
                List.of(databaseClient , httpClient1 , httpClient , fileLogger , udpClient),List.of(String.class));
        executeAll(map , requestBody);

    }

    private static void executeAll(Map<Object,Method> requestExecutors , String data) throws InvocationTargetException, IllegalAccessException {
        for(Map.Entry<Object, Method> entry : requestExecutors.entrySet()){
            Object requestExecutor = entry.getKey();
            Method method = entry.getValue();



            method.invoke(requestExecutor , data);
        }
    }

    private static Map<Object, Method> groupExecutors(List<Object> requestExecutors , List<Class<?>> methodParameterTypes ){
        Map<Object,Method> map = new HashMap<>();

        for(Object executor : requestExecutors){
            Method[] methods = executor.getClass().getDeclaredMethods();

            for(Method method : methods){
                if(Arrays.asList(method.getParameterTypes()).equals(methodParameterTypes))
                    map.put(executor , method);
            }

        }

        return map;
    }


    public static void testListEquals(List<String> list1 , List<String > list2){
        System.out.println("They are equals: " + (list1.equals(list2)));
    }

}
