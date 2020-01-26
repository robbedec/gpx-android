package com.robbedec.android.gpx.util

import java.text.SimpleDateFormat
import java.util.*

class GpxDateTimeFormat {

    companion object {
        private val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        fun format(date: Date = Date()): String {
            return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
        }
    }
}