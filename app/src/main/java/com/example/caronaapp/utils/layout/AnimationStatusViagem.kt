package com.example.caronaapp.utils.layout

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.caronaapp.data.enums.StatusViagem
import com.example.caronaapp.ui.theme.Circulo
import com.example.caronaapp.ui.theme.VerdeBackgroundCirclePendente
import com.example.caronaapp.ui.theme.VerdeCirclePendente
import com.example.caronaapp.ui.theme.VermelhoBackgroundCircleAndamento
import com.example.caronaapp.ui.theme.VermelhoCircleAndamento

@Composable
fun AnimationStatusViagem(statusViagem: StatusViagem) {
    val infiniteTransition = rememberInfiniteTransition(label = "transition-efeito-pulsar")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.3f, // 30% da escala inicial
        targetValue = 1.0f,  // 100% da escala final
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse // Anima de volta
        ), label = "pulsar"
    )

    Box(
        modifier = Modifier
            .size(28.dp) // Tamanho base do círculo
        ,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer(scaleX = scale, scaleY = scale)
                .background(
                    if (statusViagem == StatusViagem.PENDENTE) VerdeBackgroundCirclePendente
                    else VermelhoBackgroundCircleAndamento,
                    shape = CircleShape
                )
        )
        Icon(
            imageVector = Circulo,
            contentDescription = null,
            tint = if (statusViagem == StatusViagem.PENDENTE) VerdeCirclePendente
            else VermelhoCircleAndamento,
            modifier = Modifier.size(16.dp)
        )
    }
}