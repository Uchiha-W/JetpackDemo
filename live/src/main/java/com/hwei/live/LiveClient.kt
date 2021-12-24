package com.hwei.live

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.hwei.live.audio.AudioHandler
import com.hwei.live.video.VideoHandler


class LiveClient(context: Context) : LiveInterface {

    private val videoHandler = VideoHandler(context)
    private val audioHandler = AudioHandler(context)

    override fun initPreview(lifecycleOwner: LifecycleOwner, previewView: PreviewView) {
        videoHandler.initPreview(lifecycleOwner, previewView)

    }

    override fun startLive() {
        videoHandler.startLive()
        audioHandler.startLive()
    }

    override fun stopLive() {
        videoHandler.stopLive()
        audioHandler.stopLive()
    }

    override fun release() {

    }

}