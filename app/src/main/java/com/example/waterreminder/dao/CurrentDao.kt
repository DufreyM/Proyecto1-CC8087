package com.example.waterreminder.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.waterreminder.api.Current

@Dao
interface CurrentDao {
    @Query("SELECT * FROM current_table")
    fun getAll(): LiveData<List<Current>>

    @Insert
    fun addCurrent(current: Current)

    @Delete
    fun deleteCurrent(current: Current)
}

