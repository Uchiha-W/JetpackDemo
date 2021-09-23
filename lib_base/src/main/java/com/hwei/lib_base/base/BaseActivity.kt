package com.hwei.lib_base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hwei.lib_base.util.ActivityUtil

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityUtil.add(this)
        initSaveInstanceState(savedInstanceState)
        initView()
        initData()
    }

    abstract fun initView()

    abstract fun initData()

    abstract fun setEvent()

    open fun initSaveInstanceState(savedInstanceState: Bundle?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityUtil.remove(this)
    }
}