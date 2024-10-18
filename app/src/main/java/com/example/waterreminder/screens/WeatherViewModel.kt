package com.example.waterreminder.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.launch
import com.example.waterreminder.api.*
import com.example.waterreminder.dao.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherViewModel(application: Application) : AndroidViewModel(application) {
    private val locationDao: LocationDao
    private val currentDao: CurrentDao

    init {
        val database = Room.databaseBuilder(
            application,
            LocationDatabase::class.java,
            LocationDatabase.NAME
        ).build()

        locationDao = database.getLocationDao()
        currentDao = database.getCurrentDao()

    }

    // Se crea una instancia de la API de clima utilizando Retrofit.
    private val weatherApi = RetrofitInstance.weatherApi

    // Se utiliza un MutableLiveData para almacenar el estado de la respuesta de la API de clima.
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()

    // La variable pública que expone los datos de clima a la UI (pantallas).
    // Aquí se utiliza LiveData para que los observadores de la UI reciban actualizaciones cuando los datos cambien.
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String) {
        // Se actualiza el estado del LiveData para mostrar que se está cargando la información.
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather("f92b052b78b5485491824426241510", city)
                if (response.isSuccessful) {
                    response.body()?.let { weatherModel ->
                        _weatherResult.value = NetworkResponse.Success(weatherModel)

                        // Guardar en la base de datos local usando IO para evitar bloqueo del hilo principal.
                        withContext(Dispatchers.IO) {
                            locationDao.addLocation(weatherModel.location)
                            currentDao.addCurrent(weatherModel.current)
                        }
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to fetch weather")
                }
            } catch (e: Exception) {
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
                getOfflineData()
            }

        }
    }

    fun getOfflineData() {
        viewModelScope.launch {
            val locations = locationDao.getAll().value // Accede al valor actual del LiveData
            val currents = currentDao.getAll().value

            val lastLocation = locations?.lastOrNull()
            val lastCurrent = currents?.lastOrNull()

            if (lastLocation != null && lastCurrent != null) {
                _weatherResult.value = NetworkResponse.Success(
                    WeatherModel(location = lastLocation, current = lastCurrent)
                )
            } else {
                _weatherResult.value = NetworkResponse.Error("No offline data available")
            }
        }
    }

    }