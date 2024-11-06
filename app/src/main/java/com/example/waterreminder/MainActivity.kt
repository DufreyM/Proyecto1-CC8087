package com.example.waterreminder

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.lifecycle.ViewModelProvider
import com.example.waterreminder.screens.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se incia el viewModel que manejara los datos del clima para que interactúe con la API.
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        setContent {
            MyApp(weatherViewModel) // Se pasa el ViewModel para que esté disponible en la interfaz
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(weatherViewModel: WeatherViewModel) {
    var showSplash by remember { mutableStateOf(true) }
    var showProfile by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf("main") }
    var mainMascota by remember { mutableIntStateOf(R.drawable.hipopotamo) }
    val waterDayProgress by remember { mutableFloatStateOf(0.70f) }
    val waterMonthProgress by remember { mutableFloatStateOf(0.95f) }
    val activityDayProgress by remember { mutableFloatStateOf(0.50f) }
    val activityMonthProgress by remember { mutableFloatStateOf(0.95f) }
    var selectedDrinkVolume by remember { mutableStateOf("100 mL") }


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(4000) // Duración de la splash screen (2 segundos)
        showSplash = false
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onCloseDrawer = { scope.launch { drawerState.close() } },
                onSelectMain = {
                    currentScreen = "main"
                    scope.launch { drawerState.close() }
                },
                onSelectMascota = {
                    currentScreen = "mascota" // Nueva opción para la pantalla de mascotas
                    scope.launch { drawerState.close() }
                },
                onSelectMyAchievements = {
                    currentScreen = "mis logros"
                    scope.launch { drawerState.close() }
                },
                onSelectMyStatistics = {
                    currentScreen = "mis estadisticas"
                    scope.launch { drawerState.close() }
                },
                // Mostrar la pantalla clima cuando el su
                onSelectWeatherPage = {
                    currentScreen = "clima" // Selección de la pantalla clima.
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        if (showSplash) {
            SplashScreen()
        } else {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(if (showProfile) "Perfil" else if (currentScreen == "mascota") "Mascotas" else "Principal") },
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
                            IconButton(onClick = { showProfile = true; currentScreen = "profile" }) {
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
                when (currentScreen) {
                    "profile" -> {
                        ProfileScreen(
                            viewModel = weatherViewModel
                        )
                    }
                    "mascota" -> {
                        MascotaScreen { selectedMascota ->
                            mainMascota = selectedMascota // Actualiza la mascota principal
                            currentScreen = "main" // Regresar a la pantalla principal
                        }
                    }

                    "mis logros" -> {
                        MyAchievementsScreen { /*Acción para la selección de algún logro, sería que la gota de agua se pinte cuando se logre el objetivo. */
                        }
                    }
                    "mis estadisticas" -> {
                        StatsScreen(
                            waterDayProgress = waterDayProgress,
                            waterMonthProgress = waterMonthProgress,
                            activityDayProgress = activityDayProgress,
                            activityMonthProgress = activityMonthProgress
                        )
                    }
                    // Cuando se selecciona la opción de clima, se muestra la pantalla WeatherPage.
                    // Se pasa el weatherViewModel a la pantalla de clima para manejar las llamadas
                    // y respuestas de la API de clima (WeatherAPI).
                    "clima" -> {
                        WeatherPage(weatherViewModel){

                        }
                    }
                    "drinks" -> {
                        DrinksScreen(
                            onDrinkSelected = { volume ->
                                selectedDrinkVolume = volume.toString()
                                currentScreen = "main" // Volver a la pantalla principal después de seleccionar
                            }
                        )
                    }
                    else -> {
                        MainScreen(
                            modifier = Modifier.padding(paddingValues),
                            mascota = mainMascota,
                            onIngresarBebidaClick = { currentScreen = "drinks" },
                            selectedDrinkVolume = selectedDrinkVolume // Muestra el volumen seleccionado en MainScreen
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun DrawerContent(
    onCloseDrawer: () -> Unit,
    onSelectMain: () -> Unit,
    onSelectMascota: () -> Unit,
    onSelectMyAchievements: () -> Unit,
    onSelectMyStatistics: () -> Unit,
    onSelectWeatherPage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFFB0C4DE))
            .padding(16.dp)
    ) {
        Text(
            text = "PRINCIPAL",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    onSelectMain() // Cambia a la pantalla principal
                    onCloseDrawer() // Cierra el menú
                }
        )
        Text(
            text = "MASCOTA",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    onSelectMascota() // Cambia a la pantalla de mascotas
                    onCloseDrawer() // Cierra el menú
                }
        )
        Text(
            text = "MIS ESTADÍSTICAS",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    onSelectMyStatistics()
                    onCloseDrawer()
                }
        )
        Text(
            text = "MIS LOGROS",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    onSelectMyAchievements()
                    onCloseDrawer()
                }
        )
        Text(
            text = "RECOMENDACIONES",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    onSelectWeatherPage()
                    onCloseDrawer()
                }
        )
    }
}