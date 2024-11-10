package com.example.waterreminder.screens

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.waterreminder.WeatherDao
import com.example.waterreminder.WeatherDatabase
import com.example.waterreminder.WeatherEntity
import kotlinx.coroutines.launch
import com.example.waterreminder.api.*
import com.example.waterreminder.api.RetrofitInstance.weatherApi

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val weatherDao: WeatherDao = WeatherDatabase.getDatabase(application).weatherDao()
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult


fun getData(city: String) {
    _weatherResult.value = NetworkResponse.Loading
    viewModelScope.launch {
        if (isInternetAvailable(getApplication())) {
            try {
                // Hacer la solicitud a la API
                val response = weatherApi.getWeather(Constant.apiKey, city)
                if (response.isSuccessful) {
                    response.body()?.let { weatherModel ->
                        _weatherResult.value = NetworkResponse.Success(weatherModel)

                        // Guardar los datos en la base de datos
                        val weatherEntity = WeatherEntity(
                            cloud = weatherModel.current.cloud,
                            feelslike_c = weatherModel.current.feelslike_c,
                            feelslike_f = weatherModel.current.feelslike_f,
                            gust_kph = weatherModel.current.gust_kph,
                            gust_mph = weatherModel.current.gust_mph,
                            humidity = weatherModel.current.humidity,
                            is_day = weatherModel.current.is_day,
                            last_updated = weatherModel.current.last_updated,
                            last_updated_epoch = weatherModel.current.last_updated_epoch,
                            precip_in = weatherModel.current.precip_in,
                            precip_mm = weatherModel.current.precip_mm,
                            pressure_in = weatherModel.current.pressure_in,
                            pressure_mb = weatherModel.current.pressure_mb,
                            temp_c = weatherModel.current.temp_c,
                            temp_f = weatherModel.current.temp_f,
                            uv = weatherModel.current.uv,
                            vis_km = weatherModel.current.vis_km,
                            vis_miles = weatherModel.current.vis_miles,
                            wind_degree = weatherModel.current.wind_degree,
                            wind_dir = weatherModel.current.wind_dir,
                            wind_kph = weatherModel.current.wind_kph,
                            wind_mph = weatherModel.current.wind_mph,
                            country = weatherModel.location.country,
                            lat = weatherModel.location.lat,
                            localtime = weatherModel.location.localtime,
                            localtime_epoch = weatherModel.location.localtime_epoch,
                            lon = weatherModel.location.lon,
                            name = weatherModel.location.name,
                            region = weatherModel.location.region,
                            tz_id = weatherModel.location.tz_id,
                            condition_text = weatherModel.current.condition.text,
                            condition_icon = weatherModel.current.condition.icon,
                            condition_code = weatherModel.current.condition.code
                        )

                        Log.d("WeatherViewModel", "Datos guardados: $weatherEntity")
                        weatherDao.insertWeather(weatherEntity)
                        Log.d("WeatherViewModel", "Inserción completa para: ${weatherEntity.name}")

                        // Leer los datos guardados y verificar
                        val savedData = weatherDao.getWeatherByCity(city)
                        if (savedData != null) {
                            Log.d("WeatherViewModel", "Datos guardados encontrados: $savedData")
                        } else {
                            Log.d("WeatherViewModel", "No se encontraron datos guardados.")
                        }
                    }
                } else {
                    // Mostrar datos en Room si la API falla
                    showCachedData(city)
                }
            } catch (e: Exception) {
                // Mostrar datos en Room en caso de error de conexión
                showCachedData(city)
                Log.e("WeatherViewModel", "Error al obtener datos de la API: ${e.message}")
            }
        } else {
            // No hay conexión, mostrar datos de Room
            showCachedData(city)
        }
    }
}

    private suspend fun showCachedData(city: String) {
        val cachedWeather = weatherDao.getWeatherByCity(city)
        cachedWeather?.let {
            val current = Current(
                cloud = it.cloud,
                feelslike_c = it.feelslike_c,
                feelslike_f = it.feelslike_f,
                gust_kph = it.gust_kph,
                gust_mph = it.gust_mph,
                humidity = it.humidity,
                is_day = it.is_day,
                last_updated = it.last_updated,
                last_updated_epoch = it.last_updated_epoch,
                precip_in = it.precip_in,
                precip_mm = it.precip_mm,
                pressure_in = it.pressure_in,
                pressure_mb = it.pressure_mb,
                temp_c = it.temp_c,
                temp_f = it.temp_f,
                uv = it.uv,
                vis_km = it.vis_km,
                vis_miles = it.vis_miles,
                wind_degree = it.wind_degree,
                wind_dir = it.wind_dir,
                wind_kph = it.wind_kph,
                wind_mph = it.wind_mph,
                condition = Condition(
                    text = it.condition_text,
                    icon = it.condition_icon,
                    code = it.condition_code
                )
            )

            val location = Location(
                country = it.country,
                lat = it.lat,
                localtime = it.localtime,
                localtime_epoch = it.localtime_epoch,
                lon = it.lon,
                name = it.name,
                region = it.region,
                tz_id = it.tz_id
            )

            _weatherResult.value = NetworkResponse.Success(WeatherModel(current, location))
        } ?: run {
            _weatherResult.value = NetworkResponse.Error("No se encontraron datos en la base de datos.")
        }
    }


    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
