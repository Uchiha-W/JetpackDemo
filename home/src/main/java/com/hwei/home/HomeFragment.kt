package com.hwei.home

import android.util.Log
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.hwei.home.adapter.HomeAdapter
import com.hwei.home.databinding.FragmentHomeBinding
import com.hwei.home.databinding.ItemHomeBinding
import com.hwei.home.net.HomeApi
import com.hwei.lib_common.base.BaseFragment
import com.hwei.lib_common.net.RetrofitManager
import com.hwei.lib_common.router.HomeRouter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

@Route(path = HomeRouter.home)
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter<ItemHomeBinding>
    override fun setLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
//        homeViewModel.article.observe(this) {
//            Log.d("tag", it.toString())
//            homeAdapter.submitList()
//        }
        homeViewModel.LivePageData.observe(this) {
            Log.e("tag2", it.size.toString())
            homeAdapter.submitList(it)
        }
        GlobalScope.launch(Dispatchers.IO){
            try {

                //val result = RetrofitManager.create(HomeApi::class.java).getArticleList()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun initView() {
        homeAdapter = HomeAdapter()
        binding.recyclerView.apply {
            adapter = homeAdapter
        }
    }

}