package com.example.caronaapp.presentation.screens.onboarding.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingButton(
    containerColor: Color,
    textColor: Color,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.displayLarge
        )
    }
}