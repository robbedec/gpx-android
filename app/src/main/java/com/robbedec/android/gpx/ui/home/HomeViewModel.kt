package com.robbedec.android.gpx.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.robbedec.android.gpx.data.repositories.TrackRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val trackRepository: TrackRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}