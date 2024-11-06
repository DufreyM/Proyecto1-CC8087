package com.example.waterreminder.screens

import android.os.Build
import androidx.annotation.RequiresApi
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mascota: Int,
    onIngresarBebidaClick: () -> Unit // Función para cambiar a la pantalla de bebidas
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF95E0E1), Color(0xFFFFEAC2)))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mascota)
                    .decoderFactory(ImageDecoderDecoder.Factory())
                    .build(),
                contentDescription = "Mascota GIF",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de "Ingresar Bebida"
            Button(
                onClick = onIngresarBebidaClick, // Llama a la función para cambiar de pantalla
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.7f)
                    .clip(CircleShape)
                    .shadow(8.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF18C5C7),
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.Blue
                )
            ) {
                Text("Ingresar Bebida", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Botón de "Tomar Agua"
            var waterConsumed by remember { mutableIntStateOf(0) }

            Button(
                onClick = { waterConsumed += 100 }, // Aumenta en 100 mL el consumo de agua cada vez que se presiona
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.7f)
                    .clip(CircleShape)
                    .shadow(8.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF18C5C7),
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.Blue
                )
            ) {
                Text("Tomar agua", fontSize = 16.sp)
            }

            Text(
                text = "Agua consumida: $waterConsumed mL",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
