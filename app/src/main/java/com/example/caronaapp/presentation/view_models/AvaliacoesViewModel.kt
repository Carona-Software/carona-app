package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.repositories.FeedbackRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AvaliacoesViewModel(
    private val repository: FeedbackRepositoryImpl,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val avaliacoes = MutableStateFlow<List<FeedbackListagemDto>?>(null)

    init {
        getAvaliacoes()
//        var novasAvaliacoes =
//            listOf(
//                FeedbackListagemDto(
//                    data = LocalDate.now(),
//                    comentario = "Dirige muito bem! A viagem foi tranquila, podia ser melhor na comunicação",
//                    avaliador = FeedbackListagemDto.AvaliadorDto(
//                        id = 1,
//                        nome = "Kaiky Cruz",
//                        fotoUrl = "foto_kaiky"
//                    ),
//                    notaMedia = 3.5,
//                    notasCriterios = listOf(
//                        NotaCriterioListagemDto(
//                            criterio = "Comunicação",
//                            nota = 2.0
//                        ),
//                        NotaCriterioListagemDto(
//                            criterio = "Pontualidade",
//                            nota = 5.0
//                        ),
//                        NotaCriterioListagemDto(
//                            criterio = "Dirigibilidade",
//                            nota = 3.5
//                        ),
//                        NotaCriterioListagemDto(
//                            criterio = "Segurança",
//                            nota = 4.0
//                        )
//                    )
//                ),
//                FeedbackListagemDto(
//                    data = LocalDate.now(),
//                    comentario = "Dirige muito bem! A viagem foi tranquila, podia ser melhor na comunicação",
//                    avaliador = FeedbackListagemDto.AvaliadorDto(
//                        id = 1,
//                        nome = "Ewerton Lima",
//                        fotoUrl = "foto_kaiky"
//                    ),
//                    notaMedia = 4.2,
//                    notasCriterios = listOf(
//                        NotaCriterioListagemDto(
//                            criterio = "Comunicação",
//                            nota = 4.0
//                        ),
//                        NotaCriterioListagemDto(
//                            criterio = "Pontualidade",
//                            nota = 2.0
//                        ),
//                        NotaCriterioListagemDto(
//                            criterio = "Dirigibilidade",
//                            nota = 5.0
//                        ),
//                        NotaCriterioListagemDto(
//                            criterio = "Segurança",
//                            nota = 1.0
//                        )
//                    )
//                )
//            )
    }

    private fun getAvaliacoes() {
        viewModelScope.launch {
            try {
                val idUser = dataStoreManager.getIdUser()
                val response = repository.findByUsuarioId(idUser!!)

                if (response.isSuccessful) {
                    Log.i("avaliacoes", "Sucesso ao buscar avaliações do usuário")
                    if (response.code() == 204) {
                        avaliacoes.update { null }
                    } else {
                        avaliacoes.update { response.body() }
                    }
                } else {
                    Log.e(
                        "avaliacoes",
                        "Erro ao buscar avaliações do usuário: ${response.errorBody()}"
                    )
                    avaliacoes.update { null }
                }
            } catch (e: Exception) {
                Log.e(
                    "avaliacoes",
                    "Exception -> erro ao buscar avaliações do usuário: ${e.printStackTrace()}"
                )
                avaliacoes.update { null }
            }
        }
    }
}