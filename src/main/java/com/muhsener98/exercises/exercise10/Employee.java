package com.muhsener98.exercises.exercise10;

import java.util.Arrays;

public class Employee {
    private String firstName;
    private String lastName;
    private int age ;
    private double salary;
    private String[] friends;

    private Employee(){

    }

    public Employee(String firstName, String lastName, int age, double salary, String[] friends) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", friends=" + Arrays.toString(friends) +
                '}';
    }
}
