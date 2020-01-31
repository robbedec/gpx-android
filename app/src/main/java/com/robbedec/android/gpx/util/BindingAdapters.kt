package com.robbedec.android.gpx.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.robbedec.android.gpx.domain.TrackWithSegments

@BindingAdapter("trackDistance")
fun TextView.setTrackDistance(track: TrackWithSegments?) {
    track?.let {
        text = track.calculateDistance().toString()
    }
}