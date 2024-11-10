package com.example.waterreminder.screens

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waterreminder.R

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mascota: Int,
    selectedDrinkVolume: String,
    onIngresarBebidaClick: () -> Unit
) {
    var waterConsumed by remember { mutableIntStateOf(0) }
    val numericVolume = selectedDrinkVolume.replace(" mL", "").toIntOrNull() ?: 0
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.main_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mascota)
                    .apply {
                        // Choose ImageDecoderDecoder for API level 28+; otherwise, use GifDecoder
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            decoderFactory(ImageDecoderDecoder.Factory())
                        } else {
                            decoderFactory(GifDecoder.Factory())
                        }
                    }
                    .build(),
                contentDescription = "Mascota GIF",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de "Ingresar Bebida"
            Button(
                onClick = onIngresarBebidaClick,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.7f)
                    .clip(CircleShape)
                    .shadow(5.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF18C5C7),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Blue,
                    disabledContentColor = Color.LightGray
                )
            ) {
                Text("Ingresar Bebida", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Botón de "Tomar Agua" con volumen seleccionado
            Button(
                onClick = {
                    waterConsumed += numericVolume
                    Toast.makeText(
                        context,
                        "¡Genial!, has consumido: $waterConsumed mL",
                        Toast.LENGTH_SHORT
                    ).show()
                          },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.7f)
                    .clip(CircleShape)
                    .shadow(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF18C5C7),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Blue,
                    disabledContentColor = Color.Red
                )
            ) {
                Text(text = "Tomar Agua")
            }
        }
    }
}
