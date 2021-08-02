package com.hwei.me.ui.persional

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_base.base.BaseBindingFragment
import com.hwei.lib_base.router.MeRouter
import com.hwei.lib_common.UserBean
import com.hwei.lib_common.UserManager
import com.hwei.me.R
import com.hwei.me.databinding.FragementPersonalBinding
import dagger.hilt.android.AndroidEntryPoint

@Route(path = MeRouter.personal)
@AndroidEntryPoint
class PersonalFragment : BaseBindingFragment<FragementPersonalBinding>() {

    private val personViewModel: PersonViewModel by viewModels()

    override fun setLayoutId(): Int {
        return R.layout.fragement_personal
    }

    override fun initView() {
        binding.llCollectList.setOnClickListener {
            ARouter.getInstance().build(MeRouter.myCollect).navigation()
        }
        binding.llLogout.setOnClickListener {
            personViewModel.logout()
        }
    }

    override fun initData() {

    }

    override fun setEvent() {
        personViewModel.logout.observe(this) {
            UserManager.userBean.value = UserBean.emptyUser()
        }
    }

}