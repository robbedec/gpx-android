package com.robbedec.android.gpx.data.repositories

import androidx.lifecycle.LiveData
import com.robbedec.android.gpx.data.room.GpxDatabase
import com.robbedec.android.gpx.data.room.dao.TrackDao
import com.robbedec.android.gpx.domain.Track
import com.robbedec.android.gpx.domain.TrackPoint
import com.robbedec.android.gpx.domain.TrackSegment
import com.robbedec.android.gpx.domain.TrackWithSegments
import com.robbedec.android.gpx.domain.interfaces.ITrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TrackRepository @Inject constructor(gpxDatabase: GpxDatabase) : ITrackRepository {

    private val trackDao: TrackDao = gpxDatabase.trackDao

    suspend fun insertTrack(track: Track): Long {
        return withContext(Dispatchers.IO) {
            trackDao.insert(track)
        }
    }

    suspend fun insertSegment(segment: TrackSegment): Long {
        return withContext(Dispatchers.IO) {
            trackDao.insert(segment)
        }
    }

    suspend fun insertTrackPoint(point: TrackPoint) {
        withContext(Dispatchers.IO) {
            trackDao.insert(point)
        }
    }

    suspend fun updateTrack(track: Track) {
        withContext(Dispatchers.IO) {
            trackDao.update(track)
        }
    }

    suspend fun getTracks(): List<TrackWithSegments> {
        return withContext(Dispatchers.IO) {
            val tracks = trackDao.getTracks()
            tracks
        }
    }

    fun getTrackById(id: Long): LiveData<TrackWithSegments> {
        //return withContext(Dispatchers.IO) {
          return  trackDao.getTrackById(id)
        //}
    }

    suspend fun clearTracks() {
        withContext(Dispatchers.IO) {
            trackDao.clear()
        }
    }
}