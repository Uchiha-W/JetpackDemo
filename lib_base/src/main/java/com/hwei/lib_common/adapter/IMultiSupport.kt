package com.hwei.lib_common.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.hwei.lib_common.base.BaseViewHolder

interface IMultiSupport {

    /**
     * 是否支持多布局
     */
    fun isSupportMulti():Boolean

    /**
     * 多布局viewType
     */
    fun getItemMultiViewType(position: Int): Int

    /**
     * 在这里创建多布局
     */
    fun onCreateMultiViewBinding(parent: ViewGroup, viewType: Int): ViewDataBinding?

    /**
     * 多布局绑定数据
     */
    fun onBindMultiViewHolder(holder: BaseViewHolder<*>, position: Int)
}