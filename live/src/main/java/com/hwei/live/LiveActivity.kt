package com.hwei.live

import androidx.activity.result.contract.ActivityResultContracts
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.lib_base.base.BaseBindingActivity
import com.hwei.lib_base.router.LiveRouter
import com.hwei.live.databinding.ActivityLiveBinding

@Route(path = LiveRouter.live)
class LiveActivity : BaseBindingActivity<ActivityLiveBinding>() {

    private val liveClient = LiveClient(this)

    override fun initView() {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                liveClient.initPreview(this, binding.preview)
            }
        }.launch(android.Manifest.permission.RECORD_AUDIO)
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                liveClient.initPreview(this, binding.preview)
            }
        }.launch(android.Manifest.permission.CAMERA)

        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                liveClient.initPreview(this, binding.preview)
            }
        }.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    }

    override fun initData() {
        binding.btnStart.setOnClickListener {
            liveClient.startLive()
        }
        binding.btnStop.setOnClickListener {
            liveClient.stopLive()
        }
    }

    override fun setEvent() {

    }

    override fun setLayoutId(): Int {
        return R.layout.activity_live
    }

}