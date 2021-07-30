package com.hwei.system.adapter

import com.hwei.lib_base.adapter.IMultiBean

data class SystemBean(val id: Int,val type:Int,val content: String):IMultiBean {
    override val viewType: Int
        get() = type
}