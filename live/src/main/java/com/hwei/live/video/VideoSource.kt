package com.hwei.live.video

import android.content.Context
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoSource(private val context: Context) {
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraProvider: ProcessCameraProvider
    private var imageCapture: ImageCapture? = null
    private var preview: Preview? = null
    private var cameraSelector: CameraSelector? = null
    private var lengthFace = CameraSelector.LENS_FACING_FRONT
    private var camera: Camera? = null
    private val videoAnalyze = VideoAnalyze()
    private var coroutineScope = CoroutineScope(Dispatchers.IO)
    private var recording = false

    fun bind(lifecycleOwner: LifecycleOwner, previewView: PreviewView) {
        cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider, lifecycleOwner, previewView)
        }, ContextCompat.getMainExecutor(context))
    }

    private fun bindPreview(
        cameraProvider: ProcessCameraProvider,
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
    ) {
        imageCapture = ImageCapture.Builder()
            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
        preview = Preview.Builder().build()
        cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lengthFace)
            .build()
        cameraProvider.unbindAll()
        camera = cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector!!,
            videoAnalyze.imageAnalysis,
            imageCapture,
            preview
        )
        preview?.setSurfaceProvider(previewView.surfaceProvider)
    }

    fun startRecording(action: (ByteArray) -> Unit) {
        if (recording) {
            return
        }
        recording = true
        coroutineScope.launch(Dispatchers.IO) {
            while (recording) {
                videoAnalyze.start()
            }
            videoAnalyze.stop()
        }
        coroutineScope.launch(Dispatchers.IO) {
            while (recording) {
                val byteArray = videoAnalyze.dequeue()
                byteArray?.let(action)
            }
        }
    }

    fun stopRecording() {
        recording = false
    }

}