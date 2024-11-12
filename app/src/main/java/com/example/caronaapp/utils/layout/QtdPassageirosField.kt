package com.example.caronaapp.utils.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CinzaCB
import com.example.caronaapp.ui.theme.Mais
import com.example.caronaapp.ui.theme.Menos

@Composable
fun QtdPassageirosField(
    value: String,
    handleAddQnt: () -> Unit,
    handleRemoveQnt: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = stringResource(id = R.string.label_quantidade_passageiros),
            style = MaterialTheme.typography.labelLarge,
            color = Azul,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier
                .width(120.dp)
                .height(52.dp)
                .border(
                    border = BorderStroke(2.dp, Azul),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { handleRemoveQnt() },
                modifier = Modifier.size(24.dp),
                enabled = value.toInt() > 1
            ) {
                Icon(
                    imageVector = Menos,
                    contentDescription = "Menos",
                    tint = if (value.toInt() == 1) CinzaCB else Azul
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                color = Azul,
                modifier = Modifier
                    .width(40.dp)
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = { handleAddQnt() },
                modifier = Modifier.size(24.dp),
                enabled = value.toInt() < 4
            ) {
                Icon(
                    imageVector = Mais,
                    contentDescription = "Mais",
                    tint = if (value.toInt() == 4) CinzaCB else Azul
                )
            }
        }
    }
}