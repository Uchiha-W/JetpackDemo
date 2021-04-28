package com.hwei.system

import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.lib_common.base.BaseFragment
import com.hwei.lib_common.router.SystemRouter
import com.hwei.system.databinding.FragmentSystemBinding

@Route(path = SystemRouter.system)
class SystemFragment : BaseFragment<FragmentSystemBinding>() {
    override fun setLayoutId(): Int {
        return R.layout.fragment_system
    }

    override fun initData() {

    }

    override fun initView() {

    }
}