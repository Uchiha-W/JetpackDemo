package com.hwei.lib_common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hwei.lib_common.util.ActivityUtil

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtil.add(this)
        binding = DataBindingUtil.setContentView(this, setLayoutId())
        initView()
        initData()
    }

    abstract fun initData()

    abstract fun initView()

    @LayoutRes
    abstract fun setLayoutId(): Int


    override fun onDestroy() {
        super.onDestroy()
        ActivityUtil.remove(this)
    }
}