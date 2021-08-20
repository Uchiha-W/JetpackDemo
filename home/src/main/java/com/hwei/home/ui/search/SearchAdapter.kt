package com.hwei.home.ui.search

import android.text.Html
import androidx.recyclerview.widget.DiffUtil
import com.hwei.home.R
import com.hwei.home.bean.Article
import com.hwei.home.databinding.ItemHomeBinding
import com.hwei.lib_base.base.BaseViewHolder
import com.hwei.lib_base.paging.BasePagingAdapter

class SearchAdapter :
    BasePagingAdapter<ItemHomeBinding, Article>(object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(p0: Article, p1: Article): Boolean {
            return p0.id == p1.id
        }

        override fun areContentsTheSame(p0: Article, p1: Article): Boolean {
            return p0 == p1
        }
    }) {
    override fun onBindExtendsViewHolder(holder: BaseViewHolder<ItemHomeBinding>, position: Int) {
        holder.binding.tvArticle.text = Html.fromHtml(getItem(position)?.title)
    }

    override fun setLayoutId(): Int {
        return R.layout.item_home
    }
}