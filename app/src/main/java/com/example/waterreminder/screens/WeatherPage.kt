package com.example.waterreminder.screens

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

    var city by remember {
        mutableStateOf("")
    }

    val weatherResult = viewModel.weatherResult.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f).padding(40.dp),
                value = city,
                onValueChange = {
                    city = it
                },
                label = {
                    Text(text = "Buscar mi locación")
                }
            )
            IconButton(onClick = {
                viewModel.getData(city)
                keyboardController?.hide()
            }) {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "Buscar para cualquier locación"
                )
            }

        }

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
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location icon",
                    modifier = Modifier.size(40.dp)
                )
                Text(text = data.location.name, fontSize = 30.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = " ${data.current.temp_c} °c",
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
            WeatherStatsCard(data = data)
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            HydrationRecommendations(data.current.temp_c)
        }
    }
}

@Composable
fun WeatherStatsCard(data: WeatherModel) {
    Card {
        Column(modifier = Modifier.fillMaxWidth()) {
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
                WeatherKeyVal("Local Time", data.location.localtime.split(" ")[1])  // Convert to string
                WeatherKeyVal("Local Date", data.location.localtime.split(" ")[0])  // Convert to string
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
        Text(text = value.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}