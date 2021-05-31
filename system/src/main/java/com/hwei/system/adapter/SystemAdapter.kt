package com.hwei.system.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hwei.lib_common.base.BaseAdapter
import com.hwei.lib_common.base.BaseViewHolder
import com.hwei.system.BR
import com.hwei.system.R
import com.hwei.system.databinding.ItemBinding
import com.hwei.system.databinding.ItemHeaderBinding

class SystemAdapter : BaseAdapter<ItemBinding, String>(DIFF_CALLBACK) {
    override fun setLayoutId(): Int {
        return R.layout.item_
    }

    override fun onBindExtendsViewHolder(
        holder: BaseViewHolder<ItemBinding>,
        position: Int
    ) {
        holder.binding.tvTitle.text = currentList[position]
    }

    override fun onBindHeaderViewHolder(headerViewHolder: BaseViewHolder<*>) {
        val itemHeaderBinding = headerViewHolder.binding as ItemHeaderBinding
        itemHeaderBinding.tvTitle.text = "i am header"
    }

    override fun onBindFooterViewHolder(footerViewHolder: BaseViewHolder<*>) {
        footerViewHolder.binding.setVariable(BR.title, "i am footer")
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}