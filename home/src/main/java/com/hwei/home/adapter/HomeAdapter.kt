package com.hwei.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hwei.home.R
import com.hwei.home.bean.Article

class HomeAdapter<T : ViewDataBinding> :
    PagedListAdapter<Article, RecyclerView.ViewHolder>(DIFF_CALL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<T>(
            LayoutInflater.from(parent.context),
            R.layout.item_home,
            parent,
            false
        )
        return object : RecyclerView.ViewHolder(binding.root) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }
}

val DIFF_CALL = object : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}