package com.mobven.extensions

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mobven.extensions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Lifecycle", "onCreate")
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        Log.i("Lifecycle", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Lifecycle", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Lifecycle", "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Lifecycle", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Lifecycle", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Lifecycle", "onDestroy")
    }
}