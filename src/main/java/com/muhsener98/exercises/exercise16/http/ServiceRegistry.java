package com.muhsener98.exercises.exercise16.http;

import com.muhsener98.exercises.exercise16.annotation.InitializerClass;
import com.muhsener98.exercises.exercise16.annotation.InitializerMethod;

@InitializerClass
public class ServiceRegistry {

    @InitializerMethod
    public void registerService(){
        System.out.println("Service successfully registered");
    }
}
