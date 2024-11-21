package com.example.caronaapp.utils.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.caronaapp.ui.theme.Amarelo
import com.example.caronaapp.ui.theme.AmareloInativo
import com.example.caronaapp.ui.theme.CaronaAppTheme

@Composable
fun ButtonAction(
    label: String,
    isLoading: Boolean = false,
    background: Color = Amarelo,
    backgroundInativo: Color = AmareloInativo,
    labelColor: Color = Color.White,
    handleClick: () -> Unit
) {
    CaronaAppTheme {
        Button(
            onClick = { handleClick() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLoading) backgroundInativo else background,
            ),
            shape = RoundedCornerShape(12.dp),
//            enabled = isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text(
                    text = label,
                    color = labelColor,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun ButtonActionPreview() {
    ButtonAction(label = "Finalizar", isLoading = false) {}
}