package com.robbedec.android.gpx.domain

data class Track(var id: Long = 0L, var name: String = "", var segments: List<TrackSegment> = listOf()) {
}