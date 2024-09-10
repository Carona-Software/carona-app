package com.example.caronaapp.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    label: String,
    value: String,
    maxLines: Int,
    handleChange: (value: String) -> Unit
) {
    CaronaAppTheme {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = Azul,
            )
            Spacer(modifier = Modifier.height(6.dp))
            TextField(
                value = value,
                onValueChange = { handleChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(
                        BorderStroke(2.dp, Azul),
                        shape = RoundedCornerShape(12.dp)
                    ),
                textStyle = MaterialTheme.typography.headlineMedium,
                placeholder = { Text(text = label, style = MaterialTheme.typography.headlineMedium) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = maxLines,
            )
        }
    }
}