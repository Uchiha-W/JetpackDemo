package com.hwei.lib_common.widge

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.text.SimpleDateFormat
import java.util.*

object AppCrashHandler : Thread.UncaughtExceptionHandler {

    private val crash_directory =
        Environment.getExternalStorageState() + File.separator + "crash" + File.separator

    fun init(context: Context) {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(p0: Thread, p1: Throwable) {
//        val path = crash_directory + SimpleDateFormat("HH:mm:ss").format(Date())
//        FileOutputStream(File(path)).use {
//            p1.printStackTrace(PrintStream(it))
//        }
    }
}