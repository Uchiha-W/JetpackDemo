package com.hwei.lib_base.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hwei.lib_base.adapter.IMultiSupport
import com.hwei.lib_base.base.BaseViewHolder
import com.hwei.lib_base.listener.OnItemClickListener
import com.hwei.lib_base.listener.OnItemLongClickListener
import kotlinx.coroutines.withContext

abstract class BasePagingAdapter<VB : ViewDataBinding, T : Any>(
    callback: DiffUtil.ItemCallback<T>
) :
    PagingDataAdapter<T, RecyclerView.ViewHolder>(callback), IMultiSupport {


    private var onItemClickListener: OnItemClickListener<T>? = null
    private var onItemLongClickListener: OnItemLongClickListener<T>? = null

    private var mShowEmptyView = true
    private var emptyLayoutId = View.NO_ID

    private companion object {
        private const val type_normal = 10001
        private const val type_empty = 10002
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == type_empty) {
            val binding: ViewDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                emptyLayoutId,
                parent,
                false
            )
            return BaseViewHolder(binding)
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindExtendsViewHolder(holder as BaseViewHolder<VB>, position)
    }

    abstract fun onBindExtendsViewHolder(holder: BaseViewHolder<VB>, position: Int)

    override fun getItemViewType(position: Int): Int {
        if (mShowEmptyView) {
            return type_empty
        }
        if (isSupportMulti()) {
            return getItemMultiViewType(position)
        }
        return type_normal
    }

    override fun getItemCount(): Int {
        if (isShowEmptyView()) {
            mShowEmptyView = true
            return 1
        }
        mShowEmptyView = false
        return super.getItemCount()
    }

    /**
     * 是否显示空布局
     */
    private fun isShowEmptyView(): Boolean {
        return super.getItemCount() == 0 && emptyLayoutId != View.NO_ID
    }

    /**
     * 设置空布局
     */
    fun setEmptyView(emptyLayoutId: Int) {
        this.emptyLayoutId = emptyLayoutId
    }

    @LayoutRes
    abstract fun setLayoutId(): Int

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
