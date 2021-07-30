package com.hwei.lib_base.ktx

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide


fun ImageView.load(context: Context, uri: String) = Glide.with(context).load(uri).into(this)

fun ImageView.load(context: Context, @DrawableRes drawableId: Int) = Glide.with(context).load(drawableId).into(this)