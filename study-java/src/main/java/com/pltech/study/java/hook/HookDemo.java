package com.pltech.study.java.hook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 学习Java hook技术
 * Created by Pang Li on 2020/12/30
 */
public class HookDemo {

    public static void main(String[] args) {

        MyClass myClass = new MyClass();
        try {
            Field field = MyClass.class.getDeclaredField("testClass");
            field.setAccessible(true);

            OtherClass otherClass = new OtherClass();
            field.set(myClass, otherClass);

            Field field1 = MyClass.class.getDeclaredField("proxyInterface");
            field1.setAccessible(true);

            ProxyInterface proxyInterface = (ProxyInterface) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                    new Class[]{ProxyInterface.class}, new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) {
                            System.out.println("这是代理的对象，method = " + method);
                            return null;
                        }
                    });
            field1.set(myClass, proxyInterface);
            myClass.testClass.testMethod();
            myClass.proxyInterface.test();
            myClass.proxyInterfaceImpl = new ProxyInterfaceImpl();
            myClass.proxyInterfaceImpl.test();

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static class MyClass {
        private MyTestClass testClass;
        private ProxyInterface proxyInterface;
        private ProxyInterfaceImpl proxyInterfaceImpl;
    }
}
