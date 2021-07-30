package com.hwei.lib_base.sp

interface SpInterface {

    fun <T> setValue(s: String, new: T)

    fun <T> getValue(s: String, default: T): T
}