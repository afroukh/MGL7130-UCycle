package ca.uqam.ucycle.utils

import android.content.Context
import android.os.Bundle
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import android.location.LocationListener
import android.util.Log


class GpsTracker(internal var context: Context) : LocationListener {

    val location: Location?
        get() {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("fist", "error")
                return null
            }
            try {
                val lm = context.getSystemService(LOCATION_SERVICE) as LocationManager
                val isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (isGPSEnabled) {
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10f, this)
                    return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                } else {
                    Log.e("sec", "errpr")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

    override fun onLocationChanged(location: Location) {}

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}
}