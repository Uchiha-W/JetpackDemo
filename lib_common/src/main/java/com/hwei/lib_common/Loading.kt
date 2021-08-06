package com.hwei.lib_common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hwei.lib_base.ktx.showToast

@Composable
fun PagingLoadingContent(
    loadState: CombinedLoadStates,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    when (loadState.refresh) {
        is LoadState.Loading -> null
        is LoadState.Error -> showToast(
            (loadState.refresh as LoadState.Error).error.message
                ?: "unknown error"
        )
        is LoadState.NotLoading -> null
    }

    when (loadState.append) {
        is LoadState.Loading -> null
        is LoadState.Error -> showToast(
            (loadState.append as LoadState.Error).error.message
                ?: "unknown error"
        )
        is LoadState.NotLoading -> null
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = loadState.refresh == LoadState.Loading),
        onRefresh = {
            if (loadState.refresh != LoadState.Loading) {
                onRefresh.invoke()
            }
        },
        content = content
    )
}

@Composable
fun PagingFooter(loadState: CombinedLoadStates, retry: () -> Unit) {
    if (loadState.append is LoadState.Loading) {
        Text(
            text = "正在加载",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

    } else if (loadState.append is LoadState.Error) {
        Text(
            text = "重试",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                    retry.invoke()
                }
                .fillMaxWidth()
        )
    }
}

@Composable
fun LoadingContent(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            onRefresh.invoke()
        },
        content = content
    )
}