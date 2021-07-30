package com.hwei.lib_base.util

import android.os.Handler
import android.os.Looper
import androidx.annotation.FloatRange
import com.hwei.lib_base.BaseApplication
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

object DownloadManager {

    private val filepath = BaseApplication.context.externalCacheDir?.absolutePath
        ?: BaseApplication.context.cacheDir.absolutePath

    private val requestList = Collections.synchronizedList<String>(ArrayList())

    private val handler = Handler(Looper.getMainLooper())

    private val okHttpClient = OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(300, TimeUnit.SECONDS)
        .writeTimeout(300, TimeUnit.SECONDS)
        .build()

    fun download(url: String, fileName: String, onDownLoadListener: OnDownLoadListener) {
        if (requestList.contains(url)) {
            return
        }
        requestList.add(url)
        onDownLoadListener.onStart()
        okHttpClient.newCall(Request.Builder().url(url).build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post { onDownLoadListener.onFailure(e) }
                requestList.remove(url)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    try {
                        val file = saveFile(response, fileName, onDownLoadListener)
                        handler.post { onDownLoadListener.onSuccess(file) }
                    } catch (e: Exception) {
                        handler.post { onDownLoadListener.onFailure(e) }
                    }
                } else {
                    handler.post { onDownLoadListener.onFailure(IOException("下载失败")) }
                }
                requestList.remove(url)
            }
        })
    }

    private fun saveFile(
        response: Response,
        fileName: String,
        onDownLoadListener: OnDownLoadListener
    ): File {
        response.body ?: throw NullPointerException("目标文件不存在")
        File(filepath).mkdirs()
        val file = File(filepath + File.separator + fileName)
        if (file.exists()) {
            file.delete()
        }
        val contentLength = response.body!!.contentLength()
        var currentLength = 0
        FileOutputStream(file).use { output ->
            response.body!!.byteStream().use { input ->
                val buffer = ByteArray(8 * 1024)
                var bytes = input.read(buffer)
                while (bytes >= 0) {
                    output.write(buffer, 0, bytes)
                    bytes = input.read(buffer)
                    currentLength += bytes
                    handler.post { onDownLoadListener.onProgress(currentLength * 1f / contentLength * 100) }
                }
            }
        }
        return file
    }
}

interface OnDownLoadListener {

    fun onStart()

    fun onProgress(@FloatRange(from = 0.0, to = 100.0) progress: Float)

    fun onFailure(e: Exception)

    fun onSuccess(file: File)
}