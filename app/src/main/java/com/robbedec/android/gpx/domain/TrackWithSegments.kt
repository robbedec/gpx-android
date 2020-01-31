package com.robbedec.android.gpx.domain

import androidx.room.Embedded
import androidx.room.Relation

data class TrackWithSegments(@Embedded var track: Track, @Relation(parentColumn = "id", entityColumn = "trackId", entity = TrackSegment::class) var segments: List<TrackSegmentWithPoints>) {

    /**
     * Takes every [TrackSegment] and adds all the distances together.
     *
     * @return The total distance recorded in the [Track].
     */
    fun calculateDistance() = segments.fold(0f) { acc, trackSegmentWithPoints -> acc + trackSegmentWithPoints.calculateDistance() }
}