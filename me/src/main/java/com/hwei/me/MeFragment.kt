package com.hwei.me

import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.lib_common.base.BaseFragment
import com.hwei.lib_common.router.MeRouter
import com.hwei.me.databinding.FragmentMeBinding

@Route(path = MeRouter.me)
class MeFragment : BaseFragment<FragmentMeBinding>() {
    override fun setLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun initData() {

    }

    override fun initView() {

    }
}