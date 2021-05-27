package com.hwei.lib_common.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class HeaderOrFooterViewHolder<VB : ViewDataBinding>(val binding: VB): RecyclerView.ViewHolder(binding.root)
