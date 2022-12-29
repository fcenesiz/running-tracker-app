package com.fcenesiz.runningtrackerapp.other.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class DefaultLocationClient(
    private val context: Context,
    val client: FusedLocationProviderClient
) : LocationClient {

    var locationCallback : LocationCallback? = null

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long, locationCallback: LocationCallback): Flow<Location> {

        return callbackFlow {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled){
                throw LocationClient.LocationException("GPS is disabled")
            }
            println("KO")

            this@DefaultLocationClient.locationCallback = locationCallback
        }
    }



    fun removeLocationUpdates(){

    }

}
