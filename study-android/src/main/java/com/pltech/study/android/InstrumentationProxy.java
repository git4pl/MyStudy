package com.pltech.study.android;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class InstrumentationProxy extends Instrumentation {

    private static final String TAG = "InstrumentationProxy";
    public final static String REQUEST_TARGET_INTENT_NAME = "request_target_intent_name";
    private Instrumentation mInstrumentation;
    private PackageManager mPackageManager;

    public InstrumentationProxy(Instrumentation instrumentation, PackageManager packageManager) {
        this.mInstrumentation = instrumentation;
        this.mPackageManager = packageManager;
    }

    public InstrumentationProxy(Instrumentation instance) {
        this.mInstrumentation = instance;
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        super.callActivityOnCreate(activity, icicle);
        Log.d(TAG, activity.getClass().getName() + ": OnCreate() executed.");
    }

    @Override
    public void callActivityOnRestart(Activity activity) {
        super.callActivityOnRestart(activity);
        Log.d(TAG, activity.getClass().getName() + ": OnRestart() executed.");
    }

    @Override
    public void callActivityOnStart(Activity activity) {
        super.callActivityOnStart(activity);
        Log.d(TAG, activity.getClass().getName() + ": OnStart() executed.");
    }

    @Override
    public void callActivityOnResume(Activity activity) {
        super.callActivityOnResume(activity);
        Log.d(TAG, activity.getClass().getName() + ": OnResume() executed.");
    }

    @Override
    public void callActivityOnPause(Activity activity) {
        super.callActivityOnPause(activity);
        Log.d(TAG, activity.getClass().getName() + ": OnPause() executed.");
    }

    @Override
    public void callActivityOnStop(Activity activity) {
        super.callActivityOnStop(activity);
        Log.d(TAG, activity.getClass().getName() + ": OnStop() executed.");
    }

    @Override
    public void callActivityOnDestroy(Activity activity) {
        super.callActivityOnDestroy(activity);
        Log.d(TAG, activity.getClass().getName() + ": OnDestroy() executed.");
    }

//    public ActivityResult execStartActivity(
//            Context who, IBinder contextThread, IBinder token, Activity target,
//            Intent intent, int requestCode, Bundle options) {
//
//        List<ResolveInfo> resolveInfo = mPackageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL);
//        //判断启动的插件Activity是否在AndroidManifest.xml中注册过
//        if (null == resolveInfo || resolveInfo.size() == 0) {
//            //保存目标插件
//            intent.putExtra(REQUEST_TARGET_INTENT_NAME, intent.getComponent().getClassName());
//            //设置为占坑Activity
//            intent.setClassName(who, "com.glh.haiproject01.StubActivity");
//        }
//
//        try {
//            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
//                    Context.class, IBinder.class, IBinder.class, Activity.class,
//                    Intent.class, int.class, Bundle.class);
//            return (ActivityResult) execStartActivity.invoke(mInstrumentation, who, contextThread, token, target, intent, requestCode, options);
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException,
//            IllegalAccessException, ClassNotFoundException {
//        String intentName = intent.getStringExtra(REQUEST_TARGET_INTENT_NAME);
//        if (!TextUtils.isEmpty(intentName)) {
//            return super.newActivity(cl, intentName, intent);
//        }
//        return super.newActivity(cl, className, intent);
//    }

}
