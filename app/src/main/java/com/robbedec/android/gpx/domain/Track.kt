package com.robbedec.android.gpx.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
data class Track(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    var name: String = "") {
}