package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.fidelizacao.FidelizacaoCriacaoDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoFidelizacaoListagemDto
import com.example.caronaapp.data.dto.usuario.FidelizadoListagemDto
import com.example.caronaapp.data.enums.StatusSolicitacao
import com.example.caronaapp.data.repositories.FidelizacaoRepositoryImpl
import com.example.caronaapp.data.repositories.SolicitacaoFidelizacaoRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FidelizadosViewModel(
    private val fidelizacaoRepository: FidelizacaoRepositoryImpl,
    private val solicitacaoFidelizacaoRepository: SolicitacaoFidelizacaoRepositoryImpl
) : ViewModel() {

    var fidelizados = MutableStateFlow<List<FidelizadoListagemDto>?>(null)
        private set

    var solicitacoes = MutableStateFlow<List<SolicitacaoFidelizacaoListagemDto>?>(null)
        private set

    var isRemoveFidelizadoDialogOpened = MutableStateFlow(false)
        private set

    var fidelizadoToDelete = MutableStateFlow<FidelizadoListagemDto?>(null)
        private set

    init {
//        _getFidelizados()
//        _getSolicitacoesFidelizacao()
        val fidelizadosLista = listOf(
            FidelizadoListagemDto(
                id = 1,
                fotoUrl = "foto",
                nome = "Matheus Alves",
                notaGeral = 4.1,
                cidadeLocalidade = "São Paulo",
                ufLocalidade = "SP",
                qtdViagensJuntos = 3
            ),
            FidelizadoListagemDto(
                id = 1,
                fotoUrl = "foto",
                nome = "Lucas Arantes",
                notaGeral = 4.4,
                cidadeLocalidade = "Campinas",
                ufLocalidade = "SP",
                qtdViagensJuntos = 5
            ),
        )
        fidelizados.value = fidelizadosLista

        val solicitacoesFidelizacao = listOf(
            SolicitacaoFidelizacaoListagemDto(
                id = 1,
                status = StatusSolicitacao.PENDENTE,
                motorista = FidelizadoListagemDto(
                    id = 1,
                    fotoUrl = "foto",
                    nome = "Matheus Alves",
                    notaGeral = 4.1,
                    cidadeLocalidade = "São Paulo",
                    ufLocalidade = "SP",
                    qtdViagensJuntos = 5
                ),
                passageiro = FidelizadoListagemDto(
                    id = 1,
                    fotoUrl = "foto",
                    nome = "Lucas Arantes",
                    notaGeral = 4.4,
                    cidadeLocalidade = "Campinas",
                    ufLocalidade = "SP",
                    qtdViagensJuntos = 5
                )
            )
        )
        solicitacoes.value = solicitacoesFidelizacao
    }

    private fun _getFidelizados() {
        viewModelScope.launch {
            fidelizados.value = fidelizacaoRepository.findByUsuarioId(1).body()!!
        }
    }

    private fun _getSolicitacoesFidelizacao() {
        viewModelScope.launch {
            solicitacoes.value =
                solicitacaoFidelizacaoRepository.findPendentesByUsuarioId(1).body()!!
        }
    }

    fun onDismissDialog() {
        isRemoveFidelizadoDialogOpened.value = false
    }

    fun onRemoverClick(fidelizado: FidelizadoListagemDto) {
        isRemoveFidelizadoDialogOpened.value = true
        fidelizadoToDelete.value = fidelizado
    }

    fun handleDeleteFidelizado() {
        viewModelScope.launch {
            try {
                val response = fidelizacaoRepository.delete(1, fidelizadoToDelete.value!!.id)

                if (response.isSuccessful) {
                    fidelizadoToDelete.value = null
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
}