package com.example.caronaapp.data.dto.viagem

import com.example.caronaapp.data.enums.StatusViagem
import java.time.LocalDate
import java.time.LocalTime

data class ViagemSimplesListagemDto(
    val id: Int,
    val data: LocalDate,
    val hora: LocalTime,
    val preco: Double,
    val status: StatusViagem
)
