package com.example.waterreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent {
                scope.launch { drawerState.close() }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Principal") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* User Profile Action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_profile),
                                contentDescription = "Profile"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            MainScreen(Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun DrawerContent(onCloseDrawer: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFFB0C4DE)) // Color de fondo del menú
            .padding(16.dp)
    ) {
        Text(
            text = "PRINCIPAL",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { onCloseDrawer() }
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
                .clickable { /* Acción para Mis Estadísticas */ }
        )
        Text(
            text = "MIS LOGROS",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { /* Acción para Mis Logros */ }
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
