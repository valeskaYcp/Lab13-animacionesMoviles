package com.example.animaciones

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.with


enum class AppState {
    CARGANDO,
    CONTENIDO,
    ERROR
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ToggleVisibilityButton() {
    var isVisible by remember { mutableStateOf(false) }
    var isBlue by remember { mutableStateOf(true) }
    var size by remember { mutableStateOf(100.dp) }
    var offset by remember { mutableStateOf(0.dp) }
    var appState by remember { mutableStateOf(AppState.CARGANDO) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Botones para alternar visibilidad y color
        Button(onClick = { isVisible = !isVisible }) {
            Text(text = if (isVisible) "Ocultar Cuadro" else "Mostrar Cuadro")
        }
        Button(onClick = {
            isBlue = !isBlue
            size = if (size == 100.dp) 250.dp else 100.dp
            offset = if (offset == 0.dp) 100.dp else 0.dp
        }) {
            Text(text = if (isBlue) "Cambiar a Verde" else "Cambiar a Azul")
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            val color by animateColorAsState(
                targetValue = if (isBlue) Color.Blue else Color.Green,
                animationSpec = tween(durationMillis = 500)
            )
            val animatedSize by animateDpAsState(targetValue = size, animationSpec = tween(durationMillis = 500))
            val animatedOffset by animateDpAsState(targetValue = offset, animationSpec = tween(durationMillis = 500))

            Box(
                modifier = Modifier
                    .offset(x = animatedOffset, y = animatedOffset)
                    .size(animatedSize)
                    .background(color)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Contenido con estados
        AnimatedContent(
            targetState = appState,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 500)) with fadeOut(animationSpec = tween(durationMillis = 500))
            }
        ) { state ->
            when (state) {
                AppState.CARGANDO -> {
                    Text("Cargando...", color = Color.Blue)
                }
                AppState.CONTENIDO -> {
                    Text("Contenido cargado correctamente!", color = Color.Green)
                }
                AppState.ERROR -> {
                    Text("Ocurrió un error. Intenta de nuevo.", color = Color.Red)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { appState = AppState.CARGANDO }) {
                Text("Cargar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { appState = AppState.CONTENIDO }) {
                Text("Mostrar Contenido")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { appState = AppState.ERROR }) {
                Text("Mostrar Error")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewToggleVisibilityButton() {
    ToggleVisibilityButton()
}
