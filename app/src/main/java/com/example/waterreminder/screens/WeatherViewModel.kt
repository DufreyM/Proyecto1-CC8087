package com.example.waterreminder.screens

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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
            try {
                // Primero, verificar la conexión a internet
                if (isInternetAvailable(getApplication<Application>())) {
                    // Intentar cargar desde la base de datos si hay datos previos
                    val cachedWeather = weatherDao.getWeatherByCity(city)
                    cachedWeather?.let {
                        // Si hay datos almacenados en la base de datos, los mostramos
                        val current = Current(
                            cloud = cachedWeather.cloud,
                            feelslike_c = cachedWeather.feelslike_c,
                            feelslike_f = cachedWeather.feelslike_f,
                            gust_kph = cachedWeather.gust_kph,
                            gust_mph = cachedWeather.gust_mph,
                            humidity = cachedWeather.humidity,
                            is_day = cachedWeather.is_day,
                            last_updated = cachedWeather.last_updated,
                            last_updated_epoch = cachedWeather.last_updated_epoch,
                            precip_in = cachedWeather.precip_in,
                            precip_mm = cachedWeather.precip_mm,
                            pressure_in = cachedWeather.pressure_in,
                            pressure_mb = cachedWeather.pressure_mb,
                            temp_c = cachedWeather.temp_c,
                            temp_f = cachedWeather.temp_f,
                            uv = cachedWeather.uv,
                            vis_km = cachedWeather.vis_km,
                            vis_miles = cachedWeather.vis_miles,
                            wind_degree = cachedWeather.wind_degree,
                            wind_dir = cachedWeather.wind_dir,
                            wind_kph = cachedWeather.wind_kph,
                            wind_mph = cachedWeather.wind_mph,
                            condition = Condition(
                                text = cachedWeather.condition_text,
                                icon = cachedWeather.condition_icon,
                                code = cachedWeather.condition_code
                            )
                        )

                        val location = Location(
                            country = cachedWeather.country,
                            lat = cachedWeather.lat,
                            localtime = cachedWeather.localtime,
                            localtime_epoch = cachedWeather.localtime_epoch,
                            lon = cachedWeather.lon,
                            name = cachedWeather.name,
                            region = cachedWeather.region,
                            tz_id = cachedWeather.tz_id
                        )

                        _weatherResult.value = NetworkResponse.Success(WeatherModel(current, location))
                    } ?: run {
                        // Si no hay datos en la base de datos, hacemos la solicitud a la API
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

                                weatherDao.insertWeather(weatherEntity)
                            }
                        } else {
                            _weatherResult.value = NetworkResponse.Error("Failed to load data from API")
                        }
                    }
                } else {
                    // Sin conexión a internet, mostrar los datos en caché o un mensaje adecuado
                    val cachedWeather = weatherDao.getWeatherByCity(city)
                    cachedWeather?.let { cached ->
                        val current = Current(
                            cloud = cached.cloud,
                            feelslike_c = cached.feelslike_c,
                            feelslike_f = cached.feelslike_f,
                            gust_kph = cached.gust_kph,
                            gust_mph = cached.gust_mph,
                            humidity = cached.humidity,
                            is_day = cached.is_day,
                            last_updated = cached.last_updated,
                            last_updated_epoch = cached.last_updated_epoch,
                            precip_in = cached.precip_in,
                            precip_mm = cached.precip_mm,
                            pressure_in = cached.pressure_in,
                            pressure_mb = cached.pressure_mb,
                            temp_c = cached.temp_c,
                            temp_f = cached.temp_f,
                            uv = cached.uv,
                            vis_km = cached.vis_km,
                            vis_miles = cached.vis_miles,
                            wind_degree = cached.wind_degree,
                            wind_dir = cached.wind_dir,
                            wind_kph = cached.wind_kph,
                            wind_mph = cached.wind_mph,
                            condition = Condition(
                                text = cached.condition_text,
                                icon = cached.condition_icon,
                                code = cached.condition_code
                            )
                        )

                        val location = Location(
                            country = cached.country,
                            lat = cached.lat,
                            localtime = cached.localtime,
                            localtime_epoch = cached.localtime_epoch,
                            lon = cached.lon,
                            name = cached.name,
                            region = cached.region,
                            tz_id = cached.tz_id
                        )

                        _weatherResult.value = NetworkResponse.Success(WeatherModel(current, location))
                    } ?: run {
                        _weatherResult.value = NetworkResponse.Error("No Internet and no cached data available")
                    }
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Error occurred: ${e.message}")
            }
        }
    }


    fun isInternetAvailable(context: Context): Boolean {
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
