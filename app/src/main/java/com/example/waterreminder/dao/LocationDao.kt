package com.example.waterreminder.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.waterreminder.api.Location

@Dao
interface LocationDao {
    @Query("SELECT * FROM location_table")
    fun getAll(): LiveData<List<Location>>

    @Insert
    fun addLocation(location: Location)

    @Query("Delete FROM location_table where country = :country")
    fun deleteLocation(country: String)

}