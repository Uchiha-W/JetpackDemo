package com.hwei.lib_common.camera

import android.annotation.SuppressLint
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.common.util.concurrent.ListenableFuture
import com.hwei.lib_common.R
import com.hwei.lib_common.base.BaseActivity
import com.hwei.lib_common.databinding.LayoutCameraBinding
import com.hwei.lib_common.ktx.showToast
import com.hwei.lib_common.router.BaseRouter
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

@Route(path = BaseRouter.camera)
class CameraActivity : BaseActivity<LayoutCameraBinding>() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraProvider: ProcessCameraProvider
    private val executor = Executors.newSingleThreadExecutor()

    private var camera: Camera? = null
    private var imageCapture: ImageCapture? = null
    private var videoCapture: VideoCapture? = null
    private var preview: Preview? = null
    private var imageAnalysis: ImageAnalysis? = null
    private var cameraSelector: CameraSelector? = null
    private var lengthFace = CameraSelector.LENS_FACING_FRONT

    override fun setLayoutId(): Int {
        return R.layout.layout_camera
    }

    override fun initView() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    @SuppressLint("RestrictedApi")
    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        imageCapture = ImageCapture.Builder()
            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
        videoCapture = VideoCapture.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
        preview = Preview.Builder().build()
        cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lengthFace)
            .build()
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
            //todo: analyze buffer
            it.close()
        })
        cameraProvider.unbindAll()
        //imageAnalysis 和 videoCapture 不能同时使用
        camera = cameraProvider.bindToLifecycle(
            this,
            cameraSelector!!,
            imageCapture,
            videoCapture,
            preview
        )
        preview?.setSurfaceProvider(binding.preview.surfaceProvider)
    }

    @SuppressLint("MissingPermission", "RestrictedApi")
    override fun initData() {
        binding.btnTake.setOnClickListener {
            val metadata = ImageCapture.Metadata().apply {
                isReversedHorizontal = lengthFace == CameraSelector.LENS_FACING_FRONT
            }
            val outPutOption =
                ImageCapture.OutputFileOptions.Builder(
                    File(
                        externalCacheDir?.absolutePath + File.separator + SimpleDateFormat("HH:mm:ss").format(
                            Date()
                        ) + ".jpg"
                    )
                )
                    .setMetadata(metadata)
                    .build()
            imageCapture?.takePicture(
                outPutOption,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        lifecycleScope.launch {
                            showToast("保存成功")
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {

                    }
                })
        }
        binding.btnRecord.setOnClickListener {
            if (binding.btnRecord.text == "停止") {
                videoCapture?.stopRecording()
                binding.btnRecord.text = "录像"
                return@setOnClickListener
            }
            binding.btnRecord.text = "停止"
            val outputOptions =
                VideoCapture.OutputFileOptions.Builder(File(externalCacheDir?.absolutePath + File.separator + "123.mp4"))
                    .setMetadata(VideoCapture.Metadata())
                    .build()
            videoCapture?.startRecording(
                outputOptions,
                executor,
                object : VideoCapture.OnVideoSavedCallback {
                    override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
                        lifecycleScope.launch {
                            showToast("保存成功")
                        }
                    }

                    override fun onError(
                        videoCaptureError: Int,
                        message: String,
                        cause: Throwable?
                    ) {

                    }
                })
        }
        binding.btnReverse.setOnClickListener {
            lengthFace = if (lengthFace == CameraSelector.LENS_FACING_FRONT) {
                CameraSelector.LENS_FACING_BACK
            } else {
                CameraSelector.LENS_FACING_FRONT
            }
            bindPreview(cameraProvider)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onDestroy() {
        videoCapture?.stopRecording()
        executor.shutdown()
        super.onDestroy()
    }
}