package com.muhsener98.exercises.exercise16.databases;

import com.muhsener98.exercises.exercise16.annotation.InitializerClass;
import com.muhsener98.exercises.exercise16.annotation.InitializerMethod;

@InitializerClass
public class CacheLoader {

    @InitializerMethod
    public void loadCache(){
        System.out.println("Loading data from cache");
    }

    public void reloadCache(){
        System.out.println("Reload cache");

    }
}
