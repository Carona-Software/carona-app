package com.example.caronaapp.masks

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Remove tudo que não é número
        val digits = text.text.filter { it.isDigit() }

        // Constrói a data no formato dd/MM/yyyy
        val result = StringBuilder()
        for (i in digits.indices) {
            result.append(digits[i])
            if (i == 1 || i == 3) {
                result.append('/')
            }
        }

        val outputText = result.toString()
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset <= 2) offset
                else if (offset <= 4) offset + 1
                else offset + 2
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset <= 2) offset
                else if (offset <= 5) offset - 1
                else offset - 2
            }
        }

        return TransformedText(AnnotatedString(outputText), offsetMapping)
    }
}