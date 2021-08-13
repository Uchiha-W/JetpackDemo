package com.hwei.me.ui.login

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.lib_base.base.BaseBindingFragment
import com.hwei.lib_base.ktx.showToast
import com.hwei.lib_base.router.MeRouter
import com.hwei.lib_common.UserManager
import com.hwei.me.R
import com.hwei.me.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = MeRouter.login)
@AndroidEntryPoint
class LoginFragment : BaseBindingFragment<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun setLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {

        binding.btnLogin.setOnClickListener {
            lifecycleScope.launch {
                loginViewModel.login(
                    binding.etAccount.text.toString(),
                    binding.etPassword.text.toString()
                ).collect {
                    showToast("登录成功")
                    UserManager.userBean.value = it
                }
            }
        }
        binding.btnRegister.setOnClickListener {
            lifecycleScope.launch {
                loginViewModel.register(
                    binding.etAccount.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etPassword.text.toString()
                ).collect {
                    showToast("注册成功")
                }
            }
        }
    }

    override fun initData() {

    }

    override fun setEvent() {

    }
}