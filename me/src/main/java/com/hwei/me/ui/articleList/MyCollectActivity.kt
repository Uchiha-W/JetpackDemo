package com.hwei.me.ui.articleList

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyCollectActivity : AppCompatActivity() {

    private val list = listOf(1, 2, 3, 4, 5, 6, 7, 8)

    private val myCollectViewModel: MyCollectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myCollectViewModel.getCollectList(1)
        myCollectViewModel.collectList.observe(this) {

        }
        setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        LazyColumn {
            items(5) {
                Text(text = "123456789")
            }
        }
    }

    @Preview("theme", widthDp = 360, heightDp = 640, showBackground = true)
    @Composable
    fun LiPreview() {
        Content()
    }
}