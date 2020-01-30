package com.robbedec.android.gpx.di

import android.app.Application
import com.robbedec.android.gpx.services.LocationRecorderService
import com.robbedec.android.gpx.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun inject(fragment: HomeFragment)
    fun inject(service: LocationRecorderService)
}