package com.pltech.study.android.launch_mode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wb.study.R

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("pl-LAUNCH", "Activity3->onCreate()")
        setContentView(R.layout.activity_third)
        val taskId = taskId
        Log.d("pl-LAUNCH", "ThirdActivity 所在任务栈id：$taskId")
    }

    override fun onStart() {
        super.onStart()
        Log.d("pl-LAUNCH", "Activity3->onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("pl-LAUNCH", "Activity3->onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("pl-LAUNCH", "Activity3->onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("pl-LAUNCH", "Activity3->onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("pl-LAUNCH", "Activity3->onDestroy()")
    }
}