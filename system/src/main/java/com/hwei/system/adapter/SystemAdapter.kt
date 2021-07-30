package com.hwei.system.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.hwei.lib_base.adapter.BaseMultiAdapter
import com.hwei.lib_base.base.BaseViewHolder
import com.hwei.system.BR
import com.hwei.system.R
import com.hwei.system.databinding.ItemBinding
import com.hwei.system.databinding.ItemEmptyBinding
import com.hwei.system.databinding.ItemHeaderBinding
import com.hwei.system.databinding.ItemImageBinding

class SystemAdapter : BaseMultiAdapter<SystemBean>(DIFF_CALLBACK) {

    init {
        addMultiItem(1 to R.layout.item_)
        addMultiItem(2 to R.layout.item_image)
    }

    override fun onBindHeaderViewHolder(headerViewHolder: BaseViewHolder<*>) {
        val itemHeaderBinding = headerViewHolder.binding as ItemHeaderBinding
        itemHeaderBinding.tvTitle.text = "i am header"
    }

    override fun onBindFooterViewHolder(footerViewHolder: BaseViewHolder<*>) {
        footerViewHolder.binding.setVariable(BR.title, "i am footer")
    }


    override fun onBindEmptyViewHolder(emptyViewHolder: BaseViewHolder<*>) {
        val binding = emptyViewHolder.binding as ItemEmptyBinding
        binding.tvValue.text = "i am empty"
    }

    override fun onBindExtendsViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        when (holder.binding) {
            is ItemBinding -> {
                val binding = holder.binding as ItemBinding
                binding.tvTitle.text = currentList[position].content
            }
            is ItemImageBinding -> {
                val binding = holder.binding as ItemImageBinding
                binding.iv.setImageResource(R.drawable.ic_system)
            }
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SystemBean>() {
    override fun areItemsTheSame(oldItem: SystemBean, newItem: SystemBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SystemBean, newItem: SystemBean): Boolean {
        return oldItem == newItem
    }
}