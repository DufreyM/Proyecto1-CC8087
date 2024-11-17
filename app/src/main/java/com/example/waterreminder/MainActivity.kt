package com.example.waterreminder


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.waterreminder.navigation.SeaFriend
import com.example.waterreminder.screens.WeatherViewModel
import com.example.waterreminder.viewmodel.ProgressViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear ViewModel y manejar errores potenciales de inicialización
        try {
            val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]


            setContent {
                val progressViewModel: ProgressViewModel = viewModel()

                SeaFriend(weatherViewModel= weatherViewModel, progressViewModel = progressViewModel)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}