package com.hwei.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hwei.home.R


class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}


class HeaderLoadStateAdapter(block:()->Unit) : LoadStateAdapter<HeaderViewHolder>() {
    override fun onBindViewHolder(holder: HeaderViewHolder, loadState: LoadState) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): HeaderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_loading_header, parent, false)
        return HeaderViewHolder(view)
    }

}