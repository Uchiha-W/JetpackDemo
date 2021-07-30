package com.hwei.me.ui.persional

import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.lib_base.base.BaseFragment
import com.hwei.lib_base.router.MeRouter
import com.hwei.me.R
import com.hwei.me.databinding.FragmentMeBinding

@Route(path = MeRouter.personal)
class PersonalFragment: BaseFragment<FragmentMeBinding>() {
    override fun setLayoutId(): Int {
        return R.layout.fragement_personal
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun setEvent() {

    }

}