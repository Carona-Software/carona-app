package com.example.caronaapp.data.dto.fidelizacao

import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto

data class FidelizacaoListagemDto(
    val passageiro: UsuarioSimplesListagemDto,
    val motorista: UsuarioSimplesListagemDto,
    val qtdViagensJuntos: Int
)
