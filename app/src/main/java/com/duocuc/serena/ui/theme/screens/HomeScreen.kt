package com.duocuc.serena.ui.theme.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duocuc.serena.R


@Composable
fun HomeScreen() {

    val infiniteTransition = rememberInfiniteTransition()

    val scale1 by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    val scale2 by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing, delayMillis = 200),
            RepeatMode.Reverse
        )
    )

    val scale3 by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing, delayMillis = 400),
            RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Serena",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tu compañera de bienestar emocional",
                color = Color.Gray,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Puntitos animados
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .scale(scale1)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .scale(scale2)
                        .background(MaterialTheme.colorScheme.secondary, CircleShape)
                )
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .scale(scale3)
                        .background(MaterialTheme.colorScheme.tertiary, CircleShape)
                )
            }
        }
    }
}


