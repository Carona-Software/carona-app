package com.example.caronaapp.utils.masks

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CepVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val cep = text.text.mapIndexed { i, c ->  // i -> index | c -> character

            // se o indice atual for 4 adiciona o "-", caso contrário retorna o caractere
            when (i) {
                4 -> "$c-"
                else -> c
            }
        }.joinToString(separator = "")

        // OffsetMapping para traduzir a posição do cursor corretamente
        val cepOffSetMap = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset > 4 -> offset + 1  // Após o quarto caractere, move um passo adiante
                    else -> offset
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset > 4 -> offset - 1  // Ajusta o cursor ao remover o traço "-"
                    else -> offset
                }
            }
        }

        // Retorna o valor transformado e o deslocamento personalizado
        return TransformedText(AnnotatedString(cep), cepOffSetMap)
    }
}