package com.example.caronaapp.presentation.screens.feedback

sealed class FeedbackField {
    data class Criterio(val id: Int, val value: Double) : FeedbackField()
    data class Comentario(val value: String) : FeedbackField()
}