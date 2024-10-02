package com.example.waterreminder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waterreminder.R

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, onBack: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFEAF2F8)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Imagen del perfil (Placeholder)
        Image(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Profile Icon",
            modifier = Modifier
                .size(120.dp) //
                .clip(CircleShape)
                .background(Color(0xFFDAE8FC))
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Título de "Mi Perfil"
        Text(
            text = "Mi Perfil",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botones de configuración
        ProfileOptionButton(text = "Configurar Peso", onClick = { /* Acción para configurar peso */ })
        ProfileOptionButton(text = "Configurar Altura", onClick = { /* Acción para configurar altura */ })
        ProfileOptionButton(text = "Configurar Actividad", onClick = { /* Acción para configurar actividad */ })

        Spacer(modifier = Modifier.height(40.dp))

        // Botón de calcular meta
        Button(
            onClick = { /* Acción para calcular la meta */ },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.7f)
                .clip(CircleShape)
                .shadow(4.dp)
        ) {
            Text("⚡ Calcular Meta", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de regresar
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.5f)
                .clip(CircleShape)
                .shadow(4.dp)
        ) {
            Text("Regresar", fontSize = 16.sp)
        }
    }
}

@Composable
fun ProfileOptionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB0C4DE)),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(0.8f)
            .clip(CircleShape)
            .shadow(2.dp)
    ) {
        Text(text, fontSize = 16.sp)
    }
}
