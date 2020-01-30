package com.robbedec.android.gpx.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_segment_table")
data class TrackSegment(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    var trackId: Long = 0L
    ) {
}