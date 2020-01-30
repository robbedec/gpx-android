package com.robbedec.android.gpx.services

import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import timber.log.Timber

class LocationRecorderService : Service() {

    private val CHANNEL_ID = "1337"

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

    companion object {

        fun startService(context: Context) {
            val intent = Intent(context, LocationRecorderService::class.java)
            ContextCompat.startForegroundService(context, intent)
        }

        fun stopService(context: Context) {
            val intent = Intent(context, LocationRecorderService::class.java)
            context.stopService(intent)
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

        // What should the system do with low memory
        //return super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }

    private fun startLocationUpdates() {
        // TODO: start

        startForeground(1337, buildForegroundNotification())

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

     private fun stopLocationUpdates() {
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

    /**
     * After Android Oreo, a user should be notified when the application is performing long-running operation in the background.
     *
     * @return a [Notification] that is displayed to the user while the operation is running.
     */
    private fun buildForegroundNotification(): Notification {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)

       return builder.setOngoing(true)
           .setContentTitle("Titel")
           .setContentText("ContentText")
           .setSmallIcon(android.R.drawable.ic_notification_overlay)
           .setTicker("Ticker text")
           .build()
    }
}