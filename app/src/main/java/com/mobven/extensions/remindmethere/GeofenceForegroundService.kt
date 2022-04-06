package com.mobven.extensions.remindmethere

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.mobven.extensions.R

class GeofenceForegroundService: Service() {

    private lateinit var geofenceHelper: GeofenceHelper
    private lateinit var geofencingClient: GeofencingClient
    private val geofenceId = "Some_Geofence_Id"
    private val geofenceRadius = 200.0

    private val TAG = "GeofenceForegroundServi"

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, MapsActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(this, 0, notificationIntent,0)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel("Geofence", "Geofence Loc", NotificationManager.IMPORTANCE_NONE)
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, "Geofence")
            .setContentTitle("Geofence Active")
            .setContentText("Location Updating...")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()

        val latLng = intent?.getParcelableExtra<LatLng>("LATLNG")
        geofenceHelper = GeofenceHelper(this)
        geofencingClient = LocationServices.getGeofencingClient(this)
        val geofence = geofenceHelper.getGeofence(geofenceId, latLng!!, geofenceRadius.toFloat())
        val geofencingRequest = geofenceHelper.getGeofencingRequest(geofence)
        val geopendingIntent = geofenceHelper.pendingIntent
        geofencingClient.addGeofences(geofencingRequest, geopendingIntent)?.run {
            addOnSuccessListener {
                Log.d(TAG, "onSuccess: Geofence Added...")
            }
            addOnFailureListener {
                Log.d(TAG, "onFailure ${geofenceHelper.getErrorString(it)}")
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION)
        } else {
            startForeground(1, notification)
        }

        return START_STICKY
    }
}