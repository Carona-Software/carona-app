package com.example.caronaapp.utils.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    shadowColor: Color = Color.Black,
    shadowElevation: Float = 24f, // Valor da elevação
    shadowOffsetY: Float = 16f, // Offset da sombra no eixo Y (sombra para cima)
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .shadow(
                elevation = shadowElevation.dp,
                shape = shape,
                ambientColor = shadowColor,
                spotColor = shadowColor,
                clip = false
            )
            .graphicsLayer() {
                translationY = shadowOffsetY // Deslocando a sombra para cima
            }
            .background(color = Color.White, shape = shape)
            .padding(16.dp)
    ) {
        content()
    }
}