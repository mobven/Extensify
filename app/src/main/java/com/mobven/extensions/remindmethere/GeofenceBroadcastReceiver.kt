package com.mobven.extensions.remindmethere

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.mobven.extension.toast

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    private val TAG = "GeofenceBroadcastReceiv"

    override fun onReceive(context: Context, intent: Intent) {
        val notificationHelper = NotificationHelper(context)
        val geofencingEvent = GeofencingEvent.fromIntent(intent)

        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes
                .getStatusCodeString(geofencingEvent.errorCode)
            Log.e(TAG, errorMessage)
            return
        }

        val geofenceList: List<Geofence>? = geofencingEvent.triggeringGeofences
        geofenceList?.forEach {
            Log.d(TAG, "onReceive: ${it.requestId}")
        }

        when (geofencingEvent.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                context.toast("GEOFENCE_TRANSITION_ENTER")
                Log.d(TAG, "GEOFENCE_TRANSITION_ENTER")
                notificationHelper.sendHighPriorityNotification(
                    "GEOFENCE_TRANSITION_ENTER",
                    "",
                    MapsActivity::class.java
                )
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                context.toast("GEOFENCE_TRANSITION_EXIT")
                Log.d(TAG, "GEOFENCE_TRANSITION_EXIT")
                notificationHelper.sendHighPriorityNotification(
                    "GEOFENCE_TRANSITION_EXIT",
                    "",
                    MapsActivity::class.java
                )
            }
            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                context.toast("GEOFENCE_TRANSITION_DWELL")
                Log.d(TAG, "GEOFENCE_TRANSITION_DWELL")
                notificationHelper.sendHighPriorityNotification(
                    "GEOFENCE_TRANSITION_DWELL",
                    "",
                    MapsActivity::class.java
                )
            }
        }
    }
}