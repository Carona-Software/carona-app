package com.example.caronaapp.presentation.view_models

import androidx.lifecycle.ViewModel
import com.example.caronaapp.service.api.SolicitacaoViagemApi

class SolicitacaoViagemViewModel(
    val repository: SolicitacaoViagemApi
): ViewModel() {
}