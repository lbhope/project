package com.example.mykotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sum = add(1, 5)
        printSum(12,23)
    }


    fun add(a: Int, b: Int) = a + b
    fun printSum(a: Int, b: Int): Unit {
        print(a + b)
    }
}