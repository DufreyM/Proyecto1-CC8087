package com.example.waterreminder.screens

import com.example.waterreminder.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    // Background color for the screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF95E0E1)), // light blue background
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Image (use your own image here)
            Image(
                painter = painterResource(id = R.drawable.profile_image), // Usa el nombre correcto de tu imagen
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .padding(15.dp)
                    .clip(shape = CircleShape)

            )
            // Profile Title
            Text(
                text = "Mi Perfil",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            // Configurar Peso Button
            Button(
                onClick = { /* No functionality */ },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFF18C5C7),
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.Blue
                )
            ) {
                Text(text = "Configurar Peso", fontSize = 18.sp)
            }

            // Configurar Altura Button
            Button(
                onClick = { /* No functionality */ },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(containerColor = Color(0xFF18C5C7), contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.Blue)
            ) {
                Text(text = "Configurar Altura", fontSize = 18.sp)
            }

            // Configurar Actividad Button
            Button(
                onClick = { /* No functionality */ },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(containerColor = Color(0xFF18C5C7), contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.Blue)
            ) {
                Text(text = "Configurar Actividad", fontSize = 18.sp)
            }

            // Configurar Ciudad Button
            Button(
                onClick = { /* No functionality */ },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(containerColor = Color(0xFF18C5C7), contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.Blue)
            ) {
                Text(text = "Configurar Ciudad", fontSize = 18.sp)
            }

            // Calcular Meta Button
            Button(
                onClick = { /* No functionality */ },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonColors(containerColor = Color(0xFF18C5C7), contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.Blue)

            ) {
                Text(text = "⚡ Calcular Meta", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMiPerfilScreen() {
    ProfileScreen()
}