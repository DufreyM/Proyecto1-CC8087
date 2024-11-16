package com.example.waterreminder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.example.waterreminder.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    mainNavController: NavHostController = rememberNavController(),
    weatherViewModel: WeatherViewModel
) {
    var currentScreen by remember { mutableStateOf("main") }
    var mainMascota by remember { mutableIntStateOf(R.drawable.hipopotamo) }
    val waterDayProgress by remember { mutableFloatStateOf(0.70f) }
    val waterMonthProgress by remember { mutableFloatStateOf(0.95f) }
    val activityDayProgress by remember { mutableFloatStateOf(0.50f) }
    val activityMonthProgress by remember { mutableFloatStateOf(0.95f) }
    var selectedDrinkVolume by remember { mutableStateOf("100 mL") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = mainNavController,
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
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
                        IconButton(onClick = { mainNavController.navigate("profile") }) {
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
            NavHost(
                navController = mainNavController,
                route = "main_graph",
                startDestination = "main",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(route = "main") {
                    MainScreen(
                        mascota = mainMascota,
                        onIngresarBebidaClick = { currentScreen = "drinks" },
                        selectedDrinkVolume = selectedDrinkVolume
                    )
                }

                composable(route = "mascota") {
                    MascotaScreen { selectedMascota ->
                        mainMascota = selectedMascota
                        currentScreen = "main"
                    }
                }

                composable(route = "profile") {
                    ProfileScreen(viewModel = weatherViewModel) {}
                }

                composable(route = "mis_logros") {
                    MyAchievementsScreen()
                }

                composable("mis_estadisticas") {
                    StatsScreen(
                        waterDayProgress = waterDayProgress,
                        waterMonthProgress = waterMonthProgress,
                        activityDayProgress = activityDayProgress,
                        activityMonthProgress = activityMonthProgress
                    )
                }

                composable(route = "clima") {
                    WeatherPage(weatherViewModel)
                }

                composable(route = "drinks") {
                    DrinksScreen{ volume ->
                        selectedDrinkVolume = volume.toString()
                        currentScreen = "drinks_screen"
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    onCloseDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFFB0C4DE))
            .padding(16.dp)
    ) {
        DrawerItem("PRINCIPAL") {
            navController.navigate("main") {
                popUpTo("main") { inclusive = true }
                launchSingleTop = true
            }
            onCloseDrawer()
        }
        DrawerItem("MASCOTA") {
            navController.navigate("mascota") {
                popUpTo("mascota") { inclusive = true }
                launchSingleTop = true
            }
            onCloseDrawer()
        }
        DrawerItem("MIS LOGROS") {
            navController.navigate("mis_logros") {
                popUpTo("mis_logros") { inclusive = true }
                launchSingleTop = true
            }
            onCloseDrawer()
        }
        DrawerItem("MIS ESTADÍSTICAS") {
            navController.navigate("mis_estadisticas") {
                popUpTo("mis_estadisticas") { inclusive = true }
                launchSingleTop = true
            }
            onCloseDrawer()
        }
        DrawerItem("CLIMA") {
            navController.navigate("clima") {
                popUpTo("clima") { inclusive = true }
                launchSingleTop = true
            }
            onCloseDrawer()
        }
    }
}

@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        fontSize = 18.sp,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    )
}