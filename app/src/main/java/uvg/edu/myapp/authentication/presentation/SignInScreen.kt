package uvg.edu.myapp.authentication.presentation

import android.widget.Toast
import androidx.annotation.DrawableRes
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uvg.edu.myapp.R

@Composable
fun SignInScreen(
    authViewModel: AuthViewModel,
    onSignInSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    val authState by authViewModel.authState.collectAsState()

    when(authState) {
        is AuthState.Idle -> {
            SignInForm(
                authViewModel,
                onSignUpClick = onSignUpClick
            )
        }
        is AuthState.Loading -> {
            CircularProgressIndicator()
        }
        is AuthState.Authenticated -> {
            onSignInSuccess()
        }
        is AuthState.Error -> {
            SignInForm(
                authViewModel,
                errorMessage = (authState as AuthState.Error).message,
                onSignUpClick = onSignUpClick
            )
        }
    }
}

@Composable
fun SignInForm(
    authViewModel: AuthViewModel,
    errorMessage: String? = null,
    onSignUpClick: () -> Unit
) {
    val authViewState by authViewModel.authViewState.collectAsState()

    if(errorMessage != null) {
        val context = LocalContext.current
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Iniciar Sesión",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color(0xFF18c5c7)
                ),
                modifier = Modifier.padding(top = 320.dp, start = 25.dp)
            )
            OutlinedTextField(
                value = authViewState.email,
                onValueChange = { authViewModel.setNewViewValue(email = it) },
                label = { Text("Email") },
                placeholder = { Text("tuCorreo@example.com") },
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 25.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = Color(0xFF18c5c7),
                    unfocusedBorderColor = Color(0xFF18c5c7).copy(alpha = 0.5f),
                ),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
            )
            OutlinedTextField(
                value = authViewState.password,
                onValueChange = { authViewModel.setNewViewValue(password = it) },
                label = { Text("Contraseña") },
                placeholder = { Text("tuContraseña123") },
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 25.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = Color(0xFF18c5c7),
                    unfocusedBorderColor = Color(0xFF18c5c7).copy(alpha = 0.5f),
                ),
                shape = RoundedCornerShape(16.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
            )
            Text(
                text = "Olvidé mi contraseña",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF18a5c7)
                ),
                modifier = Modifier
                    .padding(top = 15.dp, end = 25.dp)
                    .align(alignment = Alignment.End)
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
                onClick = { authViewModel.loginUser(
                    email = authViewState.email,
                    password = authViewState.password
                )
                          },
                modifier = Modifier
                    .padding(top = 75.dp, end = 25.dp)
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
                    text = "Acceder",
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
                    text = "¿Eres nuevo? ",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )
                Text(
                    text = "Registrate",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.clickable { onSignUpClick() }
                )
            }
        }
    }
}

@Composable
fun AuthOption(
    modifier: Modifier = Modifier,
//    context: Context,
    @DrawableRes image: Int,
    contentDescription: String? = null
//    onGetCredentialResponse: (Credential) -> Unit = {}
) {
//    val coroutineScope = rememberCoroutineScope()
//    val credentialManager = CredentialManager.create(context)

    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color(0xFF18c5c7).copy(alpha = 0.3f),
                shape = RoundedCornerShape(14.dp)
            )
            .clip(RoundedCornerShape(14.dp))
            .clickable {
                /*
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                coroutineScope.launch {
                    try {
                        val result = credentialManager.getCredential(
                            request = request,
                            context = context
                        )

                        onGetCredentialResponse(result.credential)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        if(e is GetCredentialException) throw e
                    }
                }
                */
            }
            .padding(horizontal = 25.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = contentDescription,
            modifier = Modifier.size(30.dp)
        )
    }
}