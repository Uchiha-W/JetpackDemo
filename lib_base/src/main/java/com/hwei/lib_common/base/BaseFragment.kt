package com.hwei.lib_common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T:ViewDataBinding> : Fragment() {
    lateinit var binding: T
    lateinit var mContext: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, setLayoutId(), container, false)
        mContext = requireContext()
        initView()
        initData()
        return binding.root
    }

    abstract fun initData()

    abstract fun initView()

    fun startLoading(){

    }

    fun stopLoading(){}

    @LayoutRes
    abstract fun setLayoutId(): Int
}