package com.hwei.live.video

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import androidx.camera.core.AspectRatio
import androidx.camera.core.ImageAnalysis
import com.hwei.live.config.VideoConfig
import java.util.concurrent.Executors

class VideoAnalyze {

    var imageAnalysis: ImageAnalysis? = null
    private val executor = Executors.newSingleThreadExecutor()
    private var codec: MediaCodec = MediaCodec.createEncoderByType(VideoConfig.MIME)

    private var recording = false

    init {
        val format =
            MediaFormat.createVideoFormat(VideoConfig.MIME, VideoConfig.WIDTH, VideoConfig.HEIGHT)
        format.setInteger(MediaFormat.KEY_BIT_RATE, VideoConfig.BIT_RATE)
        format.setInteger(MediaFormat.KEY_FRAME_RATE, VideoConfig.FRAME_RATE)
        format.setInteger(
            MediaFormat.KEY_COLOR_FORMAT,
            MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible
        )
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, VideoConfig.FRAME_INTERVAL)

        codec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        codec.start()

        imageAnalysis = ImageAnalysis.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis?.setAnalyzer(executor, ImageAnalysis.Analyzer {
            val buffer = it.planes[0].buffer.run {
                rewind()
                val data = ByteArray(remaining())
                get(data)
                data
            }
            if (recording) {
                encode(buffer)
            }
            it.close()
        })
    }

    fun start() {
        recording = true
    }

    fun stop() {
        recording = false
    }

    private fun encode(byteArray: ByteArray) {
        val index = codec.dequeueInputBuffer(0)
        if (index >= 0) {
            val inputBuffer = codec.getInputBuffer(index)
            inputBuffer?.clear()
            inputBuffer?.put(byteArray)
            codec.queueInputBuffer(index, 0, byteArray.size, System.currentTimeMillis(), 0)
        }
    }

    fun dequeue(): ByteArray? {
        val bufferInfo = MediaCodec.BufferInfo()
        val outputIndex = codec.dequeueOutputBuffer(bufferInfo, 0)
        if (outputIndex >= 0) {
            val outputBuffer = codec.getOutputBuffer(outputIndex)
            outputBuffer?.let {
                val output = ByteArray(it.remaining())
                it.get(output, 0, output.size)
                codec.releaseOutputBuffer(outputIndex, false)
                return output
            }
        } else if (outputIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {

        }
        return null
    }
}