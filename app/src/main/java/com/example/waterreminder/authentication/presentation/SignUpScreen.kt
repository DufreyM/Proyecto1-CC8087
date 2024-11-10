package com.example.waterreminder.authentication.presentation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.waterreminder.R
import com.example.waterreminder.additionals.factory.viewModelFactory
import com.example.waterreminder.additionals.injection.MyApp

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel = viewModel(
        factory = viewModelFactory { AuthViewModel(MyApp.appModule.authRepository) },
    ),
    onSignUpSuccess: () -> Unit,
    onSignInClick: () -> Unit
) {
    val authState by authViewModel.authState.collectAsState()

    when(authState) {
        is AuthState.Idle -> {
            SignUpForm(
                authViewModel,
                onSignInClick = onSignInClick
            )
        }
        is AuthState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is AuthState.Authenticated -> {
            onSignUpSuccess()
        }
        is AuthState.Error -> {
            authViewModel.resetAuthState()
        }
    }
}

@Composable
fun SignUpForm(
    authViewModel: AuthViewModel,
    onSignInClick: () -> Unit
) {
    val userViewState by authViewModel.userViewState.collectAsState()
    var focused = false

    if(userViewState.errorMessage != null) {
        val context = LocalContext.current
        Toast.makeText(context, userViewState.errorMessage, Toast.LENGTH_SHORT).show()
        authViewModel.setNewViewValue()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.register_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Registro",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color(0xFF18c5c7)
                ),
                modifier = Modifier.padding(top = 220.dp, start = 25.dp)
            )
            OutlinedTextField(
                value = userViewState.nombre,
                onValueChange = { authViewModel.setNewViewValue(name = it)},
                label = { Text("Nombre de usuario") },
                placeholder = { Text("Usuario123") },
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 25.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    cursorColor = Color.Gray,
                    focusedBorderColor = Color(0xFF18c5c7),
                    unfocusedBorderColor = Color(0xFF18c5c7).copy(alpha = 0.5f),
                ),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
            )

            Box(
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 30.dp)
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if(focused) Color(0xFF18c5c7) else Color(0xFF18c5c7).copy(alpha = 0.5f),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Text(text = userViewState.gender)
                    Image(
                        painter = painterResource(id = R.drawable.arrow_down_icon),
                        contentDescription = "DropDown Icon",
                        modifier = Modifier.size(25.dp).clickable {
                            focused = true
                            authViewModel.dropGenderMenu(true)
                        },
                        contentScale = ContentScale.Fit
                    )
                }
                DropdownMenu(
                    expanded = userViewState.dropGenderSelector,
                    onDismissRequest = {
                        focused = false
                        authViewModel.dropGenderMenu(false)
                    }
                ) {
                    listOf("Hombre", "Mujer").forEach {gender ->
                        DropdownMenuItem(text = {
                            Text(
                                text = gender,
                                textAlign = TextAlign.Center
                            )
                        },
                            onClick = {
                                authViewModel.setNewViewValue(gender = gender)
                                focused = false
                                authViewModel.dropGenderMenu(false)
                            })
                    }
                }
            }

            OutlinedTextField(
                value = userViewState.email,
                onValueChange = { authViewModel.setNewViewValue(email = it) },
                label = { Text("Email") },
                placeholder = { Text("tuCorreo@example.com") },
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 25.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    cursorColor = Color.Gray,
                    focusedBorderColor = Color(0xFF18c5c7),
                    unfocusedBorderColor = Color(0xFF18c5c7).copy(alpha = 0.5f),
                ),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
            )
            OutlinedTextField(
                value = userViewState.password,
                onValueChange = { authViewModel.setNewViewValue(password = it) },
                label = { Text("Contraseña") },
                placeholder = { Text("tuContraseña123") },
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 25.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray
                ),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    cursorColor = Color.Gray,
                    focusedBorderColor = Color(0xFF18c5c7),
                    unfocusedBorderColor = Color(0xFF18c5c7).copy(alpha = 0.5f),
                ),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp)
            ) {
                AuthOption(
                    modifier = Modifier.padding(start = 25.dp),
                    image = R.drawable.google_icon
                )
                AuthOption(
                    modifier = Modifier.padding(start = 15.dp),
                    image = R.drawable.facebook_icon
                )
            }
            OutlinedButton(
                onClick = {
                    authViewModel.registerUser(
                        name = userViewState.nombre,
                        email = userViewState.email,
                        password = userViewState.password,
                        gender = userViewState.gender
                    )
                          },
                modifier = Modifier
                    .padding(top = 30.dp, end = 25.dp)
                    .align(alignment = Alignment.End),
                colors = ButtonDefaults.outlinedButtonColors (
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Gray
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Ingresar",
                    style = TextStyle(
                        fontSize = 22.sp
                    ),
                    modifier = Modifier.padding(5.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, end = 25.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "¿Eres miembro? ",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "Accede",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.clickable { onSignInClick() }
                )
            }
        }
    }
}