package com.wb.study;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Hook Android View 的OnClick事件
 * Created by Pang Li on 5/16/21
 */
public class HookSetOnClickListener {
    public static void hook(final View view) {
        try {
            // 步骤1：获取到View原理注册的listener变量
            // 反射执行View类的getListenerInfo()方法，拿到v的mListenerInfo对象，这个对象就是点击事件的持有者
            @SuppressLint("DiscouragedPrivateApi")
            Method method = View.class.getDeclaredMethod("getListenerInfo");
            //getListenerInfo()方法不是public的，需要设置访问权限
            method.setAccessible(true);
            //这里拿到的就是mListenerInfo对象，即点击事件的持有者
            Object mListenerInfo = method.invoke(view);
            //从这里拿到当前点击事件的对象
            @SuppressLint("PrivateApi")
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field field = listenerInfoClz.getDeclaredField("mOnClickListener");
            final View.OnClickListener clickListenerInstance = (View.OnClickListener) field.get(mListenerInfo);
            //步骤2：新建一个代理类，该代理类执行onClick的时候会在处理被代理类这个方法的同时去执行我们的逻辑
            //步骤3：把新建的代理类放回到View中
            field.set(mListenerInfo, new ProxyOnClickListener(clickListenerInstance));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 静态代理实现
     */
    static class ProxyOnClickListener implements View.OnClickListener {

        private final View.OnClickListener originalListener;

        ProxyOnClickListener(View.OnClickListener listener) {
            this.originalListener = listener;
        }

        @Override
        public void onClick(View v) {
            Log.d("HookSetOnClickListener", "点击事件被hook到了");
            if (originalListener != null) {
                originalListener.onClick(v);
            }
        }
    }
}
