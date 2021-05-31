package com.hwei.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hwei.home.R
import com.hwei.home.bean.Article
import com.hwei.home.databinding.ItemHomeBinding
import com.hwei.lib_common.base.BaseViewHolder
import com.hwei.lib_common.paging.BasePagingAdapter
import javax.inject.Inject

class HomeAdapter @Inject constructor() :
    BasePagingAdapter<ItemHomeBinding, Article>(DIFF_CALL) {
    override fun setLayoutId(): Int {
        return R.layout.item_home
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ItemHomeBinding>, position: Int) {
        holder.binding.article = getItem(position)
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