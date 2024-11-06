package com.example.caronaapp.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioListagemDto
import com.example.caronaapp.data.repositories.FeedbackRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class AvaliacoesViewModel(
    val repository: FeedbackRepositoryImpl
) : ViewModel() {

    var avaliacoes = MutableStateFlow<List<FeedbackListagemDto>?>(null)
        private set

    init {
//        getAvaliacoes()
        var novasAvaliacoes =
            listOf(
                FeedbackListagemDto(
                    data = LocalDate.now(),
                    comentario = "Dirige muito bem! A viagem foi tranquila, podia ser melhor na comunicação",
                    avaliador = FeedbackListagemDto.AvaliadorDto(
                        id = 1,
                        nome = "Kaiky Cruz",
                        fotoUrl = "foto_kaiky"
                    ),
                    notaMedia = 3.5,
                    notasCriterios = listOf(
                        NotaCriterioListagemDto(
                            criterio = "Comunicação",
                            nota = 2.0
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Pontualidade",
                            nota = 5.0
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Dirigibilidade",
                            nota = 3.5
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Segurança",
                            nota = 4.0
                        )
                    )
                ),
                FeedbackListagemDto(
                    data = LocalDate.now(),
                    comentario = "Dirige muito bem! A viagem foi tranquila, podia ser melhor na comunicação",
                    avaliador = FeedbackListagemDto.AvaliadorDto(
                        id = 1,
                        nome = "Ewerton Lima",
                        fotoUrl = "foto_kaiky"
                    ),
                    notaMedia = 4.2,
                    notasCriterios = listOf(
                        NotaCriterioListagemDto(
                            criterio = "Comunicação",
                            nota = 4.0
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Pontualidade",
                            nota = 2.0
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Dirigibilidade",
                            nota = 5.0
                        ),
                        NotaCriterioListagemDto(
                            criterio = "Segurança",
                            nota = 1.0
                        )
                    )
                )
            )

//        avaliacoes.value = novasAvaliacoes
    }

    private fun getAvaliacoes() {
        viewModelScope.launch { avaliacoes.value = repository.findByUsuarioId(1).body()!! }
    }
}