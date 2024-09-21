package com.muhsener98.exercises.exercise16;

import com.muhsener98.exercises.exercise16.annotation.InitializerClass;
import com.muhsener98.exercises.exercise16.annotation.InitializerMethod;

@InitializerClass
public class AutoSaver {

    @InitializerMethod
    public void startAutoSavingThreads(){
        System.out.println("Start automatic data saving disk");
    }
}
