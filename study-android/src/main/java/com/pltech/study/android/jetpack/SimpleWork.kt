package com.pltech.study.android.jetpack

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleWork(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val data = inputData.getString("data")
        Log.d("WorkManager", "收到任务${data}, 执行完毕.")
        if ("Task3" == data) {
            val reply =
                Data.Builder().putString("reply", "Yes, I've got the task and finished it.").build()
            return Result.success(reply)
        }
        return Result.success()
    }
}