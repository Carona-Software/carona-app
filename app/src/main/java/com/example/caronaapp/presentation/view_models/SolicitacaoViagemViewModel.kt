package com.example.caronaapp.presentation.view_models

import androidx.lifecycle.ViewModel
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.service.api.SolicitacaoViagemApi

class SolicitacaoViagemViewModel(
    val repository: SolicitacaoViagemApi,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
}