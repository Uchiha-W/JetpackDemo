package com.hwei.lib_base.util

import com.hwei.lib_base.BaseApplication
import java.io.File

object FileUtils {
    val file =
        File(BaseApplication.context.externalCacheDir?.absolutePath + File.separator + "aac.mp3")

    val file2 =
        File(BaseApplication.context.externalCacheDir?.absolutePath + File.separator + "h264.mp4")


    fun writeAudio(byteArray: ByteArray) {
        file.appendBytes(byteArray)
    }

    fun writeVideo(byteArray: ByteArray){
        file2.appendBytes(byteArray)
    }
}