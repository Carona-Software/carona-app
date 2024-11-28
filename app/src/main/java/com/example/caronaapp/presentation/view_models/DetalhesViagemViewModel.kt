package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.feedback.FeedbackConsultaDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemCriacaoDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemListagemDto
import com.example.caronaapp.data.dto.usuario.PassageiroDto
import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemDetalhesListagemDto
import com.example.caronaapp.data.enums.StatusViagem
import com.example.caronaapp.data.repositories.CaronaRepositoryImpl
import com.example.caronaapp.data.repositories.FeedbackRepositoryImpl
import com.example.caronaapp.data.repositories.SolicitacaoViagemRepositoryImpl
import com.example.caronaapp.data.repositories.ViagemRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.presentation.screens.detalhes_viagem.DetalhesViagemUiState
import com.example.caronaapp.utils.functions.isUrlFotoUserValida
import com.example.caronaapp.utils.functions.toPassageiroDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalhesViagemViewModel(
    private val viagemRepository: ViagemRepositoryImpl,
    private val solicitacaoViagemRepository: SolicitacaoViagemRepositoryImpl,
    private val caronaRepository: CaronaRepositoryImpl,
    private val dataStoreManager: DataStoreManager,
    private val feedbackRepository: FeedbackRepositoryImpl,
) : ViewModel() {

    val isLoadingScreen = MutableStateFlow(true)
    val idUser = MutableStateFlow<Int?>(null)
    val perfilUser = MutableStateFlow<String?>(null)

    val state = MutableStateFlow(DetalhesViagemUiState())

    fun getDetalhesViagem(viagemId: Int) {
        viewModelScope.launch {
            try {
                idUser.update { dataStoreManager.getIdUser() }
                perfilUser.update { dataStoreManager.getPerfilUser() }

                val response = viagemRepository.findById(viagemId)

                if (response.isSuccessful) {
                    state.update { it.copy(viagem = setDetalhesViagemComFotosVerificadas(response.body()!!)) }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao buscar detalhes da viagem: ${response.body()}"
                    )
                    if (response.body()!!.status == StatusViagem.FINALIZADA) {
                        if (perfilUser.value == "PASSAGEIRO") {
                            verifyFeedbackFromPassageiroToMotorista(
                                passageiroId = idUser.value!!,
                                motoristaId = response.body()!!.motorista.id,
                                viagemId = response.body()!!.id
                            )
                        }
                    }
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Não foi possível buscar detalhes da viagem"
                        )
                    }
                    Log.e(
                        "detalhesViagem",
                        "Erro ao buscar detalhes da viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Houve um erro ao buscar detalhes da viagem"
                    )
                }
                Log.e(
                    "detalhesViagem",
                    "Erro ao buscar detalhes da viagem: ${e.message}"
                )
            } finally {
                isLoadingScreen.update { false }
            }
        }
    }

    private suspend fun verifyFeedbackFromPassageiroToMotorista(
        passageiroId: Int,
        motoristaId: Int,
        viagemId: Int
    ) {
        try {
            val response = feedbackRepository.existsByDestinatarioAndRemetenteAndViagem(
                FeedbackConsultaDto(
                    destinatarioId = motoristaId,
                    remetenteId = passageiroId,
                    viagemId = viagemId
                )
            )

            if (response.isSuccessful) {
                Log.i(
                    "detalhesViagem",
                    "Sucesso ao consultar se passageiro já avaliou motorista: ${response.body()}"
                )
                state.update { it.copy(motoristaFoiAvaliado = response.body()!!) }
            } else {
                Log.e(
                    "detalhesViagem",
                    "Erro ao consultar se passageiro já avaliou motorista: ${response.errorBody()}"
                )
            }
        } catch (e: Exception) {
            Log.e(
                "detalhesViagem",
                "Exception -> erro ao consultar se passageiro já avaliou motorista: ${e.message}"
            )
        }
    }

    fun onRefuseClick(solicitacao: SolicitacaoViagemListagemDto) {
        viewModelScope.launch {
            try {
                // aceitar solicitação
                val responseSolicitacao = solicitacaoViagemRepository.refuse(solicitacao.id)
                if (responseSolicitacao.isSuccessful) {
                    state.update {
                        it.copy(
                            viagem = it.viagem?.copy(
                                solicitacoes = it.viagem.solicitacoes.filter { solicitacaoViagem ->
                                    solicitacaoViagem.id != solicitacao.id
                                }
                            )
                        )
                    }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao recusar solicitação de viagem: ${responseSolicitacao.body()}"
                    )
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Houve um erro ao recusar solicitação"
                        )
                    }
                    Log.e(
                        "detalhesViagem",
                        "Erro ao recusar solicitação de viagem: ${responseSolicitacao.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Houve um erro ao recusar solicitação"
                    )
                }
                Log.e(
                    "detalhesViagem",
                    "Exception -> erro ao recusar solicitação de viagem: ${e.message}"
                )
            }
        }
    }

    fun onAcceptClick(solicitacao: SolicitacaoViagemListagemDto) {
        viewModelScope.launch {
            try {
                // aceitar solicitação
                val responseSolicitacao = solicitacaoViagemRepository.approve(solicitacao.id)
                if (responseSolicitacao.isSuccessful) {
                    state.update {
                        it.copy(
                            viagem = it.viagem?.copy(
                                solicitacoes = it.viagem.solicitacoes.filter { solicitacaoViagem ->
                                    solicitacaoViagem.id != solicitacao.id
                                },
                                passageiros = (it.viagem.passageiros) + toPassageiroDto(solicitacao.usuario)
                            )
                        )
                    }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao aceitar solicitação de viagem: ${responseSolicitacao.body()}"
                    )
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Houve um erro ao aceitar solicitação"
                        )
                    }
                    Log.e(
                        "detalhesViagem",
                        "Erro ao aceitar solicitação de viagem: ${responseSolicitacao.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Houve um erro ao aceitar solicitação"
                    )
                }
                Log.e(
                    "detalhesViagem",
                    "Erro ao aceitar solicitação de viagem e associar à viagem: ${e.message}"
                )
            }
        }
    }

    fun onCancelViagemClick() {
        state.update { it.copy(isCancelViagemModalOpened = true) }
    }

    fun onCancelReservaClick() {
        state.update { it.copy(isCancelReservaModalOpened = true) }
    }

    fun onIniciarViagemClick() {
        state.update { it.copy(isIniciarViagemModalOpened = true) }
    }

    fun onFinalizarViagemClick() {
        state.update { it.copy(isFinalizarViagemModalOpened = true) }
    }

    fun onCancelSolicitacaoReservaClick(solicitacao: SolicitacaoViagemListagemDto) {
        state.update {
            it.copy(
                isCancelSolicitacaoModalOpened = true,
                solicitacaoToDelete = solicitacao
            )
        }
    }

    fun onDismissCancelViagemRequest() {
        state.update { it.copy(isCancelViagemModalOpened = false) }
    }

    fun onDismissCancelReservaRequest() {
        state.update { it.copy(isCancelReservaModalOpened = false) }
    }

    fun onDismissCancelSolicitacaoRequest() {
        state.update {
            it.copy(
                isCancelSolicitacaoModalOpened = false,
                solicitacaoToDelete = null
            )
        }
    }

    fun onDismissIniciarViagemRequest() {
        state.update {
            it.copy(
                isIniciarViagemModalOpened = false,
            )
        }
    }

    fun onDismissFinalizarViagemRequest() {
        state.update {
            it.copy(
                isFinalizarViagemModalOpened = false,
            )
        }
    }

    fun handleCancelViagem() {
        viewModelScope.launch {
            try {
                val response = viagemRepository.delete(state.value.viagem!!.id)
                if (response.isSuccessful) {
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao deletar viagem"
                    )
                    state.update {
                        it.copy(
                            isViagemDeleted = true,
                            successMessage = "Viagem cancelada"
                        )
                    }
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Houve um erro ao cancelar a viagem"
                        )
                    }
                    Log.e(
                        "detalhesViagem",
                        "Erro ao deletar viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Houve um erro ao cancelar a viagem"
                    )
                }
                Log.e(
                    "detalhesViagem",
                    "Erro ao deletar viagem: ${e.message}"
                )
            }
        }
    }

    fun handleCancelReserva() {
        viewModelScope.launch {
            try {
                val response = caronaRepository.delete(state.value.viagem!!.id, idUser.value!!)

                if (response.isSuccessful) {
                    state.update {
                        it.copy(
                            isViagemDeleted = true,
                            successMessage = "Carona cancelada"
                        )
                    }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao cancelar reserva na viagem: ${response.body()}"
                    )
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Houve um erro ao cancelar reserva"
                        )
                    }
                    Log.e(
                        "detalhesViagem",
                        "Erro ao cancelar reserva na viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Houve um erro ao cancelar reserva"
                    )
                }
                Log.e(
                    "detalhesViagem",
                    "Exception -> erro ao cancelar reserva na viagem: ${e.message}"
                )
            }
        }
    }

    fun handleCancelSolicitacao() {
        viewModelScope.launch {
            try {
                val response = solicitacaoViagemRepository.delete(state.value.viagem!!.id)

                if (response.isSuccessful) {
                    state.update {
                        it.copy(
                            isViagemDeleted = true,
                            successMessage = "Solicitação de carona cancelada"
                        )
                    }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao cancelar reserva na viagem"
                    )
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Houve um erro ao cancelar solicitação de reserva"
                        )
                    }
                    Log.e(
                        "detalhesViagem",
                        "Erro ao cancelar reserva na viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Houve um erro ao cancelar solicitação de reserva"
                    )
                }
                Log.e(
                    "detalhesViagem",
                    "Exception -> erro ao cancelar reserva na viagem: ${e.message}"
                )
            }
        }
    }

    fun handleSolicitarCarona() {
        viewModelScope.launch {
            try {
                val solicitacaoViagemCriacao = SolicitacaoViagemCriacaoDto(
                    usuarioId = idUser.value!!,
                    viagemId = state.value.viagem!!.id
                )
                val response = solicitacaoViagemRepository.save(solicitacaoViagemCriacao)
                if (response.isSuccessful) {
                    state.update {
                        it.copy(
                            isSuccess = true,
                            successMessage = "Solicitação de carona enviada",
                            viagem = it.viagem?.copy(
                                solicitacoes = (it.viagem.solicitacoes) + response.body()!!
                            )
                        )
                    }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao solicitar reserva para a viagem: ${response.body()}"
                    )
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Houve um erro ao solicitar reserva"
                        )
                    }
                    Log.e(
                        "detalhesViagem",
                        "Erro ao solicitar reserva para a viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Houve um erro ao solicitar reserva"
                    )
                }
                Log.e(
                    "detalhesViagem",
                    "Exception -> erro ao solicitar reserva para a viagem: ${e.message}"
                )
            }
        }
    }

    fun handleIniciarViagem() {
        viewModelScope.launch {
            try {
                val response = viagemRepository.start(state.value.viagem!!.id)

                if (response.isSuccessful) {
                    state.update {
                        it.copy(
                            isSuccess = true,
                            successMessage = "Viagem iniciada",
                            viagem = it.viagem?.copy(
                                status = StatusViagem.ANDAMENTO
                            ),
                            isIniciarViagemModalOpened = false
                        )
                    }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao iniciar viagem: ${response.body()}"
                    )
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Houve um erro ao iniciar viagem"
                        )
                    }
                    Log.e(
                        "detalhesViagem",
                        "Erro ao iniciar viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Houve um erro ao iniciar viagem"
                    )
                }
                Log.e(
                    "detalhesViagem",
                    "Exception -> erro ao iniciar viagem: ${e.message}"
                )
            }
        }
    }

    fun handleFinalizarViagem() {
        viewModelScope.launch {
            try {
                val response = viagemRepository.finish(state.value.viagem!!.id)

                if (response.isSuccessful) {
                    state.update {
                        it.copy(
                            isSuccess = true,
                            successMessage = "Viagem finalizada",
                            viagem = it.viagem?.copy(
                                status = StatusViagem.FINALIZADA
                            ),
                            isFinalizarViagemModalOpened = false
                        )
                    }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao finalizar viagem: ${response.body()}"
                    )
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Houve um erro ao finalizar viagem"
                        )
                    }
                    Log.e(
                        "detalhesViagem",
                        "Erro ao finalizar viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Houve um erro ao finalizar viagem"
                    )
                }
                Log.e(
                    "detalhesViagem",
                    "Exception -> erro ao finalizar viagem: ${e.message}"
                )
            }
        }
    }

    fun setIsSuccessToFalse() {
        state.update {
            it.copy(
                isSuccess = false,
                successMessage = ""
            )
        }
    }

    fun setIsErrorToFalse() {
        state.update {
            it.copy(
                isError = false,
                errorMessage = ""
            )
        }
    }

    fun setIsViagemDeletedToFalse() {
        state.update {
            it.copy(
                isViagemDeleted = false,
                successMessage = ""
            )
        }
    }

    private suspend fun setDetalhesViagemComFotosVerificadas(viagemData: ViagemDetalhesListagemDto): ViagemDetalhesListagemDto {
        return withContext(Dispatchers.IO) {
            val motoristaValidado = validarFotoMotorista(viagemData.motorista)
            val passageirosValidados = validarFotosPassageiros(viagemData.passageiros)
            val solicitacoesValidadas = validarFotosSolicitacoes(viagemData.solicitacoes)

            viagemData.copy(
                motorista = motoristaValidado,
                passageiros = passageirosValidados,
                solicitacoes = solicitacoesValidadas
            )
        }
    }

    private suspend fun validarFotosSolicitacoes(solicitacoes: List<SolicitacaoViagemListagemDto>): List<SolicitacaoViagemListagemDto> {
        return solicitacoes.map { solicitacao ->
            solicitacao.copy(
                usuario = solicitacao.usuario.copy(
                    isFotoValida = isUrlFotoUserValida(solicitacao.usuario.fotoUrl)
                )
            )
        }
    }

    private suspend fun validarFotosPassageiros(passageiros: List<PassageiroDto>): List<PassageiroDto> {
        return passageiros.map { passageiro ->
            passageiro.copy(
                isFotoValida = isUrlFotoUserValida(passageiro.fotoUrl)
            )
        }
    }

    private suspend fun validarFotoMotorista(motorista: UsuarioSimplesListagemDto): UsuarioSimplesListagemDto {
        return motorista.copy(
            isFotoValida = isUrlFotoUserValida(motorista.fotoUrl)
        )
    }

}