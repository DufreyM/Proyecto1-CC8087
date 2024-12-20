package com.example.waterreminder

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cloud: String,
    val feelslike_c: String,
    val feelslike_f: String,
    val gust_kph: String,
    val gust_mph: String,
    val humidity: String,
    val is_day: String,
    val last_updated: String,
    val last_updated_epoch: String,
    val precip_in: String,
    val precip_mm: String,
    val pressure_in: String,
    val pressure_mb: String,
    val temp_c: String,
    val temp_f: String,
    val uv: String,
    val vis_km: String,
    val vis_miles: String,
    val wind_degree: String,
    val wind_dir: String,
    val wind_kph: String,
    val wind_mph: String,

    // Campos de Location
    val country: String,
    val lat: String,
    val localtime: String,
    val localtime_epoch: String,
    val lon: String,
    val name: String,
    val region: String,
    val tz_id: String,

    // Condición (parte del Current)
    val condition_text: String,
    val condition_icon: String,
    val condition_code: String
)

