package com.example.animaciones

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AnimacionCombinada() {
    var isDarkMode by remember { mutableStateOf(false) }
    var isButtonVisible by remember { mutableStateOf(true) }
    var size by remember { mutableStateOf(100.dp) }
    var color by remember { mutableStateOf(Color.Blue) }

    val backgroundColor by animateColorAsState(
        targetValue = if (isDarkMode) Color.Black else Color.White,
        animationSpec = tween(durationMillis = 500)
    )

    val animatedSize by animateDpAsState(
        targetValue = size,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Elemento que cambia de tamaño y color al hacer clic
            Box(
                modifier = Modifier
                    .size(animatedSize)
                    .background(color)
                    .clickable {
                        size = if (size.value == 100f) 200.dp else 100.dp
                        color = if (color == Color.Blue) Color.Green else Color.Blue
                    }
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Botón que se desplaza y desaparece
            AnimatedVisibility(
                visible = isButtonVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = slideOutHorizontally(animationSpec = tween(durationMillis = 300))
            ) {
                Button(onClick = {
                    isButtonVisible = false
                }) {
                    Text("Desaparecer")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón para alternar entre modo claro y oscuro
            Button(onClick = { isDarkMode = !isDarkMode }) {
                Text("Cambiar a ${if (isDarkMode) "Modo Claro" else "Modo Oscuro"}")
            }
        }
    }
}
