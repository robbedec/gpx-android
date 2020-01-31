package com.robbedec.android.gpx.domain

import androidx.room.Embedded
import androidx.room.Relation
import kotlin.math.*

data class TrackSegmentWithPoints (@Embedded var trackSegment: TrackSegment, @Relation(parentColumn = "id", entityColumn = "trackSegmentId") var trackPoints: List<TrackPoint>) {

    companion object {
        private const val earthRadiusKM = 6378.1370f
        private fun toRads(x: Double) = x * (Math.PI / 180).toFloat()
    }

    /**
     * Calculates the distance between all the TrackPoints in the TrackSegment.
     * Uses the Haversine formula. More information about this [here](https://en.wikipedia.org/wiki/Haversine_formula)
     *
     * @return The recorded distance in the [TrackSegment].
     */
    fun calculateDistance(): Float {
        var res = 0.0f

        trackPoints.takeWhile { trackPoints.indexOf(it) < trackPoints.size - 1 }.forEach {
            val pointB = trackPoints[trackPoints.indexOf(it) + 1]

            val a = it.latitude.apply { toRads(this) } to it.longitude.apply { toRads(this) }
            val b = pointB.latitude.apply { toRads(this) } to pointB.longitude.apply { toRads(this) }

            val latD = a.first - b.first
            val lonD = a.second - b.second

            val aa = sin(latD / 2.0).pow(2.0) + cos(a.first) * cos(b.first) * sin(lonD / 2.0).pow(2.0)

            val cc = 2.0 * atan2(sqrt(aa), sqrt(1.0 - aa))

            res += (cc * earthRadiusKM).toFloat()
        }
        return res
    }
}