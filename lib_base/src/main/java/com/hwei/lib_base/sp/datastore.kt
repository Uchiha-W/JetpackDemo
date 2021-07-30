package com.hwei.lib_base.sp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SpDelegate<T>(private val key: String, private val default: T) : ReadWriteProperty<Any?, T> {
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return SpStorage.getValue(key, default)
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        SpStorage.setValue(key, value)
    }
}
