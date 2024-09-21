package com.muhsener98.exercises.exercise16.databases;

import com.muhsener98.exercises.exercise16.annotation.InitializerClass;
import com.muhsener98.exercises.exercise16.annotation.InitializerMethod;
import com.muhsener98.exercises.exercise16.annotation.RetryOperation;

import java.io.IOException;

@InitializerClass
public class DatabaseConnection {
    private int failCounter = 5 ;

    @InitializerMethod
    @RetryOperation(numberOfRetries = 10,
            retryExceptions = IOException.class,
            durationBetweenRetriesMs = 1000,
            failureMessage = "Connection to database 1 failed after retry")
    public void connectToDatabase1() throws IOException {
        System.out.println("Connecting to database 1");
        if(failCounter > 0){
            failCounter--;
            throw new IOException("Connection failed");
        }

        System.out.println("Connection to database 1 succeeded");
    }

    @InitializerMethod
    public void connectToDatabase2(){
        System.out.println("Connection to database 2");
    }
}
