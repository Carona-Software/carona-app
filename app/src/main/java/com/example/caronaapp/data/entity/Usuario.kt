package com.example.caronaapp.data.entity

import com.example.caronaapp.data.enums.TipoUsuario
import java.time.LocalDate

data class Usuario(
    val id: Int,
    val nome: String,
    val email: String,
    val cpf: String,
    val senha: String,
    val perfil: TipoUsuario,
    val genero: String,
    val dataNascimento: LocalDate,
    val fotoUrl: String,
    val endereco: Endereco,
    val codigoRecuperacaoSenha: String
)
