package com.example.waterreminder.screens

import com.example.waterreminder.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.waterreminder.additionals.factory.viewModelFactory
import com.example.waterreminder.additionals.injection.MyApp
import com.example.waterreminder.authentication.presentation.AuthViewModel

@Composable
fun ProfileScreen(
    viewModel: WeatherViewModel,
    authViewModel: AuthViewModel = viewModel(
        factory = viewModelFactory { AuthViewModel(MyApp.appModule.authRepository) },
    ),
    onSingOut: () -> Unit
) {
    var showCityInput by remember { mutableStateOf(false) }
    var city by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val profileViewState by authViewModel.profileViewState.collectAsState()
    val userViewState by authViewModel.userViewState.collectAsState()

    authViewModel.getUserInfo()

    if (userViewState.seeUserData) {
        AlertDialog(
            onDismissRequest = { authViewModel.showDataWindow(false) },
            title = {
                Text(
                    text = "Tus Datos",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
                    },
            text = {
                Text(
                    text = "Nombre de usuario: ${profileViewState.username}\nSexo: ${profileViewState.gender}\nNivel: ${profileViewState.level}\nMonedas: ${profileViewState.coins}",
                )
                   },
            confirmButton = {
                TextButton(
                    onClick = { authViewModel.showDataWindow(false) }
                ) {
                    Text("Volver")
                }
            }
        )
    }

    // Pantalla de perfil
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF95E0E1)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Aquí irá la foto de perfil del usuario
            Image(
                painter = painterResource(
                    id = if(profileViewState.gender == "Mujer") R.drawable.female_user_icon else R.drawable.male_user_icon
                ),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .padding(15.dp)
                    .clip(shape = CircleShape)
            )
            // Profile Title
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Mi Perfil ",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Text(
                    text = "@${profileViewState.username}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .clickable { authViewModel.showDataWindow(true) }
                )
            }

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
                onClick = { showCityInput = true },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(containerColor = Color(0xFF18C5C7), contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.Blue)
            ) {
                Text(text = "Configurar Ciudad", fontSize = 18.sp)
            }

            if (showCityInput) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("Ingresar ciudad") },
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White)
                    )
                    IconButton(
                        onClick = {
                            viewModel.getData(city)  // Llamada a la función de búsqueda en el ViewModel
                            showCityInput = false    // Oculta el campo de entrada
                            keyboardController?.hide() // Oculta el teclado
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    }
                }
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

            // Cerra Sesión Button
            Button(
                onClick = {
                    authViewModel.logOutUser()
                    onSingOut()
                          },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(containerColor = Color(0xFF18C5C7), contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.Blue)
            ) {
                Text(text = "Cerrar Sesión", fontSize = 18.sp)
            }
        }
    }
}