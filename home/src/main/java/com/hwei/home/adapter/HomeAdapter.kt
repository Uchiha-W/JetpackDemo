package com.hwei.home.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.hwei.home.R
import com.hwei.home.bean.Article
import com.hwei.home.databinding.ItemHomeBinding
import com.hwei.lib_base.base.BaseViewHolder
import com.hwei.lib_base.paging.BaseMultiPagingAdapter
import javax.inject.Inject

class HomeAdapter @Inject constructor() :
    BaseMultiPagingAdapter<Article>(DIFF_CALL) {

    init {
        addMultiItem(1 to R.layout.item_home)
    }


    override fun onBindExtendsViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        when (getItem(position)?.viewType) {
            1 -> {
                val binding = holder.binding as ItemHomeBinding
                binding.article = getItem(position)
            }
        }
    }
}

private val DIFF_CALL = object : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}