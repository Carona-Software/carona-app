package com.example.caronaapp.presentation.screens.perfil_outro_usuario

import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.presentation.screens.meu_perfil.AvaliacoesCriterioUser

data class PerfilOutroUsuarioUiState (
    val isLoadingScreen: Boolean = true,
    val perfilUser: String = "",
    val currentFirebaseUser: String = "",
    val userData: UsuarioDetalhesListagemDto? = null,
    val isSuccessful: Boolean = false,
    val isError: Boolean = false,
    val messageToDisplay: String = "",
    val isFotoValida: Boolean = false,
    val isPassageiroFidelizado: Boolean = false,
    val totalViagensJuntos: Int = 0,
    val avaliacoesCriterioUser: AvaliacoesCriterioUser = AvaliacoesCriterioUser(),
)