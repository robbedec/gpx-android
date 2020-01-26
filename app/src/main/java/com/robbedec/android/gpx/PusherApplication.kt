package com.robbedec.android.gpx

import android.app.Application
import com.robbedec.android.gpx.di.ApplicationComponent
import com.robbedec.android.gpx.di.DaggerApplicationComponent
import timber.log.Timber

class PusherApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        appComponent = DaggerApplicationComponent.builder().application(this).build()

    }
}