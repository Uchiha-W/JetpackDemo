package com.hwei.home.ui.search

import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.home.R
import com.hwei.home.bean.Article
import com.hwei.home.databinding.ActivitySearchBinding
import com.hwei.lib_base.base.BaseBindingActivity
import com.hwei.lib_base.listener.OnItemClickListener
import com.hwei.lib_base.router.HomeRouter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@Route(path = HomeRouter.search)
class SearchActivity : BaseBindingActivity<ActivitySearchBinding>() {

    private val searchViewModel: SearchViewModel by viewModels()
    private val adapter by lazy { SearchAdapter() }

    override fun initView() {
        binding.title.setLeftClickListener { finishAfterTransition() }
        binding.searchView.setIconifiedByDefault(false)
        binding.searchView.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    lifecycleScope.launch {
                        searchViewModel.search(it).collect {
                            adapter.submitData(it)
                        }
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        adapter.setOnItemClickListener(object : OnItemClickListener<Article> {
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
        binding.recyclerView.adapter = adapter
    }

    override fun initData() {

    }

    override fun setEvent() {

    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_search
    }
}