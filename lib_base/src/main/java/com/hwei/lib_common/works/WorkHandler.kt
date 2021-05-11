package com.hwei.lib_common.works

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class WorkHandler {
    fun enque(context: Context){
        val work = OneTimeWorkRequestBuilder<UploadWork>().build()
        WorkManager.getInstance(context).enqueue(work)
    }
}