package com.example.caronaapp.presentation.view_models

import androidx.lifecycle.ViewModel
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.service.api.ViagemApi

class OferecerViagemViewModel(
    val viagemApi: ViagemApi,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
}