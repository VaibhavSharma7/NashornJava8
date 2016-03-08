package com.my.pack;

public class User {
    String firstName;
    String lastName;

    //Single parametrized constructor
    public User(String firstName) {
        System.out.println("Single parametrized constructor invoked.");
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        System.out.println("User getter invoked.");
        return firstName+" "+lastName;
    }
}

