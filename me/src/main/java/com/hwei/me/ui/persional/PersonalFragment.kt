package com.hwei.me.ui.persional

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_base.base.BaseBindingFragment
import com.hwei.lib_base.ktx.showToast
import com.hwei.lib_base.router.MeRouter
import com.hwei.lib_base.util.LocationUtil
import com.hwei.lib_common.UserBean
import com.hwei.lib_common.UserManager
import com.hwei.me.R
import com.hwei.me.databinding.FragementPersonalBinding
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
            lifecycleScope.launch {
                personViewModel.logout().collect {
                    UserManager.userBean.value = UserBean.emptyUser()
                }
            }
        }
        PermissionX.init(this)
            .permissions(
                listOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ).request { allGranted, _, _ ->
                if (allGranted) {
                    lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            LocationUtil.getLocation().collectLatest {

                            }
                        }
                    }
                }
            }
    }

    override fun initData() {

    }

    override fun setEvent() {

    }

}