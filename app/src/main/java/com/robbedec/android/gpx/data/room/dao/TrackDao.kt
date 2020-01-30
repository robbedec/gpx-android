package com.robbedec.android.gpx.data.room.dao

import androidx.room.*
import com.robbedec.android.gpx.domain.Track
import com.robbedec.android.gpx.domain.TrackPoint
import com.robbedec.android.gpx.domain.TrackSegment
import com.robbedec.android.gpx.domain.TrackWithSegments

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: Track)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trackSegment: TrackSegment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trackPoint: TrackPoint)

    @Update
    fun update(track: Track)

    @Transaction
    @Query("SELECT * FROM track_table")
    fun getTracks(): List<TrackWithSegments>

    @Query("DELETE FROM track_table")
    fun clear()
}