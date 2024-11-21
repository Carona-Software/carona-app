package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.repositories.FeedbackRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.utils.functions.isUrlFotoUserValida
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AvaliacoesViewModel(
    private val repository: FeedbackRepositoryImpl,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val isLoadingScreen = MutableStateFlow(true)
    val avaliacoes = MutableStateFlow<List<FeedbackListagemDto>?>(null)

    init {
        getAvaliacoes()
    }

    private fun getAvaliacoes() {
        viewModelScope.launch {
            try {
                val idUser = dataStoreManager.getIdUser()
                val response = repository.findByUsuarioId(idUser!!)

                if (response.isSuccessful) {
                    Log.i("avaliacoes", "Sucesso ao buscar avaliações do usuário: ${response.body()}")
                    if (response.code() == 200) {
                        avaliacoes.update {
                            setAvaliacoesComFotosVerificadas(
                                response.body() ?: emptyList()
                            )
                        }
                    }
                } else {
                    Log.e(
                        "avaliacoes",
                        "Erro ao buscar avaliações do usuário: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "avaliacoes",
                    "Exception -> erro ao buscar avaliações do usuário: ${e.printStackTrace()}"
                )
            } finally {
                isLoadingScreen.update { false }
            }
        }
    }

    private suspend fun setAvaliacoesComFotosVerificadas(feedbacks: List<FeedbackListagemDto>): List<FeedbackListagemDto> {
        return withContext(Dispatchers.IO) {
            feedbacks.map { feedback ->
                async {
                    feedback.apply {
                        this.avaliador.isFotoValida = isUrlFotoUserValida(this.avaliador.fotoUrl)
                    }
                }
            }.awaitAll() // Espera todas as validações serem concluídas
        }
    }
}