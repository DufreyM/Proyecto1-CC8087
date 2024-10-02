package com.example.waterreminder.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import com.example.waterreminder.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import kotlinx.coroutines.delay

// Asegúrate de que tienes la fuente en res/font/ y actualiza el nombre según sea necesario.
val animatedFont = FontFamily(Font(R.font.letra1))

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SplashScreen() {
    // Mantener la pantalla de splash durante 4 segundos
    LaunchedEffect(true) {
        delay(4000)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB0E0E6)), // Fondo azul claro
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Cargar el GIF usando Coil con soporte para animaciones
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.agua) // Reemplaza con tu archivo de GIF en res/drawable
                    .decoderFactory(ImageDecoderDecoder.Factory()) // Activar soporte para GIF
                    .build(),
                contentDescription = "Splash GIF",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "WaterCare",
                fontSize = 32.sp,
                color = Color(0xFF008080), // Color del texto
                fontFamily = animatedFont, // Aplica la fuente animada
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
