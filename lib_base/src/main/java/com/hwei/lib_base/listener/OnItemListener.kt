package com.hwei.lib_base.listener

import android.view.View

interface OnItemClickListener<T : Any> {
    fun onClick(v: View, item: T, position: Int)
}

interface OnItemLongClickListener<T : Any> {
    fun onLongClick(v: View, item: T, position: Int): Boolean
}