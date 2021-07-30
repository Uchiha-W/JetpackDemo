package com.hwei.lib_base.bindAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.hwei.lib_base.ktx.load


@BindingAdapter("uri")
fun setImageSrc(imageView: ImageView, uri: String) {
    imageView.load(imageView.context, uri)
}
@BindingAdapter("drawableRes")
fun setImageDrawable(imageView: ImageView, uri: Int) {
    imageView.load(imageView.context, uri)
}


