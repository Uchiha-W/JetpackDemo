package com.hwei.system.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hwei.lib_common.adapter.BaseMultiAdapter
import com.hwei.lib_common.base.BaseViewHolder
import com.hwei.system.BR
import com.hwei.system.R
import com.hwei.system.databinding.ItemHeaderBinding

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

    override fun onBindExtendMultiViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (currentList[position].viewType) {
            1 -> holder.binding.setVariable(BR.title, currentList[position].content)
            2 -> holder.binding.setVariable(BR.drawableRes, R.drawable.ic_system)
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