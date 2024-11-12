package com.example.caronaapp.data.dto.usuario

import com.example.caronaapp.data.dto.endereco.EnderecoCriacaoDto

data class UsuarioCriacaoDto(
    var nome: String = "",
    var email: String = "",
    var senha: String = "",
    var cpf: String = "",
    var perfil: String = "",
    var genero: String = "",
    var dataNascimento: String = "",
    var fotoUrl: String = "",
    var endereco: EnderecoCriacaoDto = EnderecoCriacaoDto()
)
