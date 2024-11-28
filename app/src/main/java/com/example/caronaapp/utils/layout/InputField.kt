package com.example.caronaapp.utils.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.caronaapp.ui.theme.Azul
import com.example.caronaapp.ui.theme.CaronaAppTheme
import com.example.caronaapp.ui.theme.Cinza90
import com.example.caronaapp.ui.theme.VermelhoErro

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    maxLines: Int = 1,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    buttonIconEnabled: Boolean = true,
    iconDescription: String? = null,
    onIconClick: (() -> Unit)? = null,
    handleChange: (String) -> Unit = {}
) {
    CaronaAppTheme {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = Azul,
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .align(Alignment.CenterHorizontally)
                    .border(
                        BorderStroke(2.dp, if (isError) VermelhoErro else Azul),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (startIcon != null) {
                    IconButton(
                        enabled = buttonIconEnabled,
                        onClick = {
                        if (onIconClick != null) {
                            onIconClick()
                        }
                    }) {
                        Icon(
                            imageVector = startIcon,
                            contentDescription = iconDescription,
                            Modifier.size(28.dp),
                            tint = Azul
                        )
                    }
                }
                TextField(
                    value = value,
                    onValueChange = { handleChange(it) },
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(if (endIcon != null) 0.9f else 1f)
                        .border(
                            BorderStroke(0.dp, Color.Transparent),
                            shape = RoundedCornerShape(12.dp)
                        ).then(modifier),
                    textStyle = MaterialTheme.typography.headlineMedium,
                    placeholder = {
                        Text(
                            text = label,
                            color = Cinza90,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
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
                    maxLines = maxLines,
                    keyboardOptions = keyboardOptions,
                    visualTransformation = visualTransformation,
                    enabled = enabled,
                    singleLine = singleLine
                )
                if (endIcon != null) {
                    IconButton(onClick = {
                        if (onIconClick != null) {
                            onIconClick()
                        }
                    }) {

                        Icon(
                            imageVector = endIcon,
                            contentDescription = iconDescription,
                            modifier = Modifier
                                .size(28.dp),
                            tint = Azul
                        )
                    }
                }
            }

            if (isError) {
                Spacer(modifier = Modifier.height(4.dp))
                if (supportingText != null) {
                    Text(
                        text = supportingText,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }

    }
}