package com.hwei.lib_common.sp

import android.annotation.SuppressLint
import androidx.datastore.preferences.core.*
import com.hwei.lib_common.BaseApplication
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

@SuppressLint("StaticFieldLeak")
object SpStorage {

    private val mContext = BaseApplication.context

    fun <T> setValue(s: String, new: T) {
        runBlocking {
            val key = getPreferenceKey(s, new)
            mContext.dataStore.edit {
                it[key] = new
            }
        }
    }

    fun <T> getValue(s: String, default: T): T {
        return runBlocking {
            val channel = Channel<T>(1)
            val key = getPreferenceKey(s, default)
            mContext.dataStore.data.map {
                it[key] ?: default
            }.collect {
                channel.send(it)
            }
            channel.receive()
        }
    }

    private fun <T> getPreferenceKey(s: String, type: T): Preferences.Key<T> {
        val key = when (type) {
            is Int -> intPreferencesKey(s)
            is Long -> longPreferencesKey(s)
            is Double -> doublePreferencesKey(s)
            is String -> stringPreferencesKey(s)
            is Boolean -> booleanPreferencesKey(s)
            is Float -> floatPreferencesKey(s)
            is List<*> -> stringPreferencesKey(s)
            is Set<*> -> stringSetPreferencesKey(s)
            is Map<*, *> -> stringPreferencesKey(s)
            else -> throw IllegalArgumentException()
        }
        return key as Preferences.Key<T>
    }

}