package com.hwei.lib_common.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.hwei.lib_common.base.BaseViewHolder
import com.hwei.lib_common.listener.OnItemClickListener
import com.hwei.lib_common.listener.OnItemLongClickListener

abstract class BasePagingAdapter<VB : ViewDataBinding, T : Any>(
    callback: DiffUtil.ItemCallback<T>
) :
    PagingDataAdapter<T, BaseViewHolder<VB>>(callback) {

    private var onItemClickListener: OnItemClickListener<T>? = null
    private var onItemLongClickListener: OnItemLongClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding = DataBindingUtil.inflate<VB>(
            LayoutInflater.from(parent.context),
            setLayoutId(),
            parent,
            false
        )
        return BaseViewHolder(binding).apply {
            this.binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let { item ->
                    onItemClickListener?.onClick(it, item, absoluteAdapterPosition)
                }
            }
            this.binding.root.setOnLongClickListener {
                getItem(absoluteAdapterPosition)?.let { item ->
                    onItemLongClickListener?.onLongClick(it, item, absoluteAdapterPosition)
                } ?: false
            }
        }
    }


    @LayoutRes
    abstract fun setLayoutId(): Int

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<T>) {
        this.onItemLongClickListener = onItemLongClickListener
    }
}
