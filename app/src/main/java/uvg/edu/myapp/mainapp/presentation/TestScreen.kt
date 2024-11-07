package uvg.edu.myapp.mainapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TestScreen(
    screenTitle: String,
    mainViewModel: MainViewModel? = null,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = screenTitle,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Blue,
            modifier = Modifier.clickable { onClick() }
        )

        if(mainViewModel != null) {
            val appState by mainViewModel.appState.collectAsState()

            Text(text = "Cuenta en el ViewModel: ${appState.counter}",
                modifier = Modifier.padding(vertical = 10.dp))

            Button(
                onClick = { mainViewModel.addCounter() },
                modifier = Modifier.padding(vertical = 15.dp)
            ) {
                Text(text = "Sumar")
            }
            Button(
                onClick = { mainViewModel.logUserOut() },
                modifier = Modifier.padding(vertical = 15.dp)
            ) {
                Text(text = "Cerrar sesión")
            }
        }
    }
}