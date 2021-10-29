package com.hwei.lib_base.widge

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.databinding.DataBindingUtil
import com.hwei.lib_base.R
import com.hwei.lib_base.databinding.DialogCommonBinding
import com.hwei.lib_base.ktx.dp2px

class CommonDialog(context: Context) : Dialog(context, R.style.commonDialog) {

    private var binding: DialogCommonBinding =
        DataBindingUtil.inflate(layoutInflater, R.layout.dialog_common, null, false)

    private val width = 260.dp2px
    private val height = 140.dp2px

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun setTitle(text: String) {
        binding.tvTitle.text = text
    }

    fun setContent(text: String) {
        binding.tvContent.text = text
    }

    fun setOnConfirmListener(text: String = "confirm", block: () -> Unit) {
        binding.tvConfirm.text = text
        binding.tvConfirm.setOnClickListener {
            block.invoke()
        }
    }

    fun setOnCancelListener(text: String = "cancel", block: () -> Unit) {
        binding.tvCancel.text = text
        binding.tvCancel.setOnClickListener {
            block.invoke()
        }
    }

    override fun show() {
        super.show()
        window?.let {
            val param = it.attributes
            param.width = width
            param.height = height
            param.gravity = Gravity.CENTER
            it.attributes = param
        }
    }
}