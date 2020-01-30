package com.robbedec.android.gpx.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.robbedec.android.gpx.util.GpxDateTimeFormat

@Entity(tableName = "trackpoint_table")
data class TrackPoint(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    var trackSegmentId: Long = 0L,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var elevation: Double = 0.0,
    val time: String = GpxDateTimeFormat.format())