package com.my.pack;

public class User {
    String name;

    public User(String name) {
        System.out.println("User constructor invoked.");
        this.name = name;
    }

    public String getName() {
        System.out.println("User getter invoked.");
        return name;
    }
}

