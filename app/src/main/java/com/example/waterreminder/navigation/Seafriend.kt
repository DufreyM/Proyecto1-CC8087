package com.example.waterreminder.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.waterreminder.screens.MainView
import com.example.waterreminder.screens.SplashScreen
import com.example.waterreminder.screens.WeatherViewModel

@Composable
fun SeaFriend(
    rootNavController: NavHostController = rememberNavController(),
    weatherViewModel: WeatherViewModel
) {
    NavHost(
        navController = rootNavController,
        route = "root_graph",
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen {
                rootNavController.navigate(route = "auth_graph") {
                    popUpTo(route = "splash") {
                        inclusive = true
                    }
                }
            }
        }

        // Navegación de Autenticación
        authNavGraph(navController = rootNavController)

        // Navegación de la aplicación central
        composable(route = "main_graph") {
            MainView(
                weatherViewModel = weatherViewModel
            )
        }
    }
}