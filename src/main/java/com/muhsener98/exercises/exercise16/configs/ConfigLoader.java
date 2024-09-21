package com.muhsener98.exercises.exercise16.configs;

import com.muhsener98.exercises.exercise16.annotation.InitializerClass;
import com.muhsener98.exercises.exercise16.annotation.InitializerMethod;

@InitializerClass
public class ConfigLoader {

    @InitializerMethod
    public void loadAllConfigs( ){
        System.out.println("Loading all configuration files");
    }
}
