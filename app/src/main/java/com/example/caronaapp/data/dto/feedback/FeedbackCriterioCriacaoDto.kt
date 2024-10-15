package com.example.caronaapp.data.dto.feedback

import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioCriacaoDto
import java.time.LocalDate

data class FeedbackCriterioCriacaoDto(
    val remetenteId: Int,
    val destinatarioId: Int,
    val comentario: String,
    val data: LocalDate,
    val viagemId: Int,
    val notasCriterios: List<NotaCriterioCriacaoDto>
)
