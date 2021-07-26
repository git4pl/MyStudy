package com.pltech.study.android.launch_mode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.wb.study.R

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        findViewById<Button>(R.id.btn_start_thiActivity).setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
        val taskId = taskId
        Log.d("pl-LAUNCH", "SecondActivity 所在任务栈id：$taskId")
    }
}