package com.hwei.lib_common.bindAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.hwei.lib_common.ktx.load


@BindingAdapter("uri")
fun setImageSrc(imageView: ImageView, uri: String) {
    imageView.load(imageView.context, uri)
}
@BindingAdapter("drawableRes")
fun setImageDrawable(imageView: ImageView, uri: Int) {
    imageView.load(imageView.context, uri)
}


