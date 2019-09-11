package com.smrahmadi.cityguid.data.repository

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData


class LocationRepository(var context: Context) : LocationListener {

    companion object {
        const val TAG = "LocationRepository"
    }

    var locationManager: LocationManager
    private var locationData: MutableLiveData<Location?>? = null

    init {
        locationData = MutableLiveData()
        locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        try {
            // Request location updates
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0L,
                0f,
                this
            )
        } catch (ex: SecurityException) {
            Log.d(TAG, "Security Exception, no location available")
        }
    }

    override fun onLocationChanged(p0: Location?) {
        Log.v(TAG, "onLocationChanged ${p0!!.latitude}  ||| ${p0.longitude}")
        locationData!!.postValue(p0)
        locationManager.removeUpdates(this)
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        Log.v(TAG, "onStatusChanged")
    }

    override fun onProviderEnabled(p0: String?) {
        Log.v(TAG, "onProviderEnabled")
    }

    override fun onProviderDisabled(p0: String?) {
        Log.v(TAG, "onProviderDisabled")
    }

    fun getLocationData(): MutableLiveData<Location?>? {
        return locationData
    }
}