package com.hwei.lib_base.util

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

object ScreenUtil {
    /**
     * 不包含状态栏导航栏
     */
    fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        return size.x
    }
    fun getScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        return size.y
    }
    /**
     * 包含状态栏导航栏
     */
    fun getRealScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        windowManager.defaultDisplay.getRealSize(size)
        return size.x
    }

    fun getRealScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        windowManager.defaultDisplay.getRealSize(size)
        return size.y
    }
}