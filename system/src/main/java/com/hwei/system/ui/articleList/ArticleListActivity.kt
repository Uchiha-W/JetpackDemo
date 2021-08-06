package com.hwei.system.ui.articleList

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.paging.compose.collectAsLazyPagingItems
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_base.base.BaseActivity
import com.hwei.lib_base.router.HomeRouter
import com.hwei.lib_base.router.SystemRouter
import com.hwei.lib_base.widge.CommonTitle
import com.hwei.lib_common.PagingFooter
import com.hwei.lib_common.PagingLoadingContent
import com.hwei.system.ui.system.SystemViewModel
import dagger.hilt.android.AndroidEntryPoint

@Route(path = SystemRouter.article)
@AndroidEntryPoint
class ArticleListActivity : BaseActivity() {

    private val systemViewModel: SystemViewModel by viewModels()

    @Autowired
    @JvmField
    var id: Int = 0

    @Autowired
    lateinit var title: String

    override fun initView() {
        ARouter.getInstance().inject(this)
        systemViewModel.id = id
        setContent {
            ArticleList()
        }
    }

    @Composable
    fun ArticleList() {
        Scaffold(topBar = {
            AndroidView(factory = {
                CommonTitle(it).apply {
                    setMiddleText(title)
                }
            })
        }) {
            Column {
                val lazyPaging = systemViewModel.pager.collectAsLazyPagingItems()
                PagingLoadingContent(loadState = lazyPaging.loadState, { lazyPaging.refresh() }) {
                    LazyColumn(
                        contentPadding = PaddingValues(10.dp, 10.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            20.dp,
                            Alignment.CenterVertically
                        )
                    ) {
                        items(lazyPaging.snapshot().size) { index ->
                            Text(
                                text = lazyPaging.getAsState(index = index).value?.title ?: "",
                                Modifier.clickable {
                                    val item = lazyPaging.snapshot().items[index]
                                    ARouter.getInstance().build(HomeRouter.article)
                                        .withString("link", item.link)
                                        .withInt("id", item.id)
                                        .withString("title", item.title)
                                        .withString("author", item.author)
                                        .withBoolean("collect", item.collect)
                                        .navigation()
                                })
                        }
                        item {
                            PagingFooter(loadState = lazyPaging.loadState) {
                                lazyPaging.retry()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initData() {

    }

    override fun setEvent() {

    }
}