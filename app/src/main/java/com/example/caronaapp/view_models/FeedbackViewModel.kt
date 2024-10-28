package com.example.caronaapp.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.endereco.EnderecoListagemDto
import com.example.caronaapp.data.dto.endereco.PrincipalTrajeto
import com.example.caronaapp.data.dto.feedback.FeedbackCriterioCriacaoDto
import com.example.caronaapp.data.dto.feedback.FeedbackListagemDto
import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioCriacaoDto
import com.example.caronaapp.data.dto.nota_criterio.NotaCriterioListagemDto
import com.example.caronaapp.data.dto.usuario.FidelizadoListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemSimplesListagemDto
import com.example.caronaapp.data.entity.CriterioFeedback
import com.example.caronaapp.data.enums.StatusViagem
import com.example.caronaapp.screens.feedback.FeedbackField
import com.example.caronaapp.service.repository.FeedbackRepository
import com.example.caronaapp.service.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class FeedbackViewModel(
    val feedbackRepository: FeedbackRepository,
    val usuarioRepository: UsuarioRepository
) : ViewModel() {

    val criteriosFeedback = MutableStateFlow<List<CriterioFeedback>?>(
//        null
        listOf(
            CriterioFeedback(
                id = 1,
                nome = "Comunicação"
            ),
            CriterioFeedback(
                id = 2,
                nome = "Segurança"
            ),
            CriterioFeedback(
                id = 3,
                nome = "Pontualidade"
            ),
            CriterioFeedback(
                id = 4,
                nome = "Comportamento"
            ),
            CriterioFeedback(
                id = 5,
                nome = "Dirigibilidade"
            ),
        )
    )

    val usuarioAvaliado = MutableStateFlow<UsuarioDetalhesListagemDto?>(
//        null
        UsuarioDetalhesListagemDto(
            id = 1,
            nome = "Gustavo Medeiros",
            email = "gustavo@email.com",
            cpf = "50378814800",
            genero = "MASCULINO",
            perfil = "MOTORISTA",
            dataNascimento = LocalDate.now(),
            fotoUrl = "https://res.cloudinary.com/carona/image/upload/v1729863605/ph8npbut9xtt2vhg2i0z.png",
            notaMedia = 4.1,
            endereco = EnderecoListagemDto(
                id = 1,
                cep = "04244000",
                uf = "SP",
                cidade = "São Paulo",
                logradouro = "Estrada das Lágrimas",
                bairro = "São João CLímaco",
                numero = 3621
            ),
            avaliacoes = listOf(
                FeedbackListagemDto(
                    data = LocalDate.now(),
                    comentario = "Dirige muito bem! A viagem foi tranquila, podia ser melhor na comunicação",
                    avaliador = FeedbackListagemDto.AvaliadorDto(
                        id = 1,
                        nome = "Kaiky Cruz",
                        fotoUrl = "foto_kaiky"
                    ),
                    notaMedia = 4.3,
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
                    avaliador = FeedbackListagemDto
                        .AvaliadorDto(
                            id = 1,
                            nome = "Kaiky Cruz",
                            fotoUrl = "foto_kaiky"
                        ),
                    notaMedia = 3.2,
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
            ),
            carros = listOf(
                UsuarioDetalhesListagemDto.CarroDto(
                    marca = "Honda",
                    modelo = "Fit",
                    cor = "Preto",
                    placa = "ABC1D06"
                )
            ),
            fidelizados = listOf(
                FidelizadoListagemDto(
                    id = 2,
                    nome = "Kaiky Cruz",
                    fotoUrl = "foto_kaiky",
                    notaGeral = 3.5,
                    ufLocalidade = "SP",
                    cidadeLocalidade = "São Paulo",
                    qtdViagensJuntos = 1
                )
            ),
            viagens = listOf(
                ViagemSimplesListagemDto(
                    id = 1,
                    data = LocalDate.now(),
                    hora = LocalTime.now(),
                    preco = 30.0,
                    status = StatusViagem.FINALIZADA
                )
            ),
            principalTrajeto = PrincipalTrajeto(
                ufChegada = "SP",
                cidadeChegada = "Campinas",
                ufPartida = "SP",
                cidadePartida = "São Paulo"
            )
        )
    )

    val feedbackState = MutableStateFlow(FeedbackCriterioCriacaoDto())

    suspend fun getCriteriosFeedback() {
        try {
            val response = feedbackRepository.findAllCriterios()

            if (response.isSuccessful) {
                if (response.code() == 204) {
                    Log.e(
                        "criteriosFeedback",
                        "Não há critérios de Feedback cadastrados!"
                    )
                } else {
                    criteriosFeedback.update {
                        response.body()!!.filter {
                            if (usuarioAvaliado.value != null && usuarioAvaliado.value!!.perfil.uppercase() == "MOTORISTA") {
                                it.nome.uppercase() != "COMPORTAMENTO"
                            } else {
                                it.nome.uppercase() != "DIRIGIBILIDADE"
                            }
                        }
                    }

                    feedbackState.update {
                        it.copy(notasCriterios = criteriosFeedback.value!!.map { criterio ->
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

    suspend fun getUsuarioAvaliado(usuarioId: Int) {
        try {
            val response = usuarioRepository.findById(usuarioId)

            if (response.isSuccessful) {
                usuarioAvaliado.update { response.body() }
                feedbackState.update { it.copy(destinatarioId = response.body()!!.id) }
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

    fun setupFeedback(viagemId: Int, usuarioId: Int) {
        viewModelScope.launch {
            feedbackState.update { it.copy(viagemId = viagemId, data = LocalDate.now()) }
//            getUsuarioAvaliado(usuarioId)
            getCriteriosFeedback()
        }
    }

    fun onChangeEvent(field: FeedbackField) {
        Log.i("dtoCriacao", "FeedbackCriterioCriacaoDto: ${feedbackState.value}")
        when (field) {
            is FeedbackField.Criterio -> {
                feedbackState.update { currentState ->
                    updateNotasCriteriosFeedbackState(currentState, field.id, field.value)
                }
            }

            is FeedbackField.Comentario -> {
                feedbackState.update { it.copy(comentario = field.value) }
            }
        }
    }

    fun getNotaCriterio(criterioId: Int): Double {
        val notaCriterio = feedbackState.value.notasCriterios?.find { it.criterioId == criterioId }
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
                val response = feedbackRepository.save(feedbackState.value)

                if (response.isSuccessful) {
                    Log.i("feedback", "Exception -> erro ao salvar o feedback: ${response.body()}")
                } else {
                    Log.e("feedback", "Erro ao salvar o feedback: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("feedback", "Exception -> erro ao salvar o feedback: ${e.printStackTrace()}")
            }
        }
    }
}