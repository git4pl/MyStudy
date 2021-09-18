package com.pltech.study.android.launch_mode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Button
import com.wb.study.R

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("pl-LAUNCH", "Activity1->onCreate()")
        var visitGlobalLayout = false
        val viewTreeObserver = window.decorView.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //if (visitGlobalLayout) return
                visitGlobalLayout = true
                Handler(Looper.getMainLooper()).post {
                    println("pl-LAUNCH，首帧")
                    //viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })
        setContentView(R.layout.activity_first)
        findViewById<Button>(R.id.btn_start_secActivity).setOnClickListener {
            startActivity(
                Intent(this, SecondActivity::class.java)
            )
        }
        val taskId = taskId
        Log.d("pl-LAUNCH", "FirstActivity 所在任务栈id：$taskId")
    }

    override fun onStart() {
        super.onStart()
        Log.d("pl-LAUNCH", "Activity1->onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("pl-LAUNCH", "Activity1->onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("pl-LAUNCH", "Activity1->onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("pl-LAUNCH", "Activity1->onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("pl-LAUNCH", "Activity1->onDestroy()")
    }
}