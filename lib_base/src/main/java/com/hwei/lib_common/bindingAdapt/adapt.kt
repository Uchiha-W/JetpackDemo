package com.hwei.lib_common.bindingAdapt

import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

object adapt {

    @JvmStatic
    @BindingAdapter("onItemClick")
    fun onItemClick(bottomNavigationView: BottomNavigationView, block: (Int) -> Unit) {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            block(it.itemId)
            true
        }
    }

}