package com.example.caronaapp.data.dto.viagem

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ViagemProcuraDto(
    val pontoPartida: Coordenadas? = null,
    val pontoChegada: Coordenadas? = null,
    val data: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    val precoMinimo: Double? = null,
    val precoMaximo: Double? = null,
    val capacidadePassageiros: Int? = null,
    val apenasMulheres: Boolean? = null
)
