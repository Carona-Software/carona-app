package com.example.caronaapp.utils.masks

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CpfVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val cpf = text.text.mapIndexed { i, c ->  // i -> index | c -> character

            // mapeia quando o indice é um dos indices especificados,
            // senao só retorna o valor inputado
            when (i) {
                2 -> "$c."
                5 -> "$c."
                8 -> "$c-"
                else -> c
            }
        }.joinToString(separator = "")

        // OffsetMapping para traduzir a posição do cursor corretamente
        val cpfOffSetMap = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset > 9 -> offset + 3
                    offset > 5 -> offset + 2
                    offset > 2 -> offset + 1
                    else -> offset
                }

            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset > 9 -> offset - 4
                    offset > 5 -> offset - 2
                    offset > 2 -> offset - 1
                    else -> offset
                }
            }
        }

        // valor, deslocamento (o deslocamento padrão Identity é o mais usado,
        // porém nesse caso é necessário um totalmente custom)
        return TransformedText(AnnotatedString(cpf), cpfOffSetMap)
    }
}