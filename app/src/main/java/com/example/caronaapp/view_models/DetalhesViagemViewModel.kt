package com.example.caronaapp.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.carona.CaronaCriacaoDto
import com.example.caronaapp.data.dto.solicitacao.SolicitacaoViagemListagemDto
import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto
import com.example.caronaapp.data.dto.viagem.Coordenadas
import com.example.caronaapp.data.dto.viagem.LocalidadeDto
import com.example.caronaapp.data.dto.viagem.TrajetoDto
import com.example.caronaapp.data.dto.viagem.ViagemDetalhesListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemSimplesListagemDto
import com.example.caronaapp.data.enums.StatusSolicitacao
import com.example.caronaapp.data.enums.StatusViagem
import com.example.caronaapp.service.repository.CaronaRepository
import com.example.caronaapp.service.repository.SolicitacaoViagemRepository
import com.example.caronaapp.service.repository.ViagemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class DetalhesViagemViewModel(
    val viagemRepository: ViagemRepository,
    val solicitacaoViagemRepository: SolicitacaoViagemRepository,
    val caronaRepository: CaronaRepository
) : ViewModel() {

    var viagem = MutableStateFlow<ViagemDetalhesListagemDto?>(
//        null
        ViagemDetalhesListagemDto(
            id = 1,
            data = LocalDate.now(),
            horarioSaida = LocalTime.now(),
            horarioChegada = LocalTime.now(),
            preco = 30.0,
            status = StatusViagem.PENDENTE,
            apenasMulheres = false,
            capacidadePassageiros = 4,
            motorista = UsuarioSimplesListagemDto(
                id = 1,
                nome = "Matheus Alves",
                notaGeral = 4.2,
                fotoUrl = "foto",
                perfil = "MOTORISTA"
            ),
            passageiros =
//            null
            listOf(
                UsuarioSimplesListagemDto(
                    id = 1,
                    nome = "Ewerton Lima",
                    notaGeral = 4.0,
                    fotoUrl = "foto",
                    perfil = "PASSAGEIRO"
                ),
                UsuarioSimplesListagemDto(
                    id = 2,
                    nome = "Kauã Queiroz",
                    notaGeral = 4.2,
                    fotoUrl = "foto",
                    perfil = "PASSAGEIRO"
                )
            ),
            carro = ViagemDetalhesListagemDto.CarroDto(
                marca = "Honda",
                modelo = "Fit",
                placa = "ABC1D03",
                cor = "Preto"
            ),
            trajeto = TrajetoDto(
                pontoPartida = LocalidadeDto(
                    Coordenadas(
                        latitude = 0.0,
                        longitude = 0.0
                    ),
                    "04244000",
                    "SP",
                    "São Paulo",
                    "São João Clímaco",
                    "Estrada das Lágrimas",
                    300
                ),
                pontoChegada = LocalidadeDto(
                    Coordenadas(
                        latitude = 0.0,
                        longitude = 0.0
                    ),
                    "12045000",
                    "SP",
                    "Taubaté",
                    "Centro",
                    "Avenida Independência",
                    680
                )
            )
        )
    )
        private set

    var solicitacoes = MutableStateFlow<List<SolicitacaoViagemListagemDto>?>(
//        null
        listOf(
            SolicitacaoViagemListagemDto(
                id = 1,
                status = StatusSolicitacao.PENDENTE,
                usuario = UsuarioSimplesListagemDto(
                    id = 1,
                    nome = "Ewerton Lima",
                    notaGeral = 4.0,
                    fotoUrl = "foto",
                    perfil = "PASSAGEIRO"
                ),
                ViagemSimplesListagemDto(
                    id = 1,
                    data = LocalDate.now(),
                    hora = LocalTime.now(),
                    preco = 30.0,
                    status = StatusViagem.PENDENTE
                )
            ),
            SolicitacaoViagemListagemDto(
                id = 1,
                status = StatusSolicitacao.PENDENTE,
                usuario = UsuarioSimplesListagemDto(
                    id = 3,
                    nome = "Lucas Arantes",
                    notaGeral = 4.3,
                    fotoUrl = "foto",
                    perfil = "PASSAGEIRO"
                ),
                ViagemSimplesListagemDto(
                    id = 1,
                    data = LocalDate.now(),
                    hora = LocalTime.now(),
                    preco = 30.0,
                    status = StatusViagem.PENDENTE
                )
            )
        )
    )
        private set

    var isViagemDeleted = MutableStateFlow(false)
        private set

    fun getDetalhesViagem(viagemId: Int) {
        viewModelScope.launch {
            try {
                val response = viagemRepository.findById(viagemId)
                if (response.isSuccessful) {
                    viagem.value = response.body()
                } else {
                    viagem.value = null
                    Log.e(
                        "detalhesViagem",
                        "Erro ao buscar detalhes da viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                viagem.value = null
                Log.e(
                    "detalhesViagem",
                    "Erro ao buscar detalhes da viagem: ${e.message}"
                )
            }
        }
    }

    fun getSolicitacoesViagem(viagemId: Int) {
        viewModelScope.launch {
            try {
                val response = solicitacaoViagemRepository.findPendentesByViagemId(viagemId)
                if (response.isSuccessful) {
                    solicitacoes.value = response.body()
                } else {
                    solicitacoes.value = null
                    Log.e(
                        "detalhesViagem",
                        "Erro ao buscar solicitações para a viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                solicitacoes.value = null
                Log.e(
                    "detalhesViagem",
                    "Erro ao buscar solicitações para a viagem: ${e.message}"
                )
            }
        }
    }

    fun onRefuseClick(solicitacao: SolicitacaoViagemListagemDto) {
        viewModelScope.launch {
            try {
                // aceitar solicitação
                val responseSolicitacao = solicitacaoViagemRepository.refuse(solicitacao.id)
                if (responseSolicitacao.isSuccessful) {
                    solicitacoes.update {
                        solicitacoes.value?.filter { it.id != solicitacao.id }
                    }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao recusar solicitação de viagem: ${responseSolicitacao.body()}"
                    )
                } else {
                    Log.e(
                        "detalhesViagem",
                        "Erro ao recusar solicitação de viagem: ${responseSolicitacao.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "detalhesViagem",
                    "Erro ao recusar solicitação de viagem: ${e.message}"
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
                    solicitacoes.update { currentList ->
                        currentList?.filter { it.id != solicitacao.id }
                    }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao aceitar solicitação de viagem: ${responseSolicitacao.body()}"
                    )
                } else {
                    Log.e(
                        "detalhesViagem",
                        "Erro ao aceitar solicitação de viagem: ${responseSolicitacao.errorBody()}"
                    )
                }

                // associar a viagem
                val caronaCriacaoDto = CaronaCriacaoDto(
                    viagemId = solicitacao.viagem.id,
                    usuarioId = solicitacao.usuario.id
                )
                val responseCarona = caronaRepository.save(caronaCriacaoDto)
                if (responseCarona.isSuccessful) {
                    viagem.update { currentData ->
                        currentData?.copy(
                            passageiros = (currentData.passageiros
                                ?: emptyList()) + solicitacao.usuario
                        )
                    }
                    Log.i(
                        "detalhesViagem",
                        "Sucesso ao associar usuário à viagem: ${responseCarona.body()}"
                    )
                } else {
                    Log.e(
                        "detalhesViagem",
                        "Erro ao associar usuário à viagem: ${responseCarona.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "detalhesViagem",
                    "Erro ao aceitar solicitação de viagem e associar à viagem: ${e.message}"
                )
            }
        }
    }

    fun handleCancelViagem(viagemId: Int) {
        viewModelScope.launch {
            try {
                val response = viagemRepository.delete(viagemId)
                if (response.isSuccessful) {
                    isViagemDeleted.value = true
                } else {
                    Log.e(
                        "detalhesViagem",
                        "Erro ao deletar viagem: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "detalhesViagem",
                    "Erro ao deletar viagem: ${e.message}"
                )
            }
        }
    }
}