package com.example.caronaapp.presentation.screens.detalhes_viagem

import com.example.caronaapp.data.dto.endereco.EnderecoListagemDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemDetalhesListagemDto

data class DetalhesViagemUiState(
    val viagem: ViagemDetalhesListagemDto? = null,
    val enderecoUsuario: EnderecoListagemDto? = null,
    val isViagemDeleted: Boolean = false,
    val isCancelViagemModalOpened: Boolean = false,
    val isCancelReservaModalOpened: Boolean = false,
    val isCancelSolicitacaoModalOpened: Boolean = false,
    val isIniciarViagemModalOpened: Boolean = false,
    val isFinalizarViagemModalOpened: Boolean = false,
    val motoristaFoiAvaliado: Boolean = false,
    val isSuccess: Boolean = false,
    val successMessage: String = "",
    val isError: Boolean = false,
    val errorMessage: String = "",
    val solicitacaoToDelete: SolicitacaoViagemListagemDto? = null
)