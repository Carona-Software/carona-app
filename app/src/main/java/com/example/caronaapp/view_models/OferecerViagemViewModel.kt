package com.example.caronaapp.view_models

import androidx.lifecycle.ViewModel
import com.example.caronaapp.service.repository.ViagemRepository

class OferecerViagemViewModel(
    val viagemRepository: ViagemRepository
) : ViewModel() {
}