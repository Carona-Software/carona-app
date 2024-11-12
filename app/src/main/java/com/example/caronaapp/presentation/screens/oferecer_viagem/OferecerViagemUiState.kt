package com.example.caronaapp.presentation.screens.oferecer_viagem

import com.example.caronaapp.data.dto.carro.CarroListagemDto
import com.example.caronaapp.data.dto.google_maps.GeocodeResponse
import java.time.LocalDate
import java.time.LocalTime

sealed class OferecerViagemField {
    data class PontoPartida(val value: String) : OferecerViagemField()
    data class PontoDestino(val value: String) : OferecerViagemField()
    data class Data(val value: LocalDate) : OferecerViagemField()
    data class HorarioPartida(val value: LocalTime) : OferecerViagemField()
    data class CapacidadePassageiros(val value: Int) : OferecerViagemField()
    data class Preco(val value: Double) : OferecerViagemField()
    data class Carro(val value: CarroListagemDto) : OferecerViagemField()
    data class ApenasMulheres(val value: Boolean) : OferecerViagemField()
}

sealed class OferecerViagemEnderecoField {
    data class PontoPartida(val value: GeocodeResponse.Result) : OferecerViagemEnderecoField()
    data class PontoDestino(val value: GeocodeResponse.Result) : OferecerViagemEnderecoField()
}

data class OferecerViagemState(
    val pontoPartida: String = "",
    val pontoDestino: String = "",
    val data: String = "",
    val hora: String = "",
    val capacidadePassageiros: Int = 1,
    val apenasMulheres: Boolean = false,
    val preco: Double = 0.0,
    val carro: String = "",
    val resultsPontoPartida: List<GeocodeResponse.Result> = emptyList(),
    val resultsPontoDestino: List<GeocodeResponse.Result> = emptyList(),
    val isPartidaDropdownExpanded: Boolean = false,
    val isDestinoDropdownExpanded: Boolean = false,
    val isCarrosDropdownExpanded: Boolean = false
)