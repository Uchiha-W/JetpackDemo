package com.hwei.lib_base.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingActivity<T : ViewDataBinding> : BaseActivity() {
    lateinit var binding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, setLayoutId())
        super.onCreate(savedInstanceState)
    }

    @LayoutRes
    abstract fun setLayoutId(): Int
}