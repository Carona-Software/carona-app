package com.example.caronaapp.view_models

import androidx.lifecycle.ViewModel
import com.example.caronaapp.service.repository.ViagemRepository

class ProcurarViagemViewModel(
    val viagemRepository: ViagemRepository
) : ViewModel() {

}