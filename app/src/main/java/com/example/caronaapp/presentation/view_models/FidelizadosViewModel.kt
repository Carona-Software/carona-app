package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.fidelizacao.FidelizacaoCriacaoDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoFidelizacaoListagemDto
import com.example.caronaapp.data.dto.usuario.FidelizadoListagemDto
import com.example.caronaapp.data.repositories.FidelizacaoRepositoryImpl
import com.example.caronaapp.data.repositories.SolicitacaoFidelizacaoRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.utils.functions.isUrlFotoUserValida
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FidelizadosViewModel(
    private val fidelizacaoRepository: FidelizacaoRepositoryImpl,
    private val solicitacaoFidelizacaoRepository: SolicitacaoFidelizacaoRepositoryImpl,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val isLoadingScreen = MutableStateFlow(true)
    val fidelizados = MutableStateFlow<List<FidelizadoListagemDto>?>(null)
    val solicitacoes = MutableStateFlow<List<SolicitacaoFidelizacaoListagemDto>?>(null)
    val isRemoveFidelizadoDialogOpened = MutableStateFlow(false)
    val fidelizadoToDelete = MutableStateFlow<FidelizadoListagemDto?>(null)

    init {
        _getFidelizados()
        _getSolicitacoesFidelizacao()
    }

    private fun _getFidelizados() {
        viewModelScope.launch {
            try {
                val idUser = dataStoreManager.getIdUser()
                val response = fidelizacaoRepository.findByUsuarioId(idUser!!)

                if (response.isSuccessful) {
                    Log.i("fidelizados", "Sucesso ao buscar fidelizados do usuário")
                    Log.i("fidelizados", "Status: ${response.code()}")
                    if (response.code() == 200) {
                        fidelizados.update {
                            validarFotosFidelizados(
                                response.body() ?: emptyList()
                            )
                        }
                    }
                } else {
                    Log.e(
                        "fidelizados",
                        "Erro ao buscar fidelizados do usuário: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "fidelizados",
                    "Exception -> erro ao buscar fidelizados do usuário: ${e.printStackTrace()}"
                )
            }
        }
    }

    private fun _getSolicitacoesFidelizacao() {
        viewModelScope.launch {
            try {
                val idUser = dataStoreManager.getIdUser()
                val response = solicitacaoFidelizacaoRepository.findPendentesByUsuarioId(idUser!!)

                if (response.isSuccessful) {
                    Log.i(
                        "fidelizados",
                        "Sucesso ao buscar solicitações de fidelização para o usuário"
                    )
                    Log.i(
                        "fidelizados",
                        "Status: ${response.code()}"
                    )
                    if (response.code() == 200) {
                        solicitacoes.update {
                            validarFotosSolicitacoes(
                                response.body() ?: emptyList()
                            )
                        }
                    }
                } else {
                    Log.e(
                        "fidelizados",
                        "Erro ao buscar solicitações de fidelização para o usuário: ${response.errorBody()}"
                    )
                    solicitacoes.update { null }
                }
            } catch (e: Exception) {
                Log.e(
                    "fidelizados",
                    "Exception -> erro ao buscar solicitações de fidelização para o usuário: ${e.printStackTrace()}"
                )
            } finally {
                isLoadingScreen.update { false }
            }
        }
    }

    fun onDismissDialog() {
        isRemoveFidelizadoDialogOpened.update { false }
    }

    fun onRemoverClick(fidelizado: FidelizadoListagemDto) {
        isRemoveFidelizadoDialogOpened.update { true }
        fidelizadoToDelete.update { fidelizado }
    }

    fun handleDeleteFidelizado() {
        viewModelScope.launch {
            try {
                val response = fidelizacaoRepository.delete(1, fidelizadoToDelete.value!!.id)

                if (response.isSuccessful) {
                    fidelizadoToDelete.update { null }
                    _getFidelizados()
                    Log.i("fidelizados", "Sucesso ao deletar fidelização")
                } else {
                    Log.e("fidelizados", "Erro ao deletar fidelização: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("fidelizados", "Erro ao deletar fidelização: ${e.message}")
            }
        }
        onDismissDialog()
    }

    fun handleRefuseFidelizado(solicitacao: SolicitacaoFidelizacaoListagemDto) {
        viewModelScope.launch {
            try {
                val response = solicitacaoFidelizacaoRepository.refuse(solicitacao.id)

                if (response.isSuccessful) {
                    _getSolicitacoesFidelizacao()
                    Log.i("fidelizados", "Sucesso ao recusar solicitação de fidelização")
                } else {
                    Log.e("fidelizados", "Erro ao recusar solicitação: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("fidelizados", "Erro ao deletar fidelização: ${e.message}")
            }
        }
    }

    fun handleAcceptFidelizado(solicitacao: SolicitacaoFidelizacaoListagemDto) {
        viewModelScope.launch {
            try {
                // Solicitação
                val responseSolicitacao = solicitacaoFidelizacaoRepository.approve(solicitacao.id)
                if (responseSolicitacao.isSuccessful) {
                    _getSolicitacoesFidelizacao()
                    Log.i("fidelizados", "Sucesso ao deletar fidelização")
                } else {
                    Log.i(
                        "fidelizados",
                        "Erro ao aceitar solicitação de fidelização: ${responseSolicitacao.errorBody()}"
                    )
                }

                // Fidelização
                val fidelizacaoCriacaoDto = FidelizacaoCriacaoDto(
                    motoristaId = solicitacao.motorista.id,
                    passageiroId = solicitacao.passageiro.id
                )
                val responseFidelizacao = fidelizacaoRepository.save(fidelizacaoCriacaoDto)
                if (responseFidelizacao.isSuccessful) {
                    _getFidelizados()
                    Log.i("fidelizados", "Sucesso ao fidelizar usuários")
                } else {
                    Log.i(
                        "fidelizados",
                        "Erro ao fidelizar usuários: ${responseSolicitacao.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "fidelizados",
                    "Erro ao aceitar fidelização e fidelizar o usuário: ${e.message}"
                )
            }
        }
    }

    private suspend fun validarFotosFidelizados(fidelizados: List<FidelizadoListagemDto>): List<FidelizadoListagemDto> {
        return withContext(Dispatchers.IO) {
            fidelizados.map { fidelizado ->
                async {
                    fidelizado.apply {
                        isFotoValida = isUrlFotoUserValida(fotoUrl)
                    }
                }
            }.awaitAll() // Espera todas as validações serem concluídas
        }
    }

    private suspend fun validarFotosSolicitacoes(solicitacoes: List<SolicitacaoFidelizacaoListagemDto>): List<SolicitacaoFidelizacaoListagemDto> {
        return withContext(Dispatchers.IO) {
            solicitacoes.map { solicitacao ->
                async {
                    solicitacao.apply {
                        this.passageiro.isFotoValida = isUrlFotoUserValida(this.passageiro.fotoUrl)
                    }
                }
            }.awaitAll() // Espera todas as validações serem concluídas
        }
    }
}