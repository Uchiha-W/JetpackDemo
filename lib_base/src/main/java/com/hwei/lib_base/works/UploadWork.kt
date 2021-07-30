package com.hwei.lib_base.works

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class UploadWork(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        try {
            //do something
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }

}