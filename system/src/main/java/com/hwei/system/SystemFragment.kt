package com.hwei.system

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.lib_common.base.BaseAdapter
import com.hwei.lib_common.base.BaseFragment
import com.hwei.lib_common.base.BaseViewHolder
import com.hwei.lib_common.ktx.showToast
import com.hwei.lib_common.listener.OnItemClickListener
import com.hwei.lib_common.router.SystemRouter
import com.hwei.system.databinding.FragmentSystemBinding
import com.hwei.system.databinding.ItemBinding
import com.hwei.system.databinding.ItemHeaderBinding

@Route(path = SystemRouter.system)
class SystemFragment : BaseFragment<FragmentSystemBinding>() {
    lateinit var adapter: BaseAdapter<ItemBinding, String>
    override fun setLayoutId(): Int {
        return R.layout.fragment_system
    }

    override fun initData() {
        adapter = object : BaseAdapter<ItemBinding, String>() {
            override fun setLayoutId(): Int {
                return R.layout.item_
            }

            override fun onBindExtendsViewHolder(
                holder: BaseViewHolder<ItemBinding>,
                position: Int
            ) {
                holder.binding.tvTitle.text = datas[position]
            }

            override fun onBindHeaderViewHolder(headerViewHolder: BaseViewHolder<*>) {
                val itemHeaderBinding = headerViewHolder.binding as ItemHeaderBinding
                itemHeaderBinding.tvTitle.text = "i am header"
            }

            override fun onBindFooterViewHolder(footerViewHolder: BaseViewHolder<*>) {
                footerViewHolder.binding.setVariable(BR.title, "i am footer")
            }
        }
        adapter.addFooterView(R.layout.item_footer)
        adapter.addHeaderView(R.layout.item_header)
        adapter.setOnItemClickListener(object : OnItemClickListener<String> {
            override fun onClick(v: View, item: String, position: Int) {
                showToast(item)
            }
        })

        adapter.setData(mutableListOf("1", "2", "3", "4", "4", "5"))
        binding.recyclerView.adapter = adapter
    }

    override fun initView() {

    }
}