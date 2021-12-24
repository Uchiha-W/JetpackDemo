package com.hwei.live.audio

import android.media.*
import com.hwei.lib_base.util.FileUtils
import com.hwei.live.config.AudioConfig
import com.hwei.live.AudioUtils
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class AudioAnalyze {

    private var codec: MediaCodec = MediaCodec.createEncoderByType(AudioConfig.MIME)
    private var bufferSize: Int

    init {
        bufferSize = AudioRecord.getMinBufferSize(
            AudioConfig.SAMPLE_RATE,
            AudioConfig.CHANNEL_CONFIG,
            AudioConfig.AUDIO_FORMAT
        )
        val format = MediaFormat.createAudioFormat(AudioConfig.MIME, AudioConfig.SAMPLE_RATE, AudioConfig.CHANNEL_COUNT)
        format.setString(MediaFormat.KEY_MIME, AudioConfig.MIME)
        format.setInteger(MediaFormat.KEY_BIT_RATE, AudioConfig.BIT_RATE)
        format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, AudioConfig.CHANNEL_COUNT)
        format.setInteger(MediaFormat.KEY_SAMPLE_RATE, AudioConfig.SAMPLE_RATE)
        format.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 500 * bufferSize)
        format.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC)
        codec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        codec.start()
    }

    suspend fun enqueue(byteArray: ByteArray){
        val index = codec.dequeueInputBuffer(-1)
        if (index >= 0) {
            val inputBuffer = codec.getInputBuffer(index)
            inputBuffer?.clear()
            inputBuffer?.put(byteArray)
            codec.queueInputBuffer(index, 0, byteArray.size, System.currentTimeMillis(), 0)
        }

    }

    fun dequeue(): ByteArray? {
        val bufferInfo = MediaCodec.BufferInfo()
        val outputIndex = codec.dequeueOutputBuffer(bufferInfo, -1)
        if (outputIndex >= 0) {
            val outputBuffer = codec.getOutputBuffer(outputIndex)
            outputBuffer?.let {
                val output = ByteArray(it.remaining()+7)
                it.get(output, 7, bufferInfo.size)
                it.clear()
                AudioUtils.addADTS(output)
                FileUtils.writeAudio(output)
                codec.releaseOutputBuffer(outputIndex, false)
                return output
            }
        } else if (outputIndex == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {

        }
        return null
    }

}