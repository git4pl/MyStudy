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
        Log.d("pl-LAUNCH", "Activity2->onCreate()")
        setContentView(R.layout.activity_second)
        findViewById<Button>(R.id.btn_start_thiActivity).setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
        val taskId = taskId
        Log.d("pl-LAUNCH", "SecondActivity 所在任务栈id：$taskId")
    }

    override fun onStart() {
        super.onStart()
        Log.d("pl-LAUNCH", "Activity2->onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("pl-LAUNCH", "Activity2->onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("pl-LAUNCH", "Activity2->onPause() before sleep.")
        Thread.sleep(3000)
        Log.d("pl-LAUNCH", "Activity2->onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("pl-LAUNCH", "Activity2->onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("pl-LAUNCH", "Activity2->onDestroy()")
    }
}