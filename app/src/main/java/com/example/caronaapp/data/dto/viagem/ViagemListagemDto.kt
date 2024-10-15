package com.example.caronaapp.data.dto.viagem

import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto
import com.example.caronaapp.data.enums.StatusViagem
import java.time.LocalDate
import java.time.LocalTime

data class ViagemListagemDto(
    val id: Int,
    val capacidadePassageiros: Int,
    val apenasMulheres: Boolean,
    val data: LocalDate,
    val horarioSaida: LocalTime,
    val horarioChegada: LocalTime,
    val preco: Double,
    val statusViagem: StatusViagem,
    val motorista: UsuarioSimplesListagemDto,
    val trajeto: TrajetoDto
)
