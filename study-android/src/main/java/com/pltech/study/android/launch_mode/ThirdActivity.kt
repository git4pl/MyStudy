package com.pltech.study.android.launch_mode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wb.study.R

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        val taskId = taskId
        Log.d("pl-LAUNCH", "ThirdActivity 所在任务栈id：$taskId")
    }
}