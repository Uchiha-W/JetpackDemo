package com.hwei.lib_base.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.hwei.lib_base.util.ActivityUtil

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtil.add(this)
        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun setEvent()

    override fun onDestroy() {
        super.onDestroy()
        ActivityUtil.remove(this)
    }
}