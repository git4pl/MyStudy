package com.pltech.study.android;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;

import java.lang.reflect.Field;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        hookActivityThreadInstrumentation();
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
}
