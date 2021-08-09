package com.hwei.lib_common

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.hwei.lib_common.theme.*

@Composable
fun myTheme(Content: @Composable () -> Unit) {
    /**
     * 夜间模式：
     * 目前颜色值是用values-night中区分，因此dark没啥用
     */
    if (!isSystemInDarkTheme()) {
        MaterialTheme(colors = lightColor, content = Content)
    } else {
        MaterialTheme(colors = darkColor, content = Content)
    }
}


@SuppressLint("ConflictingOnColor")
val lightColor = lightColors(
    primary = app300,
    primaryVariant = app600,
    onPrimary = white,
    secondary = app300,
    secondaryVariant = app600,
    onSecondary = black,
)

@SuppressLint("ConflictingOnColor")
val darkColor = darkColors(
    primary = purple200,
    primaryVariant = purple500,
    onPrimary = black,
    secondary = purple200,
    secondaryVariant = purple500,
    onSecondary = white,
)


