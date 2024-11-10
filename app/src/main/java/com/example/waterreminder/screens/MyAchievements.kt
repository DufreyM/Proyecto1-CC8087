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
fun MyAchievementsScreen() {
    val achievements = listOf(
        "PRIMER SORBO" to "POR REGISTRAR TU PRIMER VASO DE AGUA.",
        "FUENTE AMBULANTE" to "TOMA AGUA DURANTE 3 DÍAS SEGUIDOS.",
        "AGUA ZEN" to "MEDITA DESPUÉS DE CADA RECORDATORIO DURANTE 3 DÍAS.",
        "FUERZA ACUÁTICA" to "BEBE 3 LITROS DE AGUA EN UN DÍA.",
        "CASCADA SALUDABLE" to "BEBE 2 LITROS DE AGUA EN UN SOLO DÍA.",
        "TSUNAMI HÍDRICO" to "BEBE 3 LITROS DE AGUA EN UN DÍA.",
        "RAYO H2O" to "REGISTRA TU AGUA AL MINUTO EXACTO DEL RECORDATORIO.",
        "GOTA A GOTA" to "TOMA AGUA CADA HORA POR 12 HORAS SEGUIDAS.",
        "AGUA NINJA" to "CUMPLE TUS METAS DE HIDRATACIÓN SIN QUE TE LO RECUERDEN.",
        "HIDRATACIÓN PERFECTA" to "POR ALCANZAR EL OBJETIVO DIARIO DE AGUA POR 7 DÍAS CONSECUTIVOS.",
        "HÉROE HIDRATADO" to "SUPERA UNA SEMANA DE MUCHO CALOR SIN OLVIDAR TUS RECORDATORIOS.",
        "ALQUIMISTA LÍQUIDO" to "EXPERIMENTA CON DIFERENTES BEBIDAS DURANTE UNA SEMANA.",
        "CIENTÍFICO DEL AGUA" to "APRENDE 10 CURIOSIDADES SOBRE EL AGUA DESDE LA APP.",
        "GLACIAR HUMANO" to "TOMA SOLO AGUA FRÍA POR 5 DÍAS SEGUIDOS.",
        "AGUA MENTE MAESTRA" to "COMPLETA TODOS LOS RECORDATORIOS DEL DÍA SIN FALLAR.",
        "EVOLUCIÓN DE LA GOTA" to "MEJORA TU PROMEDIO DE HIDRATACIÓN DURANTE 30 DÍAS.",
        "A TODA FUENTE" to "COMPLETA 30 DÍAS SEGUIDOS TOMANDO AGUA CORRECTAMENTE.",
        "OASIS PERSONAL" to "COMPLETA 30 DÍAS SEGUIDOS TOMANDO AGUA CORRECTAMENTE.",
        "CAMPEÓN HIDRATADO" to "TOMA 10 LITROS DE AGUA EN TOTAL DURANTE UNA SEMANA.",
        "MAESTRO DEL AGUA" to "COMPLETA TODAS LAS METAS DIARIAS DE HIDRATACIÓN POR 60 DÍAS SEGUIDOS.",
        "REY DEL AGUA" to "CONSIGUE TODOS LOS LOGROS RELACIONADOS CON LA HIDRATACIÓN.",
        "DETECTOR DE SEQUÍA" to "ACTIVA UNA ALERTA DESPUÉS DE 4 HORAS SIN BEBER AGUA."

    )
    MyAchievements(achievements = achievements)
}
