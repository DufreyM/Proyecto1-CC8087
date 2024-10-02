package com.example.waterreminder.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waterreminder.R

@Composable
fun Achievement(name: String, description: String) {
    var expanded by remember { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 24.dp else 0.dp,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Surface(
        color = Color(0xFF64B5F6),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_waterdroplet),
                    contentDescription = "Water Droplet",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp),
                    tint = Color.White
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    if (expanded) {
                        Spacer(modifier = Modifier.height(extraPadding))
                        Text(
                            text = description,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (expanded) R.drawable.ic_drop_up else R.drawable.ic_drop_down
                        ),
                        contentDescription = if (expanded) "Mostrar menos" else "Mostrar más",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MyAchievements(

    achievements: List<Pair<String, String>>
) {
    Surface(
        color = Color(0xFFE6F4F5),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Mis Logros",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                color = Color(0xFF64B5F6)
            )
            LazyColumn {
                items(achievements.size) { index ->
                    val (name, description) = achievements[index]
                    Achievement(name = name, description = description)
                }
            }
        }
    }
}

@Composable
fun MyAchievementsScreen(onAchievementsSelected: (Int) -> Unit) {
    val achievements = listOf(
        "PRIMER SORBO" to "POR REGISTRAR TU PRIMER VASO DE AGUA.",
        "HIDRATACIÓN PERFECTA" to "POR ALCANZAR EL OBJETIVO DIARIO DE AGUA POR 7 DÍAS CONSECUTIVOS.",
        "CASCADA SALUDABLE" to "",
        "A TODA FUENTE" to "",
        "FUERZA ACUÁTICA" to ""
    )
    MyAchievements(achievements = achievements)
}
