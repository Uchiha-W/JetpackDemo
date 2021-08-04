package com.hwei.lib_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView

abstract class BaseComposeFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = requireContext()
        initView()
        initData()
        setEvent()
        return setComposeView()
    }

    abstract fun setComposeView(): ComposeView

    override fun setLayoutId(): Int {
        return View.NO_ID
    }
}