package com.fcenesiz.runningtrackerapp

import android.app.Application
import android.util.Log
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }

}