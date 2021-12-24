package com.hwei.live.audio

import android.content.Context
import android.media.AudioRecord
import com.hwei.live.config.AudioConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AudioSource(context: Context) {

    private var audioRecord: AudioRecord
    private var bufferSize: Int

    private var coroutineScope = CoroutineScope(Dispatchers.IO)
    private var analyze: ((ByteArray) -> Unit)? = null
    private var audioAnalyze = AudioAnalyze()
    private var isRecording = false


    init {
        bufferSize = AudioRecord.getMinBufferSize(
            AudioConfig.SAMPLE_RATE,
            AudioConfig.CHANNEL_CONFIG,
            AudioConfig.AUDIO_FORMAT
        )
        audioRecord = AudioRecord(
            AudioConfig.AUDIO_SOURCE,
            AudioConfig.SAMPLE_RATE,
            AudioConfig.CHANNEL_CONFIG,
            AudioConfig.AUDIO_FORMAT,
            bufferSize,
        )
    }

    fun startRecord() {
        val state = audioRecord.state
        if (state == AudioRecord.STATE_UNINITIALIZED) {
            throw RuntimeException("audioRecord is not init")
        }
        if (isRecording) {
            return
        }
        audioRecord.startRecording()
        isRecording = true
        coroutineScope.launch(Dispatchers.IO) {
            while (isRecording) {
                if (audioRecord.state == AudioRecord.STATE_INITIALIZED) {
                    val byteArray = ByteArray(bufferSize)
                    val read = audioRecord.read(byteArray, 0, bufferSize)
                    if (read >= 0) {
                        audioAnalyze.enqueue(byteArray)
                    }
                }
            }
        }
        coroutineScope.launch(Dispatchers.IO) {
            while (isRecording) {
                if (audioRecord.state == AudioRecord.STATE_INITIALIZED) {
                    val byteArray = audioAnalyze.dequeue()
                    byteArray?.let {
                        analyze?.invoke(it)
                    }
                }
            }
        }
    }

    fun stopRecording() {
        audioRecord.stop()
        isRecording = false
    }

    fun analyze(action: (ByteArray) -> Unit) {
        this.analyze = action
    }

    fun release() {
        audioRecord.stop()
        audioRecord.release()
        isRecording = false
        coroutineScope.cancel()
    }

}
