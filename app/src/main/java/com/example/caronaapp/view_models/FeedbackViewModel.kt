package com.example.caronaapp.view_models

import androidx.lifecycle.ViewModel
import com.example.caronaapp.service.repository.FeedbackRepository

class FeedbackViewModel(
    val repository: FeedbackRepository
): ViewModel() {

}