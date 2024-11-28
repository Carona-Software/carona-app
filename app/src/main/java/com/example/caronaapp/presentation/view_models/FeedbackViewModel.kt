package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.feedback.FeedbackCriterioCriacaoDto
import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioCriacaoDto
import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.data.entity.CriterioFeedback
import com.example.caronaapp.data.repositories.FeedbackRepositoryImpl
import com.example.caronaapp.data.repositories.UsuarioRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.presentation.screens.feedback.FeedbackField
import com.example.caronaapp.utils.functions.isUrlFotoUserValida
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedbackViewModel(
    private val feedbackRepository: FeedbackRepositoryImpl,
    private val usuarioRepository: UsuarioRepositoryImpl,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val isLoadingScreen = MutableStateFlow(true)
    private val criteriosFeedback = MutableStateFlow<List<CriterioFeedback>?>(null)
    val criteriosFeedbackFiltrados = MutableStateFlow<List<CriterioFeedback>>(emptyList())
    val isFotoValida = MutableStateFlow(true)
    val usuarioAvaliado = MutableStateFlow<UsuarioDetalhesListagemDto?>(null)
    val isSuccessful = MutableStateFlow(false)
    val isError = MutableStateFlow(false)
    val messageToDisplay = MutableStateFlow("")

    val feedbackDto = MutableStateFlow(FeedbackCriterioCriacaoDto())

    fun setupFeedback(viagemId: Int, usuarioId: Int) {
        viewModelScope.launch {
            getUsuarioAvaliado(usuarioId)
            getCriteriosFeedback()
            feedbackDto.update {
                it.copy(
                    viagemId = viagemId,
                    destinatarioId = usuarioId,
                    remetenteId = dataStoreManager.getIdUser()!!
                )
            }
            isLoadingScreen.update { false }
        }
    }

    private suspend fun getCriteriosFeedback() {
        try {
            val response = feedbackRepository.findAllCriterios()

            if (response.isSuccessful) {
                if (response.code() == 204) {
                    Log.e(
                        "criteriosFeedback",
                        "Não há critérios de Feedback cadastrados!"
                    )
                } else {
                    criteriosFeedback.update { response.body() }

                    if (usuarioAvaliado.value != null && usuarioAvaliado.value!!.perfil.uppercase() == "MOTORISTA") {
                        criteriosFeedbackFiltrados.update {
                            criteriosFeedback.value!!.filter { it.nome.uppercase() != "COMPORTAMENTO" }
                        }
                    } else {
                        criteriosFeedbackFiltrados.update {
                            criteriosFeedback.value!!.filter { it.nome.uppercase() != "DIRIGIBILIDADE" }
                        }
                    }

                    feedbackDto.update {
                        it.copy(notasCriterios = criteriosFeedbackFiltrados.value.map { criterio ->
                            NotaCriterioCriacaoDto(
                                criterioId = criterio.id,
                                nota = 0.0
                            )
                        })
                    }
                }
            } else {
                Log.e(
                    "criteriosFeedback",
                    "erro ao consultar critérios de Feedback: ${response.errorBody()}"
                )
            }
        } catch (e: Exception) {
            Log.e(
                "criteriosFeedback",
                "Exception -> erro ao consultar critérios de Feedback: ${e.printStackTrace()}"
            )
        }
    }

    private suspend fun getUsuarioAvaliado(usuarioId: Int) {
        try {
            val response = usuarioRepository.findById(usuarioId)

            if (response.isSuccessful) {
                usuarioAvaliado.update { response.body() }
                feedbackDto.update { it.copy(destinatarioId = response.body()!!.id) }
                validarFotoUsuario(response.body()!!.fotoUrl)
            } else {
                Log.e(
                    "feedback",
                    "Erro ao consultar usuário avaliado: ${response.errorBody()}"
                )
            }
        } catch (e: Exception) {
            Log.e(
                "feedback",
                "Exception -> erro ao consultar usuário avaliado: ${e.printStackTrace()}"
            )
        }
    }

    fun onChangeEvent(field: FeedbackField) {
        when (field) {
            is FeedbackField.Criterio -> {
                feedbackDto.update { currentState ->
                    updateNotasCriteriosFeedbackState(currentState, field.id, field.value)
                }
            }

            is FeedbackField.Comentario -> {
                feedbackDto.update { it.copy(comentario = field.value) }
            }
        }
    }

    fun getNotaCriterio(criterioId: Int): Double {
        val notaCriterio = feedbackDto.value.notasCriterios?.find { it.criterioId == criterioId }
        return notaCriterio?.nota ?: 0.0
    }

    private fun updateNotasCriteriosFeedbackState(
        currentState: FeedbackCriterioCriacaoDto,
        fieldId: Int,
        fieldValue: Double
    ): FeedbackCriterioCriacaoDto {
        val updatedNotasCriterios = currentState.notasCriterios!!.map { notaCriterio ->
            if (notaCriterio.criterioId == fieldId) {
                notaCriterio.copy(nota = if (fieldValue == notaCriterio.nota) 0.0 else fieldValue)
            } else {
                notaCriterio
            }
        }

        return currentState.copy(notasCriterios = updatedNotasCriterios)
    }

    fun saveFeedback() {
        viewModelScope.launch {
            try {
                Log.i("feedback", "FeedbackCriacaoDto: ${feedbackDto.value}")
                val response = feedbackRepository.save(feedbackDto.value)

                if (response.isSuccessful) {
                    Log.i("feedback", "Sucesso ao salvar o feedback: ${response.body()}")
                    isSuccessful.update { true }
                    messageToDisplay.update { "Feedback enviado" }
                } else {
                    Log.e("feedback", "Erro ao salvar o feedback: ${response.errorBody()}")
                    isError.update { true }
                    messageToDisplay.update { "Não foi possível enviar o feedback" }
                }
            } catch (e: Exception) {
                Log.e("feedback", "Exception -> erro ao salvar o feedback: ${e.printStackTrace()}")
                isError.update { true }
                messageToDisplay.update { "Não foi possível enviar o feedback" }
            }
        }
    }

    fun setControlVariablesToFalse() {
        isSuccessful.update { false }
        isError.update { false }
        messageToDisplay.update { "" }
    }

    private suspend fun validarFotoUsuario(fotoUrl: String) {
        isFotoValida.update {
            isUrlFotoUserValida(fotoUrl)
        }
    }
}