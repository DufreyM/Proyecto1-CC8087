package com.example.waterreminder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class SharedViewModel : ViewModel() {
    // LiveData para almacenar la ciudad
    private val _city = MutableLiveData<String>("")
    val city: LiveData<String> = _city
    // Método para actualizar la ciudad
    fun updateCity(newCity: String) {
        _city.value = newCity
    }
}