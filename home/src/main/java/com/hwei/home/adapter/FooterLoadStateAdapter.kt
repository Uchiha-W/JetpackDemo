package com.hwei.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hwei.home.R


class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}


class FooterLoadStateAdapter(block:()->Unit) : LoadStateAdapter<FooterViewHolder>() {
    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder{
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_loading_footer, parent, false)
        return FooterViewHolder(view)
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return super.displayLoadStateAsItem(loadState)
    }
}