package com.hwei.me.me

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.lib_common.base.BaseFragment
import com.hwei.lib_common.router.MeRouter
import com.hwei.me.R
import com.hwei.me.databinding.FragmentLoginBinding

@Route(path = MeRouter.login)
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel:LoginViewModel by viewModels()

    override fun initData() {
        binding.btnLogin.setOnClickListener {
            loginViewModel.login(binding.etAccount.text.toString(),binding.etPassword.text.toString())
        }
        binding.btnRegister.setOnClickListener {
            loginViewModel.register(binding.etAccount.text.toString(),binding.etPassword.text.toString(),binding.etPassword.text.toString())
        }
    }

    override fun initView() {

    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_login
    }
}