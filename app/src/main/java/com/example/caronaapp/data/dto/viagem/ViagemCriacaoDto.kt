package com.example.caronaapp.data.dto.viagem

import com.example.caronaapp.utils.formatTime
import java.time.LocalTime

data class ViagemCriacaoDto(
    val data: String = "",
    val horarioPartida: String = formatTime(LocalTime.now()),
    val horarioChegada: String = formatTime(LocalTime.now()),
    val preco: Double = 0.0,
    val apenasMulheres: Boolean = false,
    val pontoPartida: Coordenadas = Coordenadas(0.0, 0.0),
    val pontoDestino: Coordenadas = Coordenadas(0.0, 0.0),
    val capacidadePassageiros: Int = 1,
    val carroId: Int = 0,
    val motoristaId: Int = 0
)
