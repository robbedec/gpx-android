package com.robbedec.android.gpx.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robbedec.android.gpx.data.repositories.TrackRepository
import com.robbedec.android.gpx.domain.Track
import com.robbedec.android.gpx.domain.TrackPoint
import com.robbedec.android.gpx.domain.TrackSegment
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val trackRepository: TrackRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            //trackRepository.clearTracks()
            trackRepository.insertTrack(Track(id = 1, name = "robbe"))
            trackRepository.insertSegment(TrackSegment(id = 1, trackId = 1))
            trackRepository.insertTrack(TrackPoint(id = 1, latitude = 5.0, longitude = 10.0, elevation = 15.0, trackSegmentId = 1))
            val tracks = trackRepository.getTracks()
            tracks.forEach { track -> Timber.i("TRACKNAME: ${track.track.name} \n LATITUDE OF FIRST POINT ${track.segments.first().trackPoints.first().elevation}") }
        }
    }
}