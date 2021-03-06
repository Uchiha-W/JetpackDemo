package com.hwei.me.ui

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_base.base.BaseBindingFragment
import com.hwei.lib_base.router.MeRouter
import com.hwei.lib_common.UserManager
import com.hwei.me.R
import com.hwei.me.databinding.FragmentMeBinding

@Route(path = MeRouter.me)
class MeFragment : BaseBindingFragment<FragmentMeBinding>() {

    private val list =
        mutableMapOf<String, Fragment?>(MeRouter.login to null, MeRouter.personal to null)
    private var currentFragment: Fragment? = null

    override fun setLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun initData() {

    }

    override fun initView() {
        showFragment(MeRouter.login)
    }

    override fun setEvent() {
        UserManager.isLogin.observe(this){
            if (it) {
                showFragment(MeRouter.personal)
            }else{
                showFragment(MeRouter.login)
            }
        }
    }

    private fun showFragment(tag: String) {
        var fragment = childFragmentManager.findFragmentByTag(tag)
        if (fragment == currentFragment && fragment != null) return
        if (fragment == null) {
            if (list[tag] == null) {
                fragment = ARouter.getInstance().build(tag).navigation() as Fragment
                list[tag] = fragment
            } else {
                fragment = list[tag]
            }
            childFragmentManager.beginTransaction()
                .apply {
                    add(R.id.cl_container, fragment!!, tag)
                    show(fragment)
                    currentFragment?.let {
                        remove(it)
                    }
                    commit()
                }
            currentFragment = fragment
        } else {
            childFragmentManager.beginTransaction()
                .apply {
                    show(fragment)
                    currentFragment?.let {
                        remove(it)
                    }
                    commit()
                }
            currentFragment = fragment
        }
    }

}