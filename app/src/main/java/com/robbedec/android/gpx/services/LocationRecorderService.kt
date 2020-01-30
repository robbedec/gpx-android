package com.robbedec.android.gpx.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import timber.log.Timber

class LocationRecorderService : Service() {

    private val serviceBinder: Binder = LocationRecorderServiceBinder()

    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this@LocationRecorderService)
    }

    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                registerNewLocation(locationResult)
            }
        }
    }

    inner class LocationRecorderServiceBinder: Binder() {
        fun getService() = this@LocationRecorderService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return serviceBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.i("On start command called")
        startLocationUpdates()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }

    private fun startLocationUpdates() {
        // TODO: start

        fusedLocationClient.requestLocationUpdates(createLocationRequest(),
            locationCallback,
            Looper.getMainLooper())
    }

    private fun resumeLocationUpdates() {
        // TODO: create new segment and start location updates
    }

    private fun pauseLocationUpdates() {
        // TODO: finish segment and pause location updates
    }

     fun stopLocationUpdates() {
        // TODO: finish track
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun registerNewLocation(newLocation: LocationResult) {
        Timber.i("Lat: ${newLocation.lastLocation.latitude} \n Long: ${newLocation.lastLocation.longitude}")
    }
}