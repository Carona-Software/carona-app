package com.example.caronaapp.data.dto.viagem

import com.example.caronaapp.data.enums.StatusViagem
import java.time.LocalDate
import java.time.LocalTime

data class ViagemSimplesListagemDto(
    val id: Int,
    val data: String,
    val horarioPartida: String,
    val horarioChegada: String,
    val preco: Double,
    val status: StatusViagem
) {
    // Propriedade derivada para converter para LocalDate
    val dataInDate: LocalDate
        get() = data.let { LocalDate.parse(it) }

    val horarioPartidaInTime: LocalTime
        get() = horarioPartida.let { LocalTime.parse(it) }

    val horarioChegadaInTime: LocalTime
        get() = horarioChegada.let { LocalTime.parse(it) }
}
