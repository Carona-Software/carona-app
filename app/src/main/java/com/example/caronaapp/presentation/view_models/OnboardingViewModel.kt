package com.example.caronaapp.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.di.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val isOnboardingDone = MutableStateFlow(false)

    fun setOnboardingDone() {
        viewModelScope.launch {
            dataStoreManager.setOnboardingDone()
            isOnboardingDone.update { true }
        }
    }
}