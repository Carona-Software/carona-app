package com.example.caronaapp.data.dto.usuario

import com.example.caronaapp.data.dto.endereco.EnderecoListagemDto
import com.example.caronaapp.data.enums.TipoUsuario
import java.time.LocalDate

data class UsuarioListagemDto(
    val id: Int,
    val nome: String,
    val email: String,
    val senha: String,
    val cpf: String,
    val perfil: TipoUsuario,
    val genero: String,
    val dataNascimento: LocalDate,
    val fotoUrl: String,
    val endereco: EnderecoListagemDto,
)
