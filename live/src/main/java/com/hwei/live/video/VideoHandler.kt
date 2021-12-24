package com.hwei.live.video

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.hwei.lib_base.util.FileUtils
import com.hwei.live.LiveInterface

class VideoHandler(context: Context) : LiveInterface {

    private val videoSource: VideoSource = VideoSource(context)

    private val videopacktor = VideoPacketor()

    private val videoSender = VideoSender()

    override fun initPreview(lifecycleOwner: LifecycleOwner, previewView: PreviewView) {
        videoSource.bind(lifecycleOwner, previewView)
    }

    override fun startLive() {
        videoSource.startRecording {
            FileUtils.writeVideo(it)
        }
    }

    override fun stopLive() {
        videoSource.stopRecording()
    }

    override fun release() {

    }

}