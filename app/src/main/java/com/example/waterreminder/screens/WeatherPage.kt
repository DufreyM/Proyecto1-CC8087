package com.example.waterreminder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.waterreminder.api.WeatherModel
import com.example.waterreminder.api.NetworkResponse

@Composable
fun WeatherPage(viewModel: WeatherViewModel, onAchievementsSelected: (Int) -> Unit) {

    // Estado para almacenar la ciudad ingresada por el usuario.
    var city by remember {
        mutableStateOf("")
    }

    // Observa los resultados del clima en el viewModel.
    val weatherResult = viewModel.weatherResult.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFFE6F4F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra de búsqueda.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFFE6F4F5)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(40.dp)
                    .background(Color.White),
                value = city,
                onValueChange = {
                    city = it // Actualizar el valor de la ciudad ingresada.
                },
                label = {
                    Text(text = "Buscar mi locación")
                }
            )
            IconButton(onClick = {
                viewModel.getData(city) // LLama a Viewodel para buscar el clima de la ciudad.
                keyboardController?.hide()
            }) {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "Buscar para cualquier locación"
                )
            }
        }

        // Mostrar los resultados del clima basados en el estado de la respuesta.
        when(val result = weatherResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data)
            }
            null -> {
            }
        }
    }
}


@Composable
fun WeatherDetails(data: WeatherModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFE6F4F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Elemento para mostrar la ubicación y el clima actual
        item {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFE6F4F5)),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location icon",
                    modifier = Modifier.size(40.dp)
                )
                // Muestra el nombre de la ciudad
                Text(text = data.location.name, fontSize = 30.sp)
                Spacer(modifier = Modifier.width(8.dp))
                // Muestra el país.
                Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = " ${data.current.temp_c} °c", //Temperatura actual.
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            AsyncImage(
                modifier = Modifier.size(160.dp),
                model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
                contentDescription = "Condition icon"
            )
            Text(
                text = data.current.condition.text,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))
            WeatherStatsCard(data = data) // Mostrar más información acerca del clima.
        }
        // Estadísticas adicionales del clima.
        item {
            Spacer(modifier = Modifier.height(24.dp))
            HydrationRecommendations(data.current.temp_c) // Muestra las recomendaciones basadas en el criterio de temperatura.
        }
    }
}

@Composable
fun WeatherStatsCard(data: WeatherModel) {
    Card {
        Column(modifier = Modifier.fillMaxWidth().background(Color(0xFF64B5F6))) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherKeyVal("Humidity", data.current.humidity)  // Humidity is already a double/int
                WeatherKeyVal("Wind Speed", "${data.current.wind_kph} km/h")  // Convert to string
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherKeyVal("UV", data.current.uv)  // UV is a double/int
                WeatherKeyVal("Precipitation", "${data.current.precip_mm} mm")  // Convert to string
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Pasar a string la fecha y la hora
                WeatherKeyVal("Local Time", data.location.localtime.split(" ")[1])
                WeatherKeyVal("Local Date", data.location.localtime.split(" ")[0])
            }
        }
    }
}


@Composable
fun HydrationRecommendations(tempC: String) {
    val recommendations = when {
        tempC < 15.0.toString() -> "El clima es frío. Asegúrate de beber al menos 1.5 a 2 litros de agua al día."
        tempC.toDouble() in 15.0..25.0 -> "El clima es moderado. Bebe entre 2 y 2.5 litros de agua por día."
        tempC > 25.0.toString() -> "El clima es cálido. Se recomienda beber entre 2.5 y 3 litros de agua o más."
        else -> "Mantente hidratado según tu actividad física y necesidades."
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Recomendaciones de Hidratación:",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = recommendations,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}




@Composable
fun WeatherKeyVal(key: String, value: Any) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}
