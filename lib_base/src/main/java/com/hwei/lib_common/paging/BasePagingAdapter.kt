package com.hwei.lib_common.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagingAdapter<VB : ViewDataBinding, T : Any>(
    callback: DiffUtil.ItemCallback<T>
) :
    PagingDataAdapter<T, BaseViewHolder<VB>>(callback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = DataBindingUtil.inflate<VB>(
            LayoutInflater.from(parent.context),
            setLayoutId(),
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    @LayoutRes
    abstract fun setLayoutId(): Int

}


class BaseViewHolder<VB : ViewDataBinding>(val binding: VB) :
    RecyclerView.ViewHolder(binding.root) 