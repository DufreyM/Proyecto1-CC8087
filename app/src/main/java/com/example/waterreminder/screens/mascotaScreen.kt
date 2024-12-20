package com.example.waterreminder.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.waterreminder.PreferencesManager
import com.example.waterreminder.R

@Composable
fun MascotaScreen(preferencesManager: PreferencesManager,onMascotaSelected: (Int) -> Unit) { // Cambiado a Int
    // Lista de mascotas disponibles
    val mascotas = listOf(
        R.drawable.hipopotamo,
        R.drawable.perro,
        R.drawable.loro,
        R.drawable.rana
    )
    var selectedMascota by remember { mutableIntStateOf(-1) }
    var showDialog by remember { mutableStateOf(false) }

    // Diálogo de confirmación
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Agregar Mascota Principal") },
            text = { Text("¿Quieres agregar esta mascota como tu mascota principal?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (selectedMascota != -1) {
                            onMascotaSelected(selectedMascota)
                        }
                        showDialog = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
    // Contenido de la pantalla
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF2F8))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(mascotas) { mascota ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedMascota = mascota
                        showDialog = true
                    }
                    .padding(16.dp)
                    .background(Color.White)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = mascota,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }
}
