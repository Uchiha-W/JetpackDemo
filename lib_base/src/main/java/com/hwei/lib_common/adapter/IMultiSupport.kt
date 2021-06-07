package com.hwei.lib_common.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes

interface IMultiSupport {

    /**
     * 是否支持多布局
     */
    fun isSupportMulti(): Boolean

    /**
     * 多布局viewType
     */
    fun getItemMultiViewType(position: Int): Int

    /**
     * 在这里创建多布局
     */
    @LayoutRes
    fun onCreateMultiViewBinding(parent: ViewGroup, viewType: Int): Int
}