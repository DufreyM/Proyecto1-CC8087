package com.example.waterreminder

import LoginScreen
import SignUpScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.waterreminder.viewmodel.AuthViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.waterreminder.screens.*

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se incia el viewModel que manejara los datos del clima para que interactúe con la API.
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                NavigationGraph(navController = navController, authViewModel = authViewModel)
            }
            MyApp(weatherViewModel) // Se pasa el ViewModel para que esté disponible en la interfaz
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    weatherViewModel: WeatherViewModel,
    mainMascota: MutableState<Int>,
    waterDayProgress: Float,
    waterMonthProgress: Float,
    activityDayProgress: Float,
    activityMonthProgress: Float
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignupScreen.route
    ) {
        composable(Screen.SignupScreen.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) }
            )
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route) }
            )
        }
        composable(Screen.MainScreen.route) {
            MainScreen(
                modifier = Modifier,
                mascota = mainMascota.value
            )
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen()
        }
        composable(Screen.MascotaScreen.route) {
            MascotaScreen { selectedMascota ->
                mainMascota.value = selectedMascota
                navController.navigate(Screen.MainScreen.route)
            }
        }
        composable(Screen.MyAchievementsScreen.route) {
            MyAchievementsScreen()
        }
        composable(Screen.StatsScreen.route) {
            StatsScreen(
                waterDayProgress = waterDayProgress,
                waterMonthProgress = waterMonthProgress,
                activityDayProgress = activityDayProgress,
                activityMonthProgress = activityMonthProgress
            )
        }
        composable(Screen.WeatherPage.route) {
            WeatherPage(weatherViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(weatherViewModel: WeatherViewModel, navController: NavHostController) {
    var showSplash by remember { mutableStateOf(true) }
    val mainMascota = remember { mutableStateOf(R.drawable.hipopotamo) }
    val waterDayProgress = 0.70f
    val waterMonthProgress = 0.95f
    val activityDayProgress = 0.50f
    val activityMonthProgress = 0.95f

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(4000) // Duración de la splash screen
        showSplash = false
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onCloseDrawer = { scope.launch { drawerState.close() } },
                onSelectMain = { navController.navigate(Screen.MainScreen.route) },
                onSelectMascota = { navController.navigate(Screen.MascotaScreen.route) },
                onSelectMyAchievements = { navController.navigate(Screen.MyAchievementsScreen.route) },
                onSelectMyStatistics = { navController.navigate(Screen.StatsScreen.route) },
                onSelectWeatherPage = { navController.navigate(Screen.WeatherPage.route) }
            )
        }
    ) {
        if (showSplash) {
            SplashScreen()
        } else {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Water Reminder") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "icono de menu",
                                    tint = Color.Black
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { navController.navigate(Screen.ProfileScreen.route) }) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "icono de perfil",
                                    tint = Color.Black
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                NavigationGraph(
                    navController = navController,
                    authViewModel = viewModel(),
                    weatherViewModel = weatherViewModel,
                    mainMascota = mainMascota,
                    waterDayProgress = waterDayProgress,
                    waterMonthProgress = waterMonthProgress,
                    activityDayProgress = activityDayProgress,
                    activityMonthProgress = activityMonthProgress
                )
            }
        }
    }
}
