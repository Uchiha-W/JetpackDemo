package com.hwei.lib_common.ktx

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.load(context: Context, uri: String) = Glide.with(context).load(uri).into(this)