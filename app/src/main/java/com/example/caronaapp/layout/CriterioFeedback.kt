package com.example.caronaapp.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.CinzaD6

@Composable
fun CriterioFeedback(
    label: String,
    notaMedia: Double,
    percentualNotaMedia: Float // quantidade em "porcentagem" para pintar a linha proporcional à nota media
) {
    Column( // critério de feedback
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = label,
                color = Azul,
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.nota_media_criterio_feedback, notaMedia),
                color = Cinza90,
                style = MaterialTheme.typography.displayLarge
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.zero),
                color = Azul,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CinzaD6),
                horizontalArrangement = Arrangement.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(percentualNotaMedia)
                        .height(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Azul)
                ) { // row que preenche a linha com base na média do critério}
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.cinco),
                color = Azul,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}