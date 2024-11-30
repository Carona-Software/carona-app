package com.example.caronaapp.utils.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.caronaapp.data.dto.google_maps.GeocodeResponse
import com.example.caronaapp.ui.theme.Azul

@Composable
fun DropdownEnderecoResult(
    inputLabel: String,
    inputValue: String,
    startIcon: ImageVector,
    onChangeEvent: (String) -> Unit,
    isDropdownExpanded: Boolean,
    results: List<GeocodeResponse.Result>,
    onDismissDropdown: () -> Unit,
    onDropdownItemClick: (GeocodeResponse.Result) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        InputField(
            label = inputLabel,
            value = inputValue,
            startIcon = startIcon,
            buttonIconEnabled = false
        ) {
            onChangeEvent(it)
        }
        Spacer(modifier = Modifier.height(2.dp))

        if (results.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { onDismissDropdown() },
                    modifier = Modifier.background(Color.White)
                ) {
                    results.forEach { result ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = result.formatted_address,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Azul,
                                    textAlign = TextAlign.Start
                                )
                            },
                            onClick = { onDropdownItemClick(result) }
                        )
                    }
                }
            }
        }
    }
}