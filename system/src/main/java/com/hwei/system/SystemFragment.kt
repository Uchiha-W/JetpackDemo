package com.hwei.system

import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.lib_common.adapter.BaseMultiAdapter
import com.hwei.lib_common.base.BaseFragment
import com.hwei.lib_common.base.BaseViewModel
import com.hwei.lib_common.ktx.showToast
import com.hwei.lib_common.listener.OnItemClickListener
import com.hwei.lib_common.router.SystemRouter
import com.hwei.system.adapter.SystemAdapter
import com.hwei.system.adapter.SystemBean
import com.hwei.system.databinding.FragmentSystemBinding

@Route(path = SystemRouter.system)
class SystemFragment : BaseFragment<FragmentSystemBinding>() {
    lateinit var adapter: BaseMultiAdapter<SystemBean>
    override fun setLayoutId(): Int {
        return R.layout.fragment_system
    }

    override fun initData() {
        adapter = SystemAdapter()
        adapter.addFooterView(R.layout.item_footer)
        adapter.addHeaderView(R.layout.item_header)
        adapter.setEmptyView(R.layout.item_empty)
        adapter.setOnItemClickListener(object : OnItemClickListener<SystemBean> {
            override fun onClick(v: View, item: SystemBean, position: Int) {
                showToast(item.content)
            }
        })
        binding.recyclerView.adapter = adapter
        val list = mutableListOf<SystemBean>()
        list.add(SystemBean(2, 1, "2"))
        list.add(SystemBean(3, 1, "3"))
        list.add(SystemBean(5, 2, "5"))
        list.add(SystemBean(6, 2, "6"))
        list.add(SystemBean(7, 1, "7"))
        adapter.submitList(list)
    }

    override fun initView() {

    }

    override val vm: BaseViewModel?
        get() = null
}