package com.hwei.lib_base.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import com.hwei.lib_base.BaseApplication
import com.hwei.lib_base.ktx.showToast
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object LocationUtil {

    private val TAG = "LocationManager"

    @SuppressLint("MissingPermission")
    fun getLocation(): Flow<String> {
        return callbackFlow {
            val locationManager =
                BaseApplication.context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val locationListener = LocationListener {
                val location = "latitude:" + it.latitude + " longitude:" + it.longitude
                Log.d(TAG, location)
                trySendBlocking(location)
                    .onFailure {
                        Log.d(TAG, "get location error")
                    }.onSuccess {
                        Log.d(TAG, "onSuccess")
                    }.onClosed {
                        Log.d(TAG, "onClosed")
                    }
            }

            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0f,
                locationListener
            )

            awaitClose {
                showToast("closed")
                locationManager.removeUpdates(locationListener)
            }
        }
    }
}