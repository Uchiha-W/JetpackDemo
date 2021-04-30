package com.hwei.home.ui

import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.home.R
import com.hwei.home.adapter.HomeAdapter
import com.hwei.home.bean.BannerBean
import com.hwei.home.databinding.FragmentHomeBinding
import com.hwei.lib_common.base.BaseFragment
import com.hwei.lib_common.ktx.load
import com.hwei.lib_common.router.HomeRouter
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

@Route(path = HomeRouter.home)
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter
    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
        homeViewModel.livePageData.observe(this) {
            homeAdapter.submitList(it)
        }
        homeViewModel.bannerData.observe(this) {
            binding.banner.apply {
                adapter = object : BannerImageAdapter<BannerBean>(it.toMutableList()) {
                    override fun onBindView(
                        holder: BannerImageHolder,
                        data: BannerBean,
                        position: Int,
                        size: Int
                    ) {
                        holder.imageView.load(mContext, data.imagePath)
                    }
                }
                addBannerLifecycleObserver(this@HomeFragment)//添加生命周期观察者
                indicator = CircleIndicator(mContext)
            }
        }
        homeViewModel.getBannerList()
    }

    override fun initView() {
        homeAdapter = HomeAdapter()
        binding.recyclerView.apply {
            adapter = homeAdapter
        }
    }

}