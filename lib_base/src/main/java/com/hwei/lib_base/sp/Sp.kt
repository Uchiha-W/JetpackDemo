package com.hwei.lib_base.sp

object Sp : SpInterface {

    private val spProxy = MMKVStorage

    override fun <T> setValue(s: String, new: T) {
        spProxy.setValue(s, new)
    }

    override fun <T> getValue(s: String, default: T): T {
        return spProxy.getValue(s, default)
    }
}