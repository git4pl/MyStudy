package com.wb.study;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCustomView extends View {
    private static final String TAG = "CustomView";

    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent event: " + event.getAction());
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "Action Down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Action Move");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "Action Cancel");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Action Up");
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "dispatchTouchEvent event: " + event.getAction());
        return super.dispatchTouchEvent(event);
    }
}
