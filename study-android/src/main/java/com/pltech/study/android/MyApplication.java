package com.pltech.study.android;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //hookActivityThreadInstrumentation();
        hookInstrumentation();
    }

    private void hookActivityThreadInstrumentation() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field activityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            activityThreadField.setAccessible(true);
            //获取ActivityThread对象sCurrentActivityThread
            Object activityThread = activityThreadField.get(null);

            Field instrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            instrumentationField.setAccessible(true);
            //从sCurrentActivityThread中获取成员变量mInstrumentation
            Instrumentation instrumentation = (Instrumentation) instrumentationField.get(activityThread);
            //创建代理对象InstrumentationProxy
            InstrumentationProxy proxy = new InstrumentationProxy(instrumentation, getPackageManager());
            //将sCurrentActivityThread中成员变量mInstrumentation替换成代理类InstrumentationProxy
            instrumentationField.set(activityThread, proxy);
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void hookInstrumentation() {
        try {
            Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
            Method method = activityThreadClazz.getDeclaredMethod("currentActivityThread");
            method.setAccessible(true);
            Object activityThreadObj = method.invoke(null);

            Field fieldInstrumentation = activityThreadClazz.getDeclaredField("mInstrumentation");
            fieldInstrumentation.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) fieldInstrumentation.get(activityThreadObj);

            InstrumentationProxy proxy = new InstrumentationProxy(instrumentation);
            fieldInstrumentation.set(activityThreadObj, proxy);
        } catch (ClassNotFoundException | NoSuchMethodException
                | IllegalAccessException | InvocationTargetException
                | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
