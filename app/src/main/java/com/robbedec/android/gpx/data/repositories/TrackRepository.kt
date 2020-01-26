package com.robbedec.android.gpx.data.repositories

import com.robbedec.android.gpx.data.room.GpxDatabase
import com.robbedec.android.gpx.domain.interfaces.ITrackRepository
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
class TrackRepository @Inject constructor(private val gpxDatabase: GpxDatabase) : ITrackRepository {
}