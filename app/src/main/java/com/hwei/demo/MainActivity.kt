package com.hwei.demo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.demo.databinding.ActivityMainBinding
import com.hwei.lib_base.base.BaseBindingActivity
import com.hwei.lib_base.router.HomeRouter
import com.hwei.lib_base.router.LiveRouter
import com.hwei.lib_base.router.MeRouter
import com.hwei.lib_base.router.SystemRouter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private val list = listOf(HomeRouter.home, SystemRouter.system, MeRouter.me)

    override fun initData() {

    }

    override fun initView() {
        binding.activity = this
        binding.viewpager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return list.size
            }

            override fun createFragment(position: Int): Fragment {
                return ARouter.getInstance().build(list[position]).navigation() as Fragment
            }
        }
        binding.viewpager.isUserInputEnabled = false
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_1 -> binding.viewpager.currentItem = 0
                R.id.bottom_2 -> binding.viewpager.currentItem = 1
                R.id.bottom_3 -> binding.viewpager.currentItem = 2
                else -> binding.viewpager.currentItem = 0
            }
            true
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setEvent() {

    }
}