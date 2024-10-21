package com.example.caronaapp.data.dto.viagem

import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto
import com.example.caronaapp.data.enums.StatusViagem
import java.time.LocalDate
import java.time.LocalTime

data class ViagemDetalhesListagemDto(
    val id: Int,
    val capacidadePassageiros: Int,
    val apenasMulheres: Boolean,
    val data: LocalDate,
    val horarioSaida: LocalTime,
    val horarioChegada: LocalTime,
    val preco: Double,
    val status: StatusViagem,
    val motorista: UsuarioSimplesListagemDto,
    val carro: CarroDto,
    var passageiros: List<UsuarioSimplesListagemDto>?,
    val trajeto: TrajetoDto
) {
    data class CarroDto(
        val cor: String,
        val marca: String,
        val modelo: String,
        val placa: String,
    )
}
