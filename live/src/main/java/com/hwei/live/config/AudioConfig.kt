package com.hwei.live.config

import android.media.AudioFormat
import android.media.MediaRecorder

object AudioConfig {
    const val AUDIO_SOURCE = MediaRecorder.AudioSource.MIC
    const val MIME = "audio/mp4a-latm"
    const val SAMPLE_RATE = 44100
    const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO
    const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
    const val BIT_RATE = 96000
    const val CHANNEL_COUNT = 2
}