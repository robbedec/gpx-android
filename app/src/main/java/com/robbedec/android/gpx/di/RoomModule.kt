package com.robbedec.android.gpx.di

import android.app.Application
import android.content.SharedPreferences
import com.robbedec.android.gpx.PusherApplication
import com.robbedec.android.gpx.data.room.GpxDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoom(context: Application): GpxDatabase {
        return GpxDatabase.getInstance(context)
    }
}