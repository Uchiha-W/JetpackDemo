package com.hwei.me.ui.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.lib_common.base.BaseFragment
import com.hwei.lib_common.router.MeRouter
import com.hwei.me.R
import com.hwei.me.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@Route(path = MeRouter.login)
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun initData() {
        binding.btnLogin.setOnClickListener {
            loginViewModel.login(binding.etAccount.text.toString(),binding.etPassword.text.toString())
        }
        binding.btnRegister.setOnClickListener {
            loginViewModel.register(binding.etAccount.text.toString(),binding.etPassword.text.toString(),binding.etPassword.text.toString())
        }
    }

    override fun initView() {
        loginViewModel.userBean.observe(this){

        }
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_login
    }
}