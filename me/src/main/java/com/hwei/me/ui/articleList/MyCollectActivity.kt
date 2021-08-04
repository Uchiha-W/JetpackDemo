package com.hwei.me.ui.articleList

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.paging.compose.collectAsLazyPagingItems
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_base.base.BaseActivity
import com.hwei.lib_base.router.HomeRouter
import com.hwei.lib_base.router.MeRouter
import com.hwei.lib_base.widge.CommonTitle
import dagger.hilt.android.AndroidEntryPoint


@Route(path = MeRouter.myCollect)
@AndroidEntryPoint
class MyCollectActivity : BaseActivity() {

    private val myCollectViewModel: MyCollectViewModel by viewModels()

    override fun initView() {
        setContent {
            Content()
        }
    }

    override fun initData() {

    }

    override fun setEvent() {

    }

    @Composable
    fun Content() {
        Column {
            val lazyPaging = myCollectViewModel.pager.collectAsLazyPagingItems()
            AndroidView(factory = {
                CommonTitle(it).apply {
                    setMiddleText("我的收藏")
                }
            })
            LazyColumn(
                contentPadding = PaddingValues(10.dp, 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
            ) {
                items(lazyPaging.itemCount) { it ->
                    Text(text = lazyPaging.getAsState(it).value?.title ?: "", Modifier.clickable {
                        val item = lazyPaging.snapshot().items[it]
                        ARouter.getInstance().build(HomeRouter.article)
                            .withString("link", item.link)
                            .withInt("id", item.id)
                            .withString("title", item.title)
                            .withString("author", item.author)
                            .withBoolean("collect", true)
                            .navigation()
                    }.fillMaxWidth())
                }
            }
        }
    }

    @Preview("theme", widthDp = 360, heightDp = 640, showBackground = true)
    @Composable
    fun LiPreview() {
        Content()
    }
}