package com.hwei.lib_common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hwei.lib_common.base.BaseViewHolder
import com.hwei.lib_common.listener.OnItemClickListener
import com.hwei.lib_common.listener.OnItemLongClickListener

abstract class BaseAdapter<VB : ViewDataBinding, T : Any>(itemCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, RecyclerView.ViewHolder>(itemCallback), IMultiSupport {

    private var onItemClickListener: OnItemClickListener<T>? = null
    private var onItemLongClickListener: OnItemLongClickListener<T>? = null
    private var headerLayoutId: Int = View.NO_ID
    private var footerLayoutId: Int = View.NO_ID
    private var emptyLayoutId: Int = View.NO_ID

    private companion object {
        private const val type_header = 10000
        private const val type_normal = 10001
        private const val type_empty = 10002
        private const val type_footer = 10003
    }

    @LayoutRes
    abstract fun setLayoutId(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == type_header || viewType == type_footer || viewType == type_empty) {
            val viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                when (viewType) {
                    type_header -> {
                        headerLayoutId
                    }
                    type_footer -> {
                        footerLayoutId
                    }
                    else -> {
                        emptyLayoutId
                    }
                },
                parent,
                false
            )
            return BaseViewHolder(viewDataBinding)
        }

        val binding = if (isSupportMulti()) {
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                onCreateMultiViewBinding(parent, viewType),
                parent,
                false
            )
        } else {
            DataBindingUtil.inflate<VB>(
                LayoutInflater.from(parent.context),
                setLayoutId(),
                parent,
                false
            )
        }
        return BaseViewHolder(binding!!).apply {
            this.binding.root.setOnClickListener {
                val position =
                    if (haveHeader()) absoluteAdapterPosition - 1 else absoluteAdapterPosition
                onItemClickListener?.onClick(
                    it,
                    currentList[position],
                    position
                )
            }
            this.binding.root.setOnLongClickListener {
                val position =
                    if (haveHeader()) absoluteAdapterPosition - 1 else absoluteAdapterPosition
                onItemLongClickListener?.onLongClick(
                    it,
                    currentList[position],
                    position
                ) ?: false
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isShowEmptyView()) {
            onBindEmptyViewHolder(holder as BaseViewHolder<*>)
        } else if (position == 0 && haveHeader()) {
            onBindHeaderViewHolder(holder as BaseViewHolder<*>)
        } else if (position == itemCount - 1 && haveFooter()) {
            onBindFooterViewHolder(holder as BaseViewHolder<*>)
        } else {
            onBindExtendsViewHolder(
                holder as BaseViewHolder<VB>,
                if (haveHeader()) position - 1 else position
            )
        }
    }

    abstract fun onBindExtendsViewHolder(holder: BaseViewHolder<VB>, position: Int)

    /**
     * header布局绑定
     */
    open fun onBindHeaderViewHolder(headerViewHolder: BaseViewHolder<*>) {

    }

    /**
     * footer布局绑定
     */
    open fun onBindFooterViewHolder(footerViewHolder: BaseViewHolder<*>) {

    }

    /**
     * 空布局绑定
     */
    open fun onBindEmptyViewHolder(emptyViewHolder: BaseViewHolder<*>) {

    }

    override fun getItemCount(): Int {
        super.getItemCount()
        var size = currentList.size
        if (isShowEmptyView()) {
            return size + 1
        }
        if (haveHeader()) {
            size++
        }
        if (haveFooter()) {
            size++
        }
        return size
    }

    /**
     * 是否显示空布局
     */
    private fun isShowEmptyView(): Boolean {
        return currentList.size == 0 && emptyLayoutId != View.NO_ID
    }

    /**
     * 设置空布局
     */
    fun setEmptyView(emptyLayoutId: Int) {
        this.emptyLayoutId = emptyLayoutId
    }

    /**
     * 设置头布局
     */
    fun addHeaderView(headerLayoutId: Int) {
        this.headerLayoutId = headerLayoutId
    }

    fun removeHeaderView() {
        this.headerLayoutId = View.NO_ID
    }

    /**
     * 设置尾布局
     */
    fun addFooterView(footerLayoutId: Int) {
        this.footerLayoutId = footerLayoutId
    }

    fun removeFooterView() {
        this.footerLayoutId = View.NO_ID
    }

    private fun haveHeader() = headerLayoutId != View.NO_ID
    private fun haveFooter() = footerLayoutId != View.NO_ID

    override fun getItemViewType(position: Int): Int {
        if (isShowEmptyView()) {
            return type_empty
        }
        if (position == 0 && haveHeader()) {
            return type_header
        }
        if (position == itemCount - 1 && haveFooter()) {
            return type_footer
        }
        if (isSupportMulti()) {
            return getItemMultiViewType(if (haveHeader()) position - 1 else position)
        }
        return type_normal
    }


    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<T>) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    override fun isSupportMulti(): Boolean {
        return false
    }

    override fun getItemMultiViewType(position: Int): Int {
        return -1
    }

    override fun onCreateMultiViewBinding(parent: ViewGroup, viewType: Int): Int {
        return View.NO_ID
    }
}