package com.prestosa.codelablocationservice1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 123
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Check and request location permissions
        isLocationPermissionGranted()
    }

    private fun isLocationPermissionGranted() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
        } else {
            // Permission already granted, start location updates
            startLocationUpdates()
        }
    }

    private fun startLocationUpdates() {
        try {
            // Check if GPS provider is enabled
            val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (hasGps) {
                // Request location updates
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0F,
                    gpsLocationListener
                )
            } else {
                // GPS provider is not enabled, handle accordingly
            }

            // Check if Network provider is enabled
            val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (hasNetwork) {
                // Request location updates
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    5000,
                    0F,
                    networkLocationListener
                )
            } else {
                // Network provider is not enabled, handle accordingly
            }
        } catch (e: SecurityException) {
            // Handle SecurityException, e.g., request permission again or show a message to the user
        }
    }

    private val gpsLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            // Handle GPS location updates
        }

        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private val networkLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            // Handle network location updates
        }

        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start location updates
                startLocationUpdates()
            } else {
                // Permission denied, handle accordingly
            }
        }
    }
}

