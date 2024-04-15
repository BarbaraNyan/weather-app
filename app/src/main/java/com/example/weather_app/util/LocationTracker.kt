package com.example.weather_app.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import java.lang.ref.WeakReference
import java.util.*

class LocationTracker(context: Context) {
    private var deviceLocation: Location? = null
    private val context: WeakReference<Context>
    private var locationManager: LocationManager? = null

    init {
        this.context = WeakReference(context)
        initializeLocationProviders()
    }

    private fun initializeLocationProviders() {
        if (null == locationManager) {
            locationManager = context.get()
                ?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        locationManager?.apply {
            val isGPSEnabled = isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = isProviderEnabled(LocationManager.PASSIVE_PROVIDER)

            if (ActivityCompat.checkSelfPermission(
                    context.get()!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context.get()!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                if (isGPSEnabled) {
                    var usedLocationClient =
                        LocationServices.getFusedLocationProviderClient(context.get()!!)
                    usedLocationClient.lastLocation
                        .addOnSuccessListener { location ->
                            if (location != null) {
                                deviceLocation?.latitude = location.latitude
                                deviceLocation?.longitude = location.longitude
                            }
                        }
                }
                if (null == deviceLocation && isNetworkEnabled) {
                    deviceLocation =
                        locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
            }
        }
    }

    fun getLocation(): Address? {
        val latitude = deviceLocation?.latitude ?: 0.0
        val longitude = deviceLocation?.longitude ?: 0.0
        val addressList =
            Geocoder(context.get(), Locale.ENGLISH).getFromLocation(latitude, longitude, 1)
        return if (addressList.isEmpty()) null else addressList[0]
    }

}