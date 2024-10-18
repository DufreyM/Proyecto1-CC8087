package com.example.waterreminder.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.waterreminder.api.Current
import com.example.waterreminder.api.Location

@Database(entities = [Location::class, Current::class], version = 1, exportSchema = false)
@TypeConverters(ConditionConverter::class)
abstract class LocationDatabase : RoomDatabase() {
    companion object {
        const val NAME = "location_database"
    }

    abstract fun getLocationDao(): LocationDao
    abstract fun getCurrentDao(): CurrentDao

}