package com.example.caronaapp.view_models

import androidx.lifecycle.ViewModel
import com.example.caronaapp.service.repository.SolicitacaoViagemRepository

class SolicitacaoViagemViewModel(
    val repository: SolicitacaoViagemRepository
): ViewModel() {
}