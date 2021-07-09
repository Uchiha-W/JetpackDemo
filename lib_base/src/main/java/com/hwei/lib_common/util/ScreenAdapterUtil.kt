package com.hwei.lib_common.util

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle

/**
 * 屏幕适配类
 */
object ScreenAdapterUtil {

    private var display: Display? = null

    data class Display(
        var widthPixels: Int,
        var heightPixels: Int,
        var density: Float,
        var densityDpi: Int,
        var scaledDensity: Float,
    )

    fun setUp(application: Application, designWidthDp: Int, designHeightDp: Int) {
        val displayMetrics = application.resources.displayMetrics
        if (display == null) {
            display = Display(
                displayMetrics.widthPixels,
                displayMetrics.heightPixels,
                displayMetrics.density,
                displayMetrics.densityDpi,
                displayMetrics.scaledDensity
            )
        }
        application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                display?.scaledDensity = application.resources.displayMetrics.scaledDensity
            }

            override fun onLowMemory() {

            }

        })
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                adapt(activity, designWidthDp, designHeightDp)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    private fun adapt(context: Context, widthDp: Int, heightDp: Int) {
        display?.let {
            val density = it.widthPixels / widthDp
            val densityDpi = 160 * density
            val scaledDensity = it.scaledDensity / it.density * density
            val displayMetrics = context.resources.displayMetrics
            displayMetrics.densityDpi = densityDpi
            displayMetrics.scaledDensity = scaledDensity
            displayMetrics.density = density.toFloat()
        }
    }
}