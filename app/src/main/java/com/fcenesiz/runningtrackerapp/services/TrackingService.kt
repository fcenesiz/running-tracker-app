package com.fcenesiz.runningtrackerapp.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.location.LocationManagerCompat.requestLocationUpdates
import androidx.core.location.LocationRequestCompat.Quality
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fcenesiz.runningtrackerapp.R
import com.fcenesiz.runningtrackerapp.other.Constants.ACTION_PAUSE_SERVICE
import com.fcenesiz.runningtrackerapp.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.fcenesiz.runningtrackerapp.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.fcenesiz.runningtrackerapp.other.Constants.ACTION_STOP_SERVICE
import com.fcenesiz.runningtrackerapp.other.Constants.FASTEST_LOCATION_INTERVAL
import com.fcenesiz.runningtrackerapp.other.Constants.LOCATION_UPDATE_INTERVAL
import com.fcenesiz.runningtrackerapp.other.Constants.NOTIFICATION_CHANNEL_ID
import com.fcenesiz.runningtrackerapp.other.Constants.NOTIFICATION_CHANNEL_NAME
import com.fcenesiz.runningtrackerapp.other.Constants.NOTIFICATION_ID
import com.fcenesiz.runningtrackerapp.other.TrackingUtility
import com.fcenesiz.runningtrackerapp.other.location.DefaultLocationClient
import com.fcenesiz.runningtrackerapp.ui.MainActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.StreetViewPanoramaLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

typealias Polyline = MutableList<LatLng>
typealias Polylines = MutableList<Polyline>

class TrackingService : LifecycleService() {

    var isFirstRun = true

    lateinit var client: FusedLocationProviderClient

    companion object {
        val isTracking = MutableLiveData<Boolean>()
        val pathPoints = MutableLiveData<Polylines>()
    }

    private fun postInitialValues() {
        isTracking.postValue(false)
        pathPoints.postValue(mutableListOf())
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate() {
        super.onCreate()
        postInitialValues()

        client = LocationServices.getFusedLocationProviderClient(this)
        isTracking.observe(this) {
            updateLocationTracking(it)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                        Timber.d("Starting service...")
                    } else {
                        Timber.d("Resuming service...")
                        startForegroundService()
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    Timber.d("Paused service")
                    pauseService()
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped service")
                }
            }

        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun pauseService(){
        isTracking.postValue(false)
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    private fun updateLocationTracking(isTracking: Boolean) {
        if (isTracking) {
            if (TrackingUtility.hasLocationPermissions(this)) {

                val request = LocationRequest.Builder(LOCATION_UPDATE_INTERVAL)
                    .setMinUpdateIntervalMillis(FASTEST_LOCATION_INTERVAL)
                    .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                    .build()

                client.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        } else {
                client.removeLocationUpdates(locationCallback)
        }
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if (isTracking.value!!) {

                result.locations.let { locations ->
                    for (location in locations) {
                        addPathPoint(location)
                        Timber.d("NEW LOCATION: ${location.latitude}, ${location.longitude}")
                    }
                }
            }
        }
    }

    private fun addPathPoint(location: Location?) {
        location?.let {
            val position = LatLng(it.latitude, it.longitude)
            pathPoints.value?.apply {
                last().add(position)
                pathPoints.postValue(this)
            }
        }
    }

    private fun addEmptyPolyline() = pathPoints.value?.apply {
        add(mutableListOf())
        pathPoints.postValue(this)
    } ?: pathPoints.postValue(mutableListOf(mutableListOf()))

    private fun startForegroundService() {
        this.addEmptyPolyline()
        isTracking.postValue(true)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        createNotificationChannel(notificationManager)

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
            .setContentTitle("Running Tracker App")
            .setContentText("00:00:00")
            .setContentIntent(getMainActivityPendingIntent())
            .build()

        startForeground(NOTIFICATION_ID, notificationBuilder)
    }

    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = ACTION_SHOW_TRACKING_FRAGMENT
        },
        FLAG_IMMUTABLE
    )

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW // to don't get vibrate or sound
        )
        notificationManager.createNotificationChannel(channel)
    }

}