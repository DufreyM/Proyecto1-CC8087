package com.example.waterreminder.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waterreminder.R

@Composable
fun DrinksScreen(onDrinkSelected: (Int) -> Unit) {
    // Lista de bebidas con sus propiedades
    val drinks = listOf(
        Drink("Agua", "100 mL", R.drawable.water_icon, 100),
        Drink("Soda", "500 mL", R.drawable.soda_icon, 500),
        Drink("Café", "250 mL", R.drawable.coffee_icon, 250),
        Drink("Agua", "250 mL", R.drawable.water_icon, 250),
        Drink("Agua", "500 mL", R.drawable.water_icon, 500),
        Drink("Agua", "100 mL", R.drawable.water_icon, 100),
        Drink("Jugo de Naranja", "200 mL", R.drawable.orange_juice_icon, 200),
        Drink("Café", "250 mL", R.drawable.coffee_icon, 250)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD8E7F3))
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(drinks.size) { index ->
                DrinkCard(
                    drink = drinks[index],
                    onDrinkClick = {
                        onDrinkSelected(drinks[index].volumeInMl)  // Seleccionar el volumen
                    }
                )
            }
        }
    }
}

@Composable
fun DrinkCard(drink: Drink, onDrinkClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { onDrinkClick() } // Detecta clic en la tarjeta de bebida
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = drink.icon),
            contentDescription = drink.name,
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = drink.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = drink.volume,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

// Modificar la clase de datos para incluir el volumen en ml como un Int
data class Drink(val name: String, val volume: String, val icon: Int, val volumeInMl: Int)
