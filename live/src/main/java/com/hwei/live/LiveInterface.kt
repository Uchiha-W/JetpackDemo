package com.hwei.live

import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

interface LiveInterface {

    /**
     * 初始化
     */
    fun initPreview(lifecycleOwner: LifecycleOwner, previewView: PreviewView)

    /**
     * 开启直播
     */
    fun startLive()

    /**
     * 停止直播
     */
    fun stopLive()

    /**
     * 释放资源
     */
    fun release()

}