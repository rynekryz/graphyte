package com.rynekryz.graphyte.data

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class LocationRepository(private val context: Context) {
    private val manager = context.getSystemService(LocationManager::class.java)

    @SuppressLint("MissingPermission")
    fun updates(): Flow<Location> = callbackFlow {
        val listener = LocationListener { location -> trySend(location) }
        fun scan() {
            try {
                listOf(LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER)
                    .filter { manager.isProviderEnabled(it) }
                    .forEach { provider ->
                        manager.getLastKnownLocation(provider)?.let { trySend(it) }
                        manager.requestLocationUpdates(provider, 1000L, 1f, listener, Looper.getMainLooper())
                    }
            } catch (_: SecurityException) {
                close()
            }
        }
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(ctx: Context, intent: Intent) {
                scan()
            }
        }
        scan()
        ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter(LocationManager.MODE_CHANGED_ACTION).apply {
                addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
            },
            ContextCompat.RECEIVER_NOT_EXPORTED,
        )
        awaitClose { manager.removeUpdates(listener) }
    }
}
