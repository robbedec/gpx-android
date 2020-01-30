package com.robbedec.android.gpx.domain

import androidx.room.Embedded
import androidx.room.Relation

data class TrackSegmentWithPoints (

    @Embedded var trackSegment: TrackSegment,

    @Relation(parentColumn = "id", entityColumn = "trackSegmentId")
    var trackPoints: List<TrackPoint>
    )