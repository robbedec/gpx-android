package com.robbedec.android.gpx.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.robbedec.android.gpx.domain.TrackPoint

@Database(entities = [TrackPoint::class], version = 1, exportSchema = false)
abstract class GpxDatabase : RoomDatabase() {

    //TODO: DAO's

    // Allows clients to access methods to create or get the database without instantiating the class
    companion object {

        // @Volatile: value will never be cached, in other words: always up to date
        // Used for multithreading
        @Volatile
        private var INSTANCE: GpxDatabase? = null

        /**
         * Claims exclusive access to the context and provides an instance (singleton) of the database.
         * Initialize the singleton if the current value of the instance is null.
         *
         * @param context contains global information about the application.
         * @return an instance of the database.
         */
        fun getInstance(context: Context): GpxDatabase {

            // Multithreading lock, this = access to the context
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, GpxDatabase::class.java, "gpx_history_database")
                        .fallbackToDestructiveMigration() // Migration to a new database scheme
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}