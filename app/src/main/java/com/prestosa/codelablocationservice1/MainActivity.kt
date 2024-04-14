package com.prestosa.codelablocationservice1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 123
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted, display in the TextView
                textView.text = getString(R.string.fine_location_permission_granted)
            }
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Coarse location permission granted, display in the TextView
                textView.text = getString(R.string.coarse_location_permission_granted)
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                // Explain to the user why your app requires this permission
                // Show UI to request permission again
                showPermissionExplanation()
            }
            else -> {
                // Request the ACCESS_FINE_LOCATION permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    REQUEST_CODE
                )
            }
        }
    }

    private fun showPermissionExplanation() {
        // Show UI to explain why location permission is necessary
        textView.text = getString(R.string.permission_required_for_location)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission granted, display in the TextView
                    textView.text = getString(R.string.fine_location_permission_granted_true)
                } else if (grantResults.isNotEmpty() &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    // Coarse location permission granted, display in the TextView
                    textView.text = getString(R.string.coarse_location_permission_granted_yes)
                } else {
                    // Explain to the user that the feature is unavailable
                    // Respect the user's decision
                    // Optionally, show UI to explain again or navigate to settings
                    textView.text = getString(R.string.permission_denied)
                }
            }
        }
    }
}
