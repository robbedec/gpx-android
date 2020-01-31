package com.robbedec.android.gpx.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robbedec.android.gpx.data.repositories.TrackRepository
import com.robbedec.android.gpx.domain.Track
import com.robbedec.android.gpx.domain.TrackSegment
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val trackRepository: TrackRepository) : ViewModel() {

    private var _newTrackId = MutableLiveData<Long>()
    val newTrackId: LiveData<Long>
        get() = _newTrackId

    private var _newSegmentId = MutableLiveData<Long>()
    val newSegmentId: LiveData<Long>
        get() = _newSegmentId

    fun startNewTrack() {
        viewModelScope.launch {
            _newTrackId.value = trackRepository.insertTrack(Track())
            resumeTrack()
        }
    }

    fun pauseTrack() {
        _newSegmentId.value = null
    }

    fun resumeTrack() {
        viewModelScope.launch {
            _newSegmentId.value = trackRepository.insertSegment(TrackSegment(trackId = _newTrackId.value!!))
        }
    }

    fun stopTrack() {
        _newTrackId.value = null
    }

    fun test() {
        viewModelScope.launch {
            Timber.i("${_newTrackId.value}")
            val track = trackRepository.getTrackById(_newTrackId.value!!)

            Timber.i("${track.track.id}")
            Timber.i("${track.segments.first().trackSegment.id}")
            Timber.i("${track.segments.first().trackPoints.first().time} en ${track.segments.first().trackPoints.first().latitude}")
            Timber.i("${track.calculateDistance()}")
        }
    }
}