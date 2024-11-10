package com.example.waterreminder.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import com.example.waterreminder.R

@Composable
fun StatsScreen(
    waterDayProgress: Float,
    waterMonthProgress: Float,
    activityDayProgress: Float,
    activityMonthProgress: Float
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDCEFFF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Estadísticas",//Titulo
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFF007ACC)
        )

        Spacer(modifier = Modifier.height(40.dp))

        ProgressSection(
            title = "AGUA",
            dayProgress = waterDayProgress,
            monthProgress = waterMonthProgress,
            iconRes = R.drawable.ic_water
        )

        Spacer(modifier = Modifier.height(48.dp))

        ProgressSection(
            title = "ACTIVIDAD FÍSICA",
            dayProgress = activityDayProgress,
            monthProgress = activityMonthProgress,
            iconRes = R.drawable.ic_activity
        )
    }
}

@Composable
fun ProgressSection(
    title: String,
    dayProgress: Float,
    monthProgress: Float,
    iconRes: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF007ACC),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressCircle(
                progress = dayProgress,
                label = "Meta del día"
            )

            ProgressCircle(
                progress = monthProgress,
                label = "Meta del mes"
            )
        }

        Box(
            modifier = Modifier
                .size(120.dp)
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(100.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun ProgressCircle(
    progress: Float,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(80.dp),
                color = Color(0xFF007ACC),
                strokeWidth = 8.dp,
            )
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF007ACC)
            )
        }

        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF007ACC),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
