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

    private companion object {
        private const val type_header = 10000
        private const val type_normal = 10001
        private const val type_footer = 10002
    }

    @LayoutRes
    abstract fun setLayoutId(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == type_header || viewType == type_footer) {
            val viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                if (viewType == type_header) headerLayoutId else footerLayoutId,
                parent,
                false
            )
            return BaseViewHolder(viewDataBinding)
        }

        val binding = if (isSupportMulti()) {
            onCreateMultiViewBinding(parent, viewType)
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
        if (position == 0 && haveHeader()) {
            onBindHeaderViewHolder(holder as BaseViewHolder<*>)
        } else if (position == itemCount - 1 && haveFooter()) {
            onBindFooterViewHolder(holder as BaseViewHolder<*>)
        } else if (isSupportMulti()) {
            onBindMultiViewHolder(
                holder as BaseViewHolder<*>,
                if (haveHeader()) position - 1 else position
            )
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


    override fun getItemCount(): Int {
        var size = currentList.size
        if (haveHeader()) {
            size++
        }
        if (haveFooter()) {
            size++
        }
        return size
    }

    fun addHeaderView(headerLayoutId: Int) {
        this.headerLayoutId = headerLayoutId
    }

    fun removeHeaderView() {
        this.headerLayoutId = View.NO_ID
    }

    fun addFooterView(footerLayoutId: Int) {
        this.footerLayoutId = footerLayoutId
    }

    fun removeFooterView() {
        this.footerLayoutId = View.NO_ID
    }

    private fun haveHeader() = headerLayoutId != View.NO_ID
    private fun haveFooter() = footerLayoutId != View.NO_ID

    override fun getItemViewType(position: Int): Int {
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

    override fun onCreateMultiViewBinding(parent: ViewGroup, viewType: Int): ViewDataBinding? {
        return null
    }

    override fun onBindMultiViewHolder(holder: BaseViewHolder<*>, position: Int) {

    }

}