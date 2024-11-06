package com.example.caronaapp.presentation.view_models

import androidx.lifecycle.ViewModel
import com.example.caronaapp.service.api.SolicitacaoFidelizacaoApi

class SolicitacaoFidelizacaoViewModel(
    val repository: SolicitacaoFidelizacaoApi
): ViewModel() {
}