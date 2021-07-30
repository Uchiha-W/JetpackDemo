package com.hwei.demo

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "12345")
        }
    }

    @Composable
    @Preview(widthDp = 360, heightDp = 640, showBackground = true)
    fun Preview() {
        Content()
    }
}