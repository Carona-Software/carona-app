package com.example.caronaapp.data.dto.usuario

import com.example.caronaapp.data.dto.endereco.EnderecoListagemDto
import com.example.caronaapp.data.dto.endereco.PrincipalTrajeto
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemSimplesListagemDto
import java.time.LocalDate

data class UsuarioDetalhesListagemDto(
    val id: Int,
    val nome: String,
    val email: String,
    val cpf: String,
    val perfil: String,
    val genero: String,
    val dataNascimento: LocalDate,
    val fotoUrl: String,
    val notaMedia: Double,
    val viagensRealizadas: Int,
    val endereco: EnderecoListagemDto,
    val avaliacoes: List<FeedbackListagemDto>,
    val viagens: List<ViagemSimplesListagemDto>,
    val fidelizados: List<FidelizadoListagemDto>,
    val carros: List<CarroDto>?,
    val principalTrajeto: PrincipalTrajeto
) {
    data class CarroDto(
        val cor: String,
        val marca: String,
        val modelo: String,
        val placa: String,
    )
}
