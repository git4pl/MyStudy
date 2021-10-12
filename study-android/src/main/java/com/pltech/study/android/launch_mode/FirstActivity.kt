package com.pltech.study.android.launch_mode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.work.*
import com.pltech.study.android.jetpack.PeriodicWork
import com.pltech.study.android.jetpack.SimpleWork
import com.wb.study.R
import java.util.*
import java.util.concurrent.TimeUnit

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("pl-LAUNCH", "Activity1->onCreate()")
        var visitGlobalLayout = false
        val viewTreeObserver = window.decorView.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener {
            //if (visitGlobalLayout) return
            visitGlobalLayout = true
            Handler(Looper.getMainLooper()).post {
                println("pl-LAUNCH，首帧")
                //viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        }
        setContentView(R.layout.activity_first)
        findViewById<Button>(R.id.btn_start_secActivity).setOnClickListener {
//            startActivity(
//                Intent(this, SecondActivity::class.java)
//            )
            testWorkManager()
        }
        val taskId = taskId
        Log.d("pl-LAUNCH", "FirstActivity 所在任务栈id：$taskId")
    }

    private fun testWorkManager() {
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(SimpleWork::class.java)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)

        val workRequests: ArrayList<OneTimeWorkRequest?> = ArrayList<OneTimeWorkRequest?>()
        val workRequest1 = OneTimeWorkRequest.Builder(SimpleWork::class.java)
            .setInputData(Data.Builder().putString("data", "Task1").build())
            .setInitialDelay(2, TimeUnit.MINUTES)
            .build()
        val workRequest2 = OneTimeWorkRequest.Builder(SimpleWork::class.java)
            .setInputData(Data.Builder().putString("data", "Task2").build())
            .build()
        val workRequest3 = OneTimeWorkRequest.Builder(SimpleWork::class.java)
            .setInputData(Data.Builder().putString("data", "Task3").build())
            .build()
        workRequests.add(workRequest1)
        workRequests.add(workRequest2)
        //workRequests[2] = workRequest3
        WorkManager.getInstance(this)
            .getWorkInfoByIdLiveData(workRequest3.id)
            .observe(this, {
                if (it.state.isFinished) {
                    val msg  = it.outputData
                    Log.e("WorkManager", "收到回复的消息：${msg.getString("reply")}")
                }
            })

        val workRequest4 =
            PeriodicWorkRequest.Builder(PeriodicWork::class.java, 15, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(this).beginWith(workRequests).then(workRequest3).enqueue()
        WorkManager.getInstance(this).enqueue(workRequest4)
        //WorkManager.getInstance(this).cancelAllWork()
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