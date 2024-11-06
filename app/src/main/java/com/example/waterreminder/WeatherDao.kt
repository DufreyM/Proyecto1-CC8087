package com.example.waterreminder

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather_table LIMIT 1")
    fun getWeather(): WeatherEntity?

    @Query("SELECT * FROM weather_table WHERE name = :city LIMIT 1")
    suspend fun getWeatherByCity(city: String): WeatherEntity?

}
