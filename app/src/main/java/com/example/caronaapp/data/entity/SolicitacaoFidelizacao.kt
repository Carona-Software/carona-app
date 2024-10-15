package com.example.caronaapp.data.entity

import com.example.caronaapp.data.enums.StatusSolicitacao

data class SolicitacaoFidelizacao(
    val id: Int,
    val passageiro: Usuario,
    val motorista: Usuario,
    val status: StatusSolicitacao
)
