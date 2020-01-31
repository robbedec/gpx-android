package com.robbedec.android.gpx.ui.home

import androidx.lifecycle.*
import com.robbedec.android.gpx.data.repositories.TrackRepository
import com.robbedec.android.gpx.domain.Track
import com.robbedec.android.gpx.domain.TrackSegment
import com.robbedec.android.gpx.domain.TrackWithSegments
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

    private var _updateTrack = MediatorLiveData<TrackWithSegments>()
    val updateTrack: LiveData<TrackWithSegments>
        get() = _updateTrack

    var _currentTrack = trackRepository.getTrackById(_newTrackId.value ?: 0)


    init {
        Timber.i("test")
        _updateTrack.addSource(newTrackId) { res ->
            viewModelScope.launch {
                Timber.i("RES $res")
                _currentTrack = trackRepository.getTrackById(res)
            }
        }
    }

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
            val track = _currentTrack!!.value!!

            Timber.i("${track.track.id}")
            Timber.i("${track.segments.first().trackSegment.id}")
            Timber.i("${track.segments.first().trackPoints.first().time} en ${track.segments.first().trackPoints.first().latitude}")
            Timber.i("${track.calculateDistance()}")
        }
    }
}