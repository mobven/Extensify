package com.mobven.extensions.remindmethere

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.mobven.extensions.R

class GeofenceForegroundService: Service() {

    private lateinit var geofenceHelper: GeofenceHelper
    private lateinit var geofencingClient: GeofencingClient
    private lateinit var pendingIntent: PendingIntent
    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val geofenceId = "Some_Geofence_Id"
    private val geofenceRadius = 200.0

    private val TAG = "GeofenceForegroundServi"

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        geofenceHelper = GeofenceHelper(this)

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
            .setContentIntent(geofenceHelper.pendingIntent)
            .build()

        val latLng = intent?.getParcelableExtra<LatLng>("LATLNG")
        geofencingClient = LocationServices.getGeofencingClient(this)
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        val geofence = geofenceHelper.getGeofence(geofenceId, latLng!!, geofenceRadius.toFloat())
        val geofencingRequest = geofenceHelper.getGeofencingRequest(geofence)
        pendingIntent = geofenceHelper.pendingIntent
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                Log.d(TAG, "${locationResult.lastLocation.latitude} ${locationResult.lastLocation.longitude}")
            }
        }
        Looper.myLooper()?.let {
            locationClient.requestLocationUpdates(locationRequest, locationCallback,
                it
            )
        }

        geofencingClient.addGeofences(geofencingRequest, pendingIntent).run {
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

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: RUN")
        geofencingClient.removeGeofences(pendingIntent)
        locationClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.i(TAG, "onTaskRemoved: RUN")
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }
}