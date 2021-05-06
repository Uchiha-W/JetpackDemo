package com.hwei.lib_common.sp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import okhttp3.Cookie
import kotlin.reflect.KProperty

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SpDelegate<T>(private val key: String, private val default: T) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return SpStorage.getValue(key, default)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        SpStorage.setValue(key, value)
    }
}
