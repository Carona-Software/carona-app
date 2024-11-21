package com.example.caronaapp.data.dto.viagem

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ViagemCriacaoDto(
    val data: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    val horarioPartida: String = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
    val horarioChegada: String = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
    val preco: Double = 0.0,
    val apenasMulheres: Boolean = false,
    val pontoPartida: Coordenadas = Coordenadas(0.0, 0.0),
    val pontoDestino: Coordenadas = Coordenadas(0.0, 0.0),
    val capacidadePassageiros: Int = 1,
    val carroId: Int = 0,
    val motoristaId: Int = 0
)
