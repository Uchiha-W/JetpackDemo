package com.hwei.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hwei.home.R
import com.hwei.home.bean.Article
import com.hwei.home.databinding.ItemHomeBinding

class HomeAdapter:
    PagedListAdapter<Article, HomeViewHolder>(DIFF_CALL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = DataBindingUtil.inflate<ItemHomeBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_home,
            parent,
            false
        )
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.binding.article = getItem(position)
    }
}

class HomeViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

val DIFF_CALL = object : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}