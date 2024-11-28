package com.example.caronaapp.data.dto.viagem

import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemListagemDto
import com.example.caronaapp.data.dto.usuario.PassageiroDto
import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto
import com.example.caronaapp.data.enums.StatusViagem
import java.time.LocalDate
import java.time.LocalTime

data class ViagemDetalhesListagemDto(
    val id: Int,
    val capacidadePassageiros: Int,
    val apenasMulheres: Boolean,
    val data: String,
    val horarioPartida: String,
    val horarioChegada: String,
    val preco: Double,
    val status: StatusViagem,
    val motorista: UsuarioSimplesListagemDto,
    val carro: CarroDto,
    var passageiros: List<PassageiroDto>,
    val trajeto: TrajetoDto,
    val solicitacoes: List<SolicitacaoViagemListagemDto>,
    val distanciaPartida: Double = 0.0,
    val distanciaDestino: Double = 0.0,
) {
    val dataInDate: LocalDate
        get() = data.let { LocalDate.parse(it) }

    val horarioPartidaInTime: LocalTime
        get() = horarioPartida.let { LocalTime.parse(it) }

    val horarioChegadaInTime: LocalTime
        get() = horarioChegada.let { LocalTime.parse(it) }

    data class CarroDto(
        val cor: String,
        val marca: String,
        val modelo: String,
        val placa: String,
    )
}
