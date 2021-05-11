package com.hwei.lib_common.widge

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.hwei.lib_common.R
import com.hwei.lib_common.databinding.LayoutCommonTitleBinding

class CommonTitle : RelativeLayout {
    private lateinit var binding: LayoutCommonTitleBinding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, -1)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        init(attributeSet)
    }

    private fun init(attributeSet: AttributeSet?) {
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

}