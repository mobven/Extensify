package com.mobven.extensions.remindmethere

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobven.extensions.databinding.ActivityGeofenceBinding


class GeofenceActivity : AppCompatActivity() {

  private lateinit var binding: ActivityGeofenceBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGeofenceBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}
