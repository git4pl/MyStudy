package com.pltech.study.kotlin

import kotlinx.coroutines.*
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

/**
 * Kotlin 协程学习
 * Created by Pang Li on 2020/11/5
 */

object CoroutineNotes {
    @JvmStatic
    fun main1(args: Array<String>) {
        println("Come on!!")
        GlobalScope.launch {
            //delay(500)
            println("World!")
            val start = System.currentTimeMillis()
            val count = AtomicLong()
            for (i in 0..100000L) {
                count.addAndGet(i)
            }
            println("after coroutine run 1 billion times, count=$count")
            println("cost time: ${System.currentTimeMillis() - start}")
        }
        print("Hello ")

        val start = System.currentTimeMillis()
        val count = AtomicLong()
        for (i in 0..100000L) {
            thread(true) {
                count.addAndGet(i)
            }
        }
        println("after thread run 1 million times, count=$count")
        println("cost time: ${System.currentTimeMillis() - start}")

        //Thread.sleep(100)
    }

    @JvmStatic
    fun main2(args: Array<String>) = runBlocking { // this: CoroutineScope
        launch { // 在 runBlocking 作用域中启动一个新协程
            delay(1000L)
            println("World!")
        }
        println("Hello,")
    }

    @JvmStatic
    fun main3(args: Array<String>) = runBlocking {
        val job = GlobalScope.launch { //在全局作用域中启动一个新协程
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        job.join()
    }

    @JvmStatic
    fun main4(args: Array<String>) {
        GlobalScope.launch { // 在后台启动一个新的协程并继续
            delay(1000L)
            println("World!")
        }
        println("Hello,") // 主线程中的代码会立即执行
        runBlocking {     // 但是这个表达式阻塞了主线程
            delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
        }
        //Thread.sleep(1012) //与上面的runBlocking作用相同；主线程至少要延迟1012ms，协程中的工作才能执行完
    }

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        repeat(100000) {
            launch {
                delay(1000)
                print(" ,")
            }
        }
    }

    @JvmStatic
    fun main6(args: Array<String>) {
        repeat(100000) {
            Thread {
                //sleep(1000)
                print(" ,")
            }.start()
        }
    }
}
