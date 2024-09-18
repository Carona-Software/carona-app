package com.example.caronaapp.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.CaronaAppTheme

@Composable
fun ButtonAction(label: String, handleClick: () -> Unit) {
    CaronaAppTheme {
        Button(
            onClick = { handleClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Amarelo,
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = label,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}