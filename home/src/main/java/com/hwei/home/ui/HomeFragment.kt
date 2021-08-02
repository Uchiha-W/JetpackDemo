package com.hwei.home.ui

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.home.R
import com.hwei.home.adapter.HomeAdapter
import com.hwei.home.bean.Article
import com.hwei.home.bean.BannerBean
import com.hwei.home.databinding.FragmentHomeBinding
import com.hwei.lib_base.base.BaseBindingFragment
import com.hwei.lib_base.ktx.load
import com.hwei.lib_base.ktx.showToast
import com.hwei.lib_base.listener.OnItemClickListener
import com.hwei.lib_base.router.HomeRouter
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@Route(path = HomeRouter.home)
@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var homeAdapter: HomeAdapter
    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        observeLoading(homeViewModel)
        binding.recyclerView.apply {
            adapter = homeAdapter
        }
        binding.smartRefreshLayout.setOnRefreshListener {
            homeAdapter.refresh()
        }
        binding.smartRefreshLayout.setOnLoadMoreListener {
            homeAdapter.retry()
        }
        homeAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> null
                is LoadState.Error -> {
                    binding.smartRefreshLayout.finishRefresh(false)
                    showToast((it.refresh as LoadState.Error).error.message ?: "unknown error")
                }
                is LoadState.NotLoading -> {
                    binding.smartRefreshLayout.finishRefresh()
                    if (it.refresh.endOfPaginationReached) {
                        binding.smartRefreshLayout.finishLoadMoreWithNoMoreData()
                    }
                }
            }
            when (it.append) {
                is LoadState.Error -> {
                    binding.smartRefreshLayout.finishLoadMore(false)
                }
                is LoadState.NotLoading -> {
                    binding.smartRefreshLayout.finishLoadMore()
                    if (it.append.endOfPaginationReached) {
                        binding.smartRefreshLayout.finishLoadMoreWithNoMoreData()
                    }
                }
                is LoadState.Loading -> null
            }
        }
        homeAdapter.setOnItemClickListener(object : OnItemClickListener<Article> {
            override fun onClick(v: View, item: Article, position: Int) {
                ARouter.getInstance().build(HomeRouter.article)
                    .withString("link", item.link)
                    .withInt("id", item.id)
                    .withString("title", item.title)
                    .withString("author", item.author)
                    .withBoolean("collect", item.collect)
                    .navigation()
            }
        })
    }

    override fun initData() {
        homeViewModel.getBannerList()
    }


    override fun setEvent() {
        homeViewModel.livePageData.observe(this) {
            lifecycleScope.launch {
                binding.smartRefreshLayout.finishRefresh()
                homeAdapter.submitData(it)
            }
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
    }
}