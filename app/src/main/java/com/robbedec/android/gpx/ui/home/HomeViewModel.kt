package com.robbedec.android.gpx.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robbedec.android.gpx.data.repositories.TrackRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val trackRepository: TrackRepository) : ViewModel() {


}