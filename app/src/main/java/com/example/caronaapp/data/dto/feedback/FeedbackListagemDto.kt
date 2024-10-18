package com.example.caronaapp.data.dto.feedback

import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioListagemDto
import java.time.LocalDate

data class FeedbackListagemDto(
    val data: LocalDate,
    val comentario: String,
    val notaMedia: Double,
    val avaliador: AvaliadorDto,
    val notasCriterios: List<NotaCriterioListagemDto>
) {
    data class AvaliadorDto(
        val id: Int,
        val nome: String,
        val fotoUrl: String,
    )
}
