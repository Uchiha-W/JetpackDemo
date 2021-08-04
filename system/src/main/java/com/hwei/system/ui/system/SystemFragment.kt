package com.hwei.system.ui.system

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hwei.lib_base.base.BaseComposeFragment
import com.hwei.lib_base.router.SystemRouter
import com.hwei.system.R
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@Route(path = SystemRouter.system)
@AndroidEntryPoint
class SystemFragment : BaseComposeFragment() {

    private val systemViewModel: SystemViewModel by viewModels()

    override fun setComposeView(): ComposeView {
        return ComposeView(mContext).apply {
            setContent {
                System()
            }
        }
    }

    @Composable
    fun System() {
        val tabList = arrayOf("体系", "导航")
        val state = systemViewModel.systemBeanItem.observeAsState()
        Scaffold {
            Column {
                val tabStates = remember { mutableStateOf(0) }
                TabRow(
                    selectedTabIndex = tabStates.value,
                    backgroundColor = colorResource(id = R.color.app_300),
                ) {
                    tabList.forEachIndexed { index, s ->
                        Tab(selected = index == tabStates.value,
                            selectedContentColor = colorResource(id = R.color.app_600),
                            unselectedContentColor = colorResource(id = R.color.app_50),
                            modifier = Modifier
                                .weight(1f)
                                .padding(20.dp), onClick = {
                                tabStates.value = index
                            }) {
                            Text(text = s)
                        }
                    }
                }
                Divider()
                if (tabStates.value == 0) {
                    Box(modifier = Modifier.weight(1f)) {
                        val choosePosition = remember {
                            mutableStateOf(0)
                        }
                        Row {
                            state.value?.let { it ->
                                LazyColumn(
                                    modifier = Modifier
                                        .padding(10.dp, 10.dp)
                                        .width(100.dp)
                                ) {
                                    items(it.size) { index ->
                                        Text(
                                            text = it[index].name,
                                            Modifier
                                                .clickable {
                                                    choosePosition.value = index
                                                }
                                                .padding(10.dp),
                                            color = colorResource(id = if (choosePosition.value == index) R.color.app_600 else R.color.defaul)
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .width(1.dp)
                                        .fillMaxHeight()
                                        .background(colorResource(id = R.color.defaul))
                                )
                                LazyVerticalGrid(
                                    cells = GridCells.Fixed(2),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    items(it[choosePosition.value].children.size) { index ->
                                        Text(
                                            text = it[choosePosition.value].children[index].name,
                                            color = colorResource(id = R.color.defaul),
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .clickable {
                                                    val item =
                                                        it[choosePosition.value].children[index]
                                                    ARouter
                                                        .getInstance()
                                                        .build(SystemRouter.article)
                                                        .withInt("id", item.id)
                                                        .withString("title", item.name)
                                                        .navigation()
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    //todo
                }
            }
        }
    }

    @Composable
    @Preview(widthDp = 360, heightDp = 640, showBackground = true)
    fun Preview() {
        System()
    }

    override fun initView() {

    }

    override fun initData() {
        systemViewModel.getSystemData()
    }

    override fun setEvent() {

    }
}