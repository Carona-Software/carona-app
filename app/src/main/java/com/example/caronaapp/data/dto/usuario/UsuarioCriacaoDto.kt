package com.example.caronaapp.data.dto.usuario

import com.example.caronaapp.data.dto.endereco.EnderecoCriacaoDto
import java.time.LocalDate

data class UsuarioCriacaoDto(
    var nome: String = "",
    var email: String = "",
    var senha: String = "",
    var cpf: String = "",
    var perfil: String = "",
    var genero: String = "",
//    var dataNascimento: LocalDate = LocalDate.now(),
    var dataNascimento: String = "",
    var fotoUrl: String = "",
    var endereco: EnderecoCriacaoDto? = null
)
