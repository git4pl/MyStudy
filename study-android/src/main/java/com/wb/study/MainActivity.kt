package com.wb.study

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.wb.study.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test_view)
//        val mainBinding = setContentView(this, R.layout.activity_main) as ActivityMainBinding
//
//        val book = Book("Android 高质量编程指北", "Alex PL", 5)
//        mainBinding.setVariable(BR.book, book)
        //mainBinding.book = book

        //getVideoHeight(getLastImagePath())

        val test = "      "
        Log.d("TAG", "is test empty: " + TextUtils.isEmpty(test))
    }

}