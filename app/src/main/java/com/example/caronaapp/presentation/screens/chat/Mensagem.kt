package com.example.caronaapp.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.AzulMensagem
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaF5

@Composable
fun Mensagem(
    modifier: Modifier,
    mensagem: String,
    horario: String,
    enviada: Boolean // true: enviada por mim || false: enviada pelo outro (recebida)
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (enviada) Alignment.End else Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 260.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomEnd = if (enviada) 0.dp else 20.dp,
                        bottomStart = if (enviada) 20.dp else 0.dp
                    )
                )
                .background(if (enviada) AzulMensagem else CinzaF5)
                .padding(12.dp)
        ) { // mensagem
            Text(
                text = mensagem,
                color = Azul,
                style = MaterialTheme.typography.labelMedium,
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = horario,
            color = Cinza90,
            style = MaterialTheme.typography.bodySmall,
        )
    }

}