package com.robbedec.android.gpx.domain

import androidx.room.Embedded
import androidx.room.Relation

data class TrackWithSegments(

    @Embedded var track: Track,

    @Relation(parentColumn = "id", entityColumn = "trackId", entity = TrackSegment::class)
    var segments: List<TrackSegmentWithPoints>
)