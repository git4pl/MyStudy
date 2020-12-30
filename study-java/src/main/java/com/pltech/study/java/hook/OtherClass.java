package com.pltech.study.java.hook;

/**
 * Created by Pang Li on 2020/12/30
 */
public class OtherClass extends MyTestClass {
    @Override
    public void testMethod() {
        System.out.println("HaHaHa, I'm invoked before MyTestClass been invoked.");
        super.testMethod();
    }
}
