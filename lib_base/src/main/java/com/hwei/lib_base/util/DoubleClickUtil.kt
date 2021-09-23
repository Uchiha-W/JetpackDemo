package com.hwei.lib_base.util

import android.view.View

private class DoubleClickUtil(view: View, block: (View) -> Unit) {

    private var lastTime = 0L

    init {
        view.setOnClickListener {
            val time = System.currentTimeMillis()
            if (time < lastTime + 300) {
                block.invoke(view)
            } else {
                lastTime = time
            }
        }
    }

}


fun View.doubleClick(block: (View) -> Unit) {
    DoubleClickUtil(this, block)
}