package com.pltech.study.java.hook;

/**
 * Created by Pang Li on 2020/12/30
 */
public class ProxyInterfaceImpl implements ProxyInterface {
    @Override
    public void test() {
        System.out.println("This is the implementation of ProxyInterface.");
    }
}
