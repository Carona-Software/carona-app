package com.example.caronaapp.data.dto.solicitacao

import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemSimplesListagemDto
import com.example.caronaapp.data.enums.StatusSolicitacao

data class SolicitacaoViagemListagemDto(
    val id: Int,
    val status: StatusSolicitacao,
    val usuario: UsuarioSimplesListagemDto,
    val viagem: ViagemSimplesListagemDto
)
