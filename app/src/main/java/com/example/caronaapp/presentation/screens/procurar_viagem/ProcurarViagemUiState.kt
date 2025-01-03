package com.example.caronaapp.presentation.screens.procurar_viagem

import com.example.caronaapp.data.dto.google_maps.GeocodeResponse
import java.time.LocalDate

sealed class ProcurarViagemField {
    data class PontoPartida(val value: String) : ProcurarViagemField()
    data class PontoChegada(val value: String) : ProcurarViagemField()
    data class Data(val value: LocalDate) : ProcurarViagemField()
}

data class ProcurarViagemState(
    val pontoPartida: String = "",
    val pontoChegada: String = "",
    val data: LocalDate = LocalDate.now(),
    val resultsPontoPartida: List<GeocodeResponse.Result> = emptyList(),
    val resultsPontoChegada: List<GeocodeResponse.Result> = emptyList(),
)