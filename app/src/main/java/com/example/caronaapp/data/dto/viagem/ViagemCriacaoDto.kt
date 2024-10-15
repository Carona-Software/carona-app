package com.example.caronaapp.data.dto.viagem

import java.time.LocalDate
import java.time.LocalTime

data class ViagemCriacaoDto(
    val data: LocalDate,
    val hora: LocalTime,
    val preco: Double,
    val apenasMulheres: Boolean,
    val pontoPartida: Coordenadas,
    val pontoDestino: Coordenadas,
    val capacidadePassageiros: Int,
    val carroId: Int,
    val motoristaId: Int
)
