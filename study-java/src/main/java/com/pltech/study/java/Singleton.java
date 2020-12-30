package com.pltech.study.java;

public class Singleton {

    private static Singleton instance = new Singleton();
    public static int count1;
    public static int count2 = 10;

    private Singleton() {
        count1++;
        count2++;
    }

    public static Singleton getInstance() {
        return instance;
    }
}
