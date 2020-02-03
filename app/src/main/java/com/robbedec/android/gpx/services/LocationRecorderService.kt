package com.robbedec.android.gpx.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.robbedec.android.gpx.PusherApplication
import com.robbedec.android.gpx.data.repositories.TrackRepository
import com.robbedec.android.gpx.domain.TrackPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LocationRecorderService : Service() {

    @Inject lateinit var trackRepository: TrackRepository

    private var trackId: Long? = null
    private var segmentId: Long? = null

    private val serviceBinder = LocationRecorderServiceBinder()

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
        private const val CHANNEL_ID = "1337"
    }

    inner class LocationRecorderServiceBinder: Binder() {
        fun getService() = LocationRecorderService()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return serviceBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        PusherApplication.appComponent.inject(this)

        trackId = intent?.extras?.getLong("TRACK_ID")
        segmentId = intent?.extras?.getLong("SEGMENT_ID")

        startLocationUpdates()

        // What should the system do with low memory
        //return super.onStartCommand(intent, flags, startId)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }

    fun startLocationUpdates() {
        // TODO: start

        startForeground(1337, buildForegroundNotification())

        fusedLocationClient.requestLocationUpdates(createLocationRequest(),
            locationCallback,
            Looper.getMainLooper())
    }

    fun resumeLocationUpdates() {
        // TODO: create new segment and start location updates
    }

    fun pauseLocationUpdates() {
        // TODO: finish segment and pause location updates
        stopLocationUpdates()
    }

     fun stopLocationUpdates() {
        // TODO: finish track
         Timber.i("Service stopped")

         fusedLocationClient.removeLocationUpdates(locationCallback)
         stopSelf()
    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    /**
     * Defines what to do when the callback sends a new location.
     * Store the [TrackPoint] in the database.
     *
     * @param newLocation The latest recorded location.
     */
    private fun registerNewLocation(newLocation: LocationResult) {
        Timber.i("Lat: ${newLocation.lastLocation.latitude} | Long: ${newLocation.lastLocation.longitude}")

        GlobalScope.launch {
            newLocation.lastLocation.apply {
                // TODO: only save requests with accuracy < X amount of meters from target ex. if (accuracy < 20)
                trackRepository.insertTrackPoint(TrackPoint(trackSegmentId = segmentId!!, longitude = longitude, latitude = latitude, elevation = altitude))
            }
        }
    }

    /**
     * After Android Oreo, a user should be notified when the application is performing long-running operation in the background.
     *
     * @return a [Notification] that is displayed to the user while the operation is running.
     */
    private fun buildForegroundNotification(): Notification {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)

       return builder.setOngoing(true)
           .setContentTitle("Title")
           .setContentText("ContentText")
           .setSmallIcon(android.R.drawable.ic_notification_overlay)
           .setTicker("Ticker text")
           .build()
    }
}