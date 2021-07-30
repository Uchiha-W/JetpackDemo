package com.hwei.lib_base.sp

import android.os.Parcelable
import com.tencent.mmkv.MMKV

internal object MMKVStorage : SpInterface {

    override fun <T> setValue(s: String, new: T) {
        when (new) {
            is Int -> MMKV.defaultMMKV().encode(s, new)
            is String -> MMKV.defaultMMKV().encode(s, new)
            is Double -> MMKV.defaultMMKV().encode(s, new)
            is Float -> MMKV.defaultMMKV().encode(s, new)
            is Boolean -> MMKV.defaultMMKV().encode(s, new)
            is Parcelable -> MMKV.defaultMMKV().encode(s, new)
            is ByteArray -> MMKV.defaultMMKV().encode(s, new)
            else -> throw IllegalArgumentException("不支持该类型")
        }
    }

    override fun <T> getValue(s: String, default: T): T {
        return when (default) {
            is Int -> MMKV.defaultMMKV().decodeInt(s, default)
            is String -> MMKV.defaultMMKV().decodeString(s, default)
            is Double -> MMKV.defaultMMKV().decodeDouble(s, default)
            is Float -> MMKV.defaultMMKV().decodeFloat(s, default)
            is Boolean -> MMKV.defaultMMKV().decodeBool(s, default)
            is Parcelable -> MMKV.defaultMMKV().decodeParcelable(s, default.javaClass, default)
            is ByteArray -> MMKV.defaultMMKV().decodeBytes(s, default)
            else -> throw IllegalArgumentException("不支持该类型")
        } as T
    }
}

