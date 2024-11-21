package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoFidelizacaoCriacaoDto
import com.example.caronaapp.data.repositories.CaronaRepositoryImpl
import com.example.caronaapp.data.repositories.FidelizacaoRepositoryImpl
import com.example.caronaapp.data.repositories.SolicitacaoFidelizacaoRepositoryImpl
import com.example.caronaapp.data.repositories.UsuarioRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.presentation.screens.perfil_outro_usuario.PerfilOutroUsuarioUiState
import com.example.caronaapp.utils.functions.calculateCriteriosFeedback
import com.example.caronaapp.utils.functions.isUrlFotoUserValida
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilOutroUsuarioViewModel(
    private val usuarioRepository: UsuarioRepositoryImpl,
    private val dataStoreManager: DataStoreManager,
    private val caronaRepository: CaronaRepositoryImpl,
    private val solicitacaoFidelizacaoRepository: SolicitacaoFidelizacaoRepositoryImpl,
    private val fidelizacaoRepository: FidelizacaoRepositoryImpl
) : ViewModel() {

    val state = MutableStateFlow(PerfilOutroUsuarioUiState())

    fun getDetalhesUsuario(id: Int) {
        viewModelScope.launch {
            try {
                state.update {
                    it.copy(perfilUser = dataStoreManager.getPerfilUser() ?: "")
                }

                val response = usuarioRepository.findById(id)

                if (response.isSuccessful) {
                    state.update {
                        it.copy(
                            userData = response.body()!!,
                            avaliacoesCriterioUser = calculateCriteriosFeedback(response.body()!!.avaliacoes),
                            isFotoValida = if (response.body() == null) false
                            else isUrlFotoUserValida(response.body()!!.fotoUrl),
                            totalViagensJuntos = countViagensBetweenMotoristaAndPassageiro(
                                response.body()!!.id,
                                dataStoreManager.getIdUser()!!
                            )
                        )
                    }
                    if (response.body()?.perfil == "MOTORISTA") {
                    // verifica se passageiro e motorista estão fidelizados
                        state.update {
                            it.copy(
                                isPassageiroFidelizado = verifyFidelizacaoBetweenPassageiroAndMotorista(
                                    passageiroId = dataStoreManager.getIdUser() ?: 0,
                                    motoristaId = response.body()?.id ?: 0
                                )
                            )
                        }
                    }
                } else {
                    Log.e(
                        "perfil outro usuario",
                        "Erro ao buscar informações do usuário: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "perfil outro usuario",
                    "Exception -> erro ao buscar informações do usuário: ${e.message}"
                )
            } finally {
                state.update {
                    it.copy(isLoadingScreen = false)
                }
            }
        }
    }

    private suspend fun verifyFidelizacaoBetweenPassageiroAndMotorista(
        passageiroId: Int,
        motoristaId: Int
    ): Boolean {
        try {
            val response = fidelizacaoRepository.existsFidelizacao(
                passageiroId = passageiroId,
                motoristaId = motoristaId
            )

            if (response.isSuccessful) {
                Log.i(
                    "perfilOutroUsuario",
                    "Sucesso ao verificar fidelização entre motorista e passageiro: ${response.body()}"
                )
                return response.body()!!
            } else {
                Log.e(
                    "perfilOutroUsuario",
                    "Erro ao verificar fidelização entre motorista e passageiro: ${response.errorBody()}"
                )
                return false
            }
        } catch (e: Exception) {
            Log.e(
                "perfilOutroUsuario",
                "Exception -> erro ao verificar fidelização entre motorista e passageiro: ${e.message}"
            )
            return false
        }
    }

    private suspend fun countViagensBetweenMotoristaAndPassageiro(
        motoristaId: Int,
        passageiroId: Int
    ): Int {
        try {
            val response = caronaRepository.countViagensBetweenMotoristaAndPassageiro(
                motoristaId,
                passageiroId
            )

            if (response.isSuccessful) {
                Log.i(
                    "perfilOutroUsuario",
                    "Sucesso ao contar viagens entre motorista e passageiro: ${response.body()}"
                )
                return response.body()!!
            } else {
                Log.e(
                    "perfilOutroUsuario",
                    "Erro ao contar viagens entre motorista e passageiro: ${response.errorBody()}"
                )
                return 0
            }
        } catch (e: Exception) {
            Log.e(
                "perfilOutroUsuario",
                "Exception -> erro ao contar viagens entre motorista e passageiro: ${e.message}"
            )
            return 0
        }
    }

    fun handleSolicitarFidelizacao() {
        viewModelScope.launch {
            try {
                val solicitacao = SolicitacaoFidelizacaoCriacaoDto(
                    motoristaId = state.value.userData!!.id,
                    passageiroId = dataStoreManager.getIdUser()!!
                )

                val response = solicitacaoFidelizacaoRepository.save(solicitacao)

                if (response.isSuccessful) {
                    Log.i(
                        "perfilOutroUsuario",
                        "Sucesso ao solicitar fidelização: ${response.body()}"
                    )
                    state.update {
                        it.copy(
                            isSuccessful = true,
                            messageToDisplay = "Solicitação de fidelização enviada",
                        )
                    }
                } else {
                    Log.e(
                        "perfilOutroUsuario",
                        "Erro ao solicitar fidelização: ${response.errorBody()}"
                    )
                    state.update {
                        it.copy(
                            isError = true,
                            messageToDisplay = "Não foi possível enviar a solicitação",
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e(
                    "perfilOutroUsuario",
                    "Exception -> erro ao solicitar fidelização: ${e.message}"
                )
                state.update {
                    it.copy(
                        isError = true,
                        messageToDisplay = "Não foi possível enviar a solicitação",
                    )
                }
            }
        }
    }

    fun setControlVariablesToFalse() {
        state.update {
            it.copy(
                isSuccessful = false,
                isError = false,
                messageToDisplay = "",
            )
        }
    }
}