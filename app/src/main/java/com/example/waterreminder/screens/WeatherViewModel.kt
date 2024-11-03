package com.example.waterreminder.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.waterreminder.api.*

class WeatherViewModel :ViewModel() {

    // Se crea una instancia de la API de clima utilizando Retrofit.
    private val weatherApi = RetrofitInstance.weatherApi

    // Se utiliza un MutableLiveData para almacenar el estado de la respuesta de la API de clima.
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()

    // La variable pública que expone los datos de clima a la UI (pantallas).
    // Aquí se utiliza LiveData para que los observadores de la UI reciban actualizaciones cuando los datos cambien.
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city : String){
        // Se actualiza el estado del LiveData para mostrar que se está cargando la información.
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try{
                // Se hace la solicitud a la API de clima, pasando una clave de API y la ciudad.
                val response = weatherApi.getWeather("f92b052b78b5485491824426241510",city)
                // Si la respuesta de la API es exitosa, se extraen los datos y se logean en consola.
                if(response.isSuccessful){
                    Log.i("response: ", response.body().toString())
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else{
                    Log.i("error: ", response.body().toString())
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            }
            catch (e : Exception){
                _weatherResult.value = NetworkResponse.Error("Failed to load data") // Aquí debería ir la api offline
            }

        }
    }

}
