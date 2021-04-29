package com.hwei.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hwei.home.R
import com.hwei.home.bean.BannerBean
import com.hwei.home.databinding.ItemBannerBinding
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter() : BannerAdapter<BannerBean, HomeBannerViewHolder>(mutableListOf()) {
    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): HomeBannerViewHolder {

        val binding = DataBindingUtil.inflate<ItemBannerBinding>(
            LayoutInflater.from(parent?.context),
            R.layout.item_banner,
            parent,
            false
        )
        return HomeBannerViewHolder(binding)
    }

    override fun onBindView(
        holder: HomeBannerViewHolder,
        data: BannerBean?,
        position: Int,
        size: Int
    ) {
        holder.binding.bannerBean = data
    }
}


class HomeBannerViewHolder(val binding: ItemBannerBinding) : RecyclerView.ViewHolder(binding.root)