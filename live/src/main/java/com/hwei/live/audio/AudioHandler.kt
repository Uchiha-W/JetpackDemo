package com.hwei.live.audio

import android.content.Context
import android.util.Log
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.hwei.lib_base.util.FileUtils
import com.hwei.live.AudioUtils
import com.hwei.live.LiveInterface

class AudioHandler(context: Context):LiveInterface {

    private val audioSource = AudioSource(context)

    override fun initPreview(lifecycleOwner: LifecycleOwner, previewView: PreviewView) {

    }

    override fun startLive() {
        audioSource.startRecord()
        audioSource.analyze {

        }
    }

    override fun stopLive() {
        audioSource.stopRecording()
    }

    override fun release() {
        audioSource.release()
    }
}