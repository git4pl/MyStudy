package com.pltech.study.java;

import org.openjdk.jol.info.ClassLayout;

public class TestObjectSize {
    Obj[] arr = new Obj[7];

    public static void main(String[] args) throws Exception {
        Obj a = new Obj();
        ClassLayout classLayout = ClassLayout.parseInstance(a);
        System.out.println(classLayout.toPrintable());
    }
}

class Obj {
    int i = 1;
    double j = 0.21;
    String str = "dadf";
}