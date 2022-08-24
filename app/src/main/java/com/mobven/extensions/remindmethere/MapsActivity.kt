package com.mobven.extensions.remindmethere

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mobven.extension.hasPermissions
import com.mobven.extensions.R
import com.mobven.extensions.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val geofenceRadius = 200.0

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val bim = LatLng(41.07187, 28.93595)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bim, 16F))

        enableUserLocation()

        mMap.setOnMapLongClickListener {
            if (Build.VERSION.SDK_INT >= 29) {
                if (hasPermissions(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    tryAddingGeofence(it)
                } else {
                    permissionsBuilder(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        .build().send { result ->
                            if (result.allGranted()) {
                                tryAddingGeofence(it)
                            }
                        }
                }
            } else {
                tryAddingGeofence(it)
            }
        }
    }

    private fun tryAddingGeofence(latLng: LatLng) {
        mMap.clear()
        addMarker(latLng)
        addCircle(latLng, geofenceRadius)
        addGeofence(latLng)
    }

    @SuppressLint("MissingPermission")
    private fun addGeofence(latLng: LatLng) {
        startForegroundService(
            Intent(this@MapsActivity, GeofenceForegroundService::class.java).apply {
                putExtra("LATLNG", latLng)
            }
        )
    }

    @SuppressLint("MissingPermission")
    private fun enableUserLocation() {
        permissionsBuilder(Manifest.permission.ACCESS_FINE_LOCATION)
            .build().send { result ->
                if (result.allGranted()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        permissionsBuilder(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                            .build().send { resultBackgroundPermission ->
                                if (resultBackgroundPermission.allGranted()) {
                                    mMap.isMyLocationEnabled = true
                                }
                            }
                    } else {
                        mMap.isMyLocationEnabled = true
                    }
                }
            }
    }

    private fun addMarker(latLng: LatLng) {
        MarkerOptions().position(latLng).apply {
            mMap.addMarker(this)
        }
    }

    private fun addCircle(latLng: LatLng, radius: Double) {
        CircleOptions().apply {
            center(latLng)
            radius(radius)
            strokeColor(Color.argb(255, 255, 0, 0))
            fillColor(Color.argb(64, 255, 0, 0))
            strokeWidth(4F)
            mMap.addCircle(this)
        }
    }
}