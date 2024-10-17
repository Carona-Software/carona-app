package com.example.caronaapp.view_models

import androidx.lifecycle.ViewModel
import com.example.caronaapp.service.repository.FidelizacaoRepository

class FidelizadosViewModel(
    val repository: FidelizacaoRepository
) : ViewModel() {
}