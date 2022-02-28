package com.hwei.home

import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executors

class MockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mock)
    }

    private val stub = object : IMyAidlInterface.Stub() {
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {

        }

    }
}