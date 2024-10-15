package com.example.caronaapp.data.dto.solicitacao

import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto
import com.example.caronaapp.data.enums.StatusSolicitacao

data class SolicitacaoFidelizacaoListagemDto(
    val id: Int,
    val status: StatusSolicitacao,
    val motorista: UsuarioSimplesListagemDto,
    val passageiro: UsuarioSimplesListagemDto
)
