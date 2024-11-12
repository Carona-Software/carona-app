package com.example.caronaapp.utils.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.caronaapp.R
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CinzaSwitchButton
import com.example.caronaapp.ui.theme.VerdeSwitchButton

@Composable
fun ApenasMulheresSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.apenas_mulheres),
            style = MaterialTheme.typography.labelLarge,
            color = Azul,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.White,
                checkedTrackColor = VerdeSwitchButton,
                uncheckedTrackColor = CinzaSwitchButton,
                checkedBorderColor = VerdeSwitchButton,
                uncheckedBorderColor = CinzaSwitchButton
            )
        )
    }
}