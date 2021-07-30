package com.hwei.lib_base.widge

import android.annotation.SuppressLint
import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("StaticFieldLeak")
object AppCrashHandler : Thread.UncaughtExceptionHandler {

    private lateinit var mContext: Context

    lateinit var crash_directory: String

    fun init(context: Context) {
        mContext = context.applicationContext
        crash_directory =
            mContext.externalCacheDir?.absolutePath + File.separator + "crash" + File.separator
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(p0: Thread, p1: Throwable) {
        val path = crash_directory
        val file = File(path)
        file.mkdirs()
        FileOutputStream(File(path + SimpleDateFormat("HH:mm:ss").format(Date()) + ".txt")).use {
            p1.printStackTrace(PrintStream(it))
        }
    }
}