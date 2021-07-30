package com.hwei.me.ui.login

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.lib_base.base.BaseFragment
import com.hwei.lib_base.ktx.showToast
import com.hwei.lib_base.router.MeRouter
import com.hwei.lib_common.UserManager
import com.hwei.me.R
import com.hwei.me.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@Route(path = MeRouter.login)
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun setLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initView() {
        binding.btnLogin.setOnClickListener {
            loginViewModel.login(
                binding.etAccount.text.toString(),
                binding.etPassword.text.toString()
            )
        }
        binding.btnRegister.setOnClickListener {
            loginViewModel.register(
                binding.etAccount.text.toString(),
                binding.etPassword.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    override fun initData() {

    }

    override fun setEvent() {
        loginViewModel.loginUserBean.observe(this) {
            showToast("登录成功")
            UserManager.userBean.value = it
        }

        loginViewModel.registerUserBean.observe(this) {
            showToast("注册成功")
        }
    }
}