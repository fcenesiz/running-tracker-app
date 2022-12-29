package com.fcenesiz.runningtrackerapp.other.location

import android.location.Location
import com.google.android.gms.location.LocationCallback
import kotlinx.coroutines.flow.Flow

interface LocationClient {

    fun getLocationUpdates(interval: Long, locationCallback: LocationCallback): Flow<Location>

    class LocationException(message: String) : Exception()
}