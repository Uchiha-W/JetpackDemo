package com.hwei.me.ui.persional

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_base.base.BaseFragment
import com.hwei.lib_base.router.MeRouter
import com.hwei.me.R
import com.hwei.me.databinding.FragementPersonalBinding
import com.hwei.me.ui.articleList.MyCollectActivity

@Route(path = MeRouter.personal)
class PersonalFragment : BaseFragment<FragementPersonalBinding>() {
    override fun setLayoutId(): Int {
        return R.layout.fragement_personal
    }

    override fun initView() {
        binding.llCollectList.setOnClickListener {
            startActivity(Intent(requireContext(),MyCollectActivity::class.java))
            //ARouter.getInstance().build(MeRouter.myCollect).navigation()
        }
    }

    override fun initData() {

    }

    override fun setEvent() {

    }

}