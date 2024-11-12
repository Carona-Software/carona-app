package com.example.caronaapp.utils.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.Cinza90

@Composable
fun PrecoFieldComponent(
    label: String,
    value: String,
    handleOnChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Azul,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .width(140.dp)
                .height(52.dp)
                .border(
                    border = BorderStroke(2.dp, Azul),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.cifrao_rs),
                style = MaterialTheme.typography.labelMedium,
                color = Azul,
            )
            TextField(
                value = value,
                onValueChange = { handleOnChange(it) },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Azul,
                    unfocusedTextColor = Azul,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledPlaceholderColor = Cinza90,
                    disabledTextColor = Azul
                ),
                textStyle = MaterialTheme.typography.headlineMedium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}