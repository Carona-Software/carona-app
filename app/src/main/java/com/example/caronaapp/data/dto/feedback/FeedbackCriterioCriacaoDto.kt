package com.example.caronaapp.data.dto.feedback

import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioCriacaoDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class FeedbackCriterioCriacaoDto(
    val remetenteId: Int = 0,
    val destinatarioId: Int = 0,
    val comentario: String = "",
    val data: String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
    val viagemId: Int = 0,
    val notasCriterios: List<NotaCriterioCriacaoDto>? = null
)
