package com.muhsener98.exercises.exercise7;

public class Person {
    private String name;
    private int age ;
    private double accountAmount ;
    private String[] friends;

    private Address[] addresses;

    public Person(String name, int age, double accountAmount, Address[] address, String[] friends) {
        this.name = name;
        this.age = age;
        this.accountAmount = accountAmount;
        this.addresses = address;
        this.friends = friends;
    }
}
