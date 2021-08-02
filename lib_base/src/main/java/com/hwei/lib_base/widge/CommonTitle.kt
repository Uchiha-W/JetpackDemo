package com.hwei.lib_base.widge

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.hwei.lib_base.R
import com.hwei.lib_base.databinding.LayoutCommonTitleBinding


class CommonTitle @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = -1
) :
    RelativeLayout(context, attributeSet, defStyleAttr) {
    private lateinit var binding: LayoutCommonTitleBinding

    init {
        initView()
        initData(attributeSet)
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_common_title,
            this,
            true
        )
    }

    private fun initData(attributeSet: AttributeSet?) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.CommonTitle)

        binding.tvTitle.apply {
            text = typeArray.getString(R.styleable.CommonTitle_title)
        }

        binding.tvRight.apply {
            text = typeArray.getString(R.styleable.CommonTitle_text_right)
        }

        binding.ivBack.apply {
            setOnClickListener {
                if (context is Activity) {
                    (context as Activity).finish()
                }
            }
        }
        typeArray.recycle()
    }

    fun setLeftClickListener(onClickListener: OnClickListener) {
        binding.ivBack.setOnClickListener(onClickListener)
    }

    fun setTvRight(text: String = "", onClickListener: OnClickListener? = null) {
        if (text.isNotEmpty()) {
            binding.tvRight.text = text
        }
        binding.tvRight.setOnClickListener(onClickListener)
    }

    fun setMiddleText(text: String) {
        binding.tvTitle.text = text
    }

}