package com.hwei.lib_common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.hwei.lib_common.listener.OnItemClickListener
import com.hwei.lib_common.listener.OnItemLongClickListener

abstract class BaseAdapter<VB : ViewDataBinding, T : Any> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val datas = mutableListOf<T>()
    private var onItemClickListener: OnItemClickListener<T>? = null
    private var onItemLongClickListener: OnItemLongClickListener<T>? = null
    private var headerLayoutId: Int = View.NO_ID
    private var footerLayoutId: Int = View.NO_ID

    private companion object {
        private const val type_header = 0
        private const val type_normal = 1
        private const val type_footer = 2
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
            return HeaderOrFooterViewHolder(viewDataBinding)
        }

        val binding = DataBindingUtil.inflate<VB>(
            LayoutInflater.from(parent.context),
            setLayoutId(),
            parent,
            false
        )
        return BaseViewHolder(binding).apply {
            this.binding.root.setOnClickListener {
                val position =
                    if (haveHeader()) absoluteAdapterPosition - 1 else absoluteAdapterPosition
                onItemClickListener?.onClick(
                    it,
                    datas[position],
                    position
                )
            }
            this.binding.root.setOnLongClickListener {
                val position =
                    if (haveHeader()) absoluteAdapterPosition - 1 else absoluteAdapterPosition
                onItemLongClickListener?.onLongClick(
                    it,
                    datas[position],
                    position
                ) ?: false
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0 && haveHeader()) {
            onBindHeaderViewHolder(holder as HeaderOrFooterViewHolder<*>)
        } else if (position == itemCount - 1 && haveFooter()) {
            onBindFooterViewHolder(holder as HeaderOrFooterViewHolder<*>)
        } else {
            onBindExtendsViewHolder(
                holder as BaseViewHolder<VB>,
                if (haveHeader()) position - 1 else position
            )
        }
    }

    abstract fun onBindExtendsViewHolder(holder: BaseViewHolder<VB>, position: Int)


    open fun onBindHeaderViewHolder(holderOrFooter: HeaderOrFooterViewHolder<*>) {

    }

    open fun onBindFooterViewHolder(holderOrFooter: HeaderOrFooterViewHolder<*>) {

    }

    override fun getItemCount(): Int {
        var size = datas.size
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
        return type_normal
    }

    fun setData(list: MutableList<T>) {
        this.datas.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addData(list: MutableList<T>) {
        this.datas.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<T>) {
        this.onItemLongClickListener = onItemLongClickListener
    }
}