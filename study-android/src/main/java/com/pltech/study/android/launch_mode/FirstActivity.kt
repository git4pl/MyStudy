package com.pltech.study.android.launch_mode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.wb.study.R

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        findViewById<Button>(R.id.btn_start_secActivity).setOnClickListener {
            startActivity(
                Intent(this, SecondActivity::class.java)
            )
        }
        val taskId = taskId
        Log.d("pl-LAUNCH", "FirstActivity 所在任务栈id：$taskId")
    }
}