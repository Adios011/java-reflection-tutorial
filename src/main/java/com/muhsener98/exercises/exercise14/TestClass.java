package com.muhsener98.exercises.exercise14;

public class TestClass {

    public static void beforeClass(){
        System.out.println("beforeClass() was invoked");
    }

    public void setupTest(){
        System.out.println("setupTest() was invoked.");
    }

    public void testA(){
        System.out.println("testA() was invoked.");
    }


    public void testB(){
        System.out.println("testB() was invoked.");
    }

    public static void afterClass(){
        System.out.println("afterClass() was invoked");
    }


}
