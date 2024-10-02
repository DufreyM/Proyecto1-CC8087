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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import com.example.waterreminder.screens.MainScreen
import com.example.waterreminder.screens.MascotaScreen
import com.example.waterreminder.screens.MyAchievementsScreen
import com.example.waterreminder.screens.ProfileScreen
import com.example.waterreminder.screens.SplashScreen
import com.example.waterreminder.screens.StatsScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    var showSplash by remember { mutableStateOf(true) }
    var showProfile by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf("main") }
    var mainMascota by remember { mutableIntStateOf(R.drawable.hipopotamo) }
    val waterDayProgress by remember { mutableFloatStateOf(0.70f) }
    val waterMonthProgress by remember { mutableFloatStateOf(0.95f) }
    val activityDayProgress by remember { mutableFloatStateOf(0.50f) }
    val activityMonthProgress by remember { mutableFloatStateOf(0.95f) }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(2000) // Duración de la splash screen (2 segundos)
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
                onSelectProfile = {
                    currentScreen = "profile"
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
                                    painter = painterResource(id = R.drawable.ic_menu),
                                    contentDescription = "Menu"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { showProfile = true; currentScreen = "profile" }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_profile),
                                    contentDescription = "Profile"
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->
                when (currentScreen) {
                    "profile" -> {
                        ProfileScreen(Modifier.padding(paddingValues)) { showProfile = false; currentScreen = "main" }
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
                    else -> {
                        MainScreen(
                            modifier = Modifier.padding(paddingValues),
                            onProfileClick = { showProfile = true; currentScreen = "profile" },
                            mascota = mainMascota
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerContent(onCloseDrawer: () -> Unit, onSelectMain: () -> Unit, onSelectProfile: () -> Unit, onSelectMascota: () -> Unit, onSelectMyAchievements: () -> Unit, onSelectMyStatistics: () -> Unit) {
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
            text = "HISTORIAL",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { /* Acción para ir a Historial */ }
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
            text = "CONFIGURACIÓN",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { /* Acción para Configuración */ }
        )
    }
}
