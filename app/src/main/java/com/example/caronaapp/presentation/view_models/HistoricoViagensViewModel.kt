package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.viagem.ViagemListagemDto
import com.example.caronaapp.data.repositories.ViagemRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HistoricoViagensViewModel(
    private val repository: ViagemRepositoryImpl,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val perfilUser = MutableStateFlow("")

    val viagens = MutableStateFlow<List<ViagemListagemDto>?>(
        null
//        listOf(
//            ViagemListagemDto(
//                id = 1,
//                capacidadePassageiros = 4,
//                apenasMulheres = false,
//                data = LocalDate.of(2023, 11, 25),
//                horarioSaida = LocalTime.of(17, 30),
//                horarioChegada = LocalTime.of(20, 0),
//                preco = 30.0,
//                statusViagem = StatusViagem.PENDENTE,
//                motorista = UsuarioSimplesListagemDto(
//                    id = 1,
//                    nome = "Ewerton Lima",
//                    notaGeral = 4.0,
//                    fotoUrl = "foto",
//                    perfil = "PASSAGEIRO"
//                ),
//                trajeto = TrajetoDto(
//                    pontoPartida = LocalidadeDto(
//                        Coordenadas(
//                            latitude = 0.0,
//                            longitude = 0.0
//                        ),
//                        "04244000",
//                        "SP",
//                        "São Paulo",
//                        "São João Clímaco",
//                        "Estrada das Lágrimas",
//                        300
//                    ),
//                    pontoChegada = LocalidadeDto(
//                        Coordenadas(
//                            latitude = 0.0,
//                            longitude = 0.0
//                        ),
//                        "12045000",
//                        "SP",
//                        "Taubaté",
//                        "Centro",
//                        "Avenida Independência",
//                        680
//                    )
//                )
//            ),
//            ViagemListagemDto(
//                id = 2,
//                capacidadePassageiros = 3,
//                apenasMulheres = false,
//                data = LocalDate.of(2024, 9, 25),
//                horarioSaida = LocalTime.of(10, 30),
//                horarioChegada = LocalTime.of(12, 30),
//                preco = 30.0,
//                statusViagem = StatusViagem.PENDENTE,
//                motorista = UsuarioSimplesListagemDto(
//                    id = 1,
//                    nome = "Ewerton Lima",
//                    notaGeral = 4.0,
//                    fotoUrl = "foto",
//                    perfil = "PASSAGEIRO"
//                ),
//                trajeto = TrajetoDto(
//                    pontoPartida = LocalidadeDto(
//                        Coordenadas(
//                            latitude = 0.0,
//                            longitude = 0.0
//                        ),
//                        "04244000",
//                        "SP",
//                        "São Paulo",
//                        "São João Clímaco",
//                        "Estrada das Lágrimas",
//                        300
//                    ),
//                    pontoChegada = LocalidadeDto(
//                        Coordenadas(
//                            latitude = 0.0,
//                            longitude = 0.0
//                        ),
//                        "12045000",
//                        "SP",
//                        "Taubaté",
//                        "Centro",
//                        "Avenida Independência",
//                        680
//                    )
//                )
//            ),
//            ViagemListagemDto(
//                id = 3,
//                capacidadePassageiros = 4,
//                apenasMulheres = false,
//                data = LocalDate.of(2024, 10, 21),
//                horarioSaida = LocalTime.of(17, 30),
//                horarioChegada = LocalTime.of(20, 0),
//                preco = 30.0,
//                statusViagem = StatusViagem.PENDENTE,
//                motorista = UsuarioSimplesListagemDto(
//                    id = 1,
//                    nome = "Ewerton Lima",
//                    notaGeral = 4.0,
//                    fotoUrl = "foto",
//                    perfil = "PASSAGEIRO"
//                ),
//                trajeto = TrajetoDto(
//                    pontoPartida = LocalidadeDto(
//                        Coordenadas(
//                            latitude = 0.0,
//                            longitude = 0.0
//                        ),
//                        "04244000",
//                        "SP",
//                        "São Paulo",
//                        "São João Clímaco",
//                        "Estrada das Lágrimas",
//                        300
//                    ),
//                    pontoChegada = LocalidadeDto(
//                        Coordenadas(
//                            latitude = 0.0,
//                            longitude = 0.0
//                        ),
//                        "12045000",
//                        "SP",
//                        "Taubaté",
//                        "Centro",
//                        "Avenida Independência",
//                        680
//                    )
//                )
//            ),
//            ViagemListagemDto(
//                id = 4,
//                capacidadePassageiros = 4,
//                apenasMulheres = false,
//                data = LocalDate.of(2024, 10, 15),
//                horarioSaida = LocalTime.of(17, 30),
//                horarioChegada = LocalTime.of(20, 0),
//                preco = 30.0,
//                statusViagem = StatusViagem.PENDENTE,
//                motorista = UsuarioSimplesListagemDto(
//                    id = 1,
//                    nome = "Ewerton Lima",
//                    notaGeral = 4.0,
//                    fotoUrl = "foto",
//                    perfil = "PASSAGEIRO"
//                ),
//                trajeto = TrajetoDto(
//                    pontoPartida = LocalidadeDto(
//                        Coordenadas(
//                            latitude = 0.0,
//                            longitude = 0.0
//                        ),
//                        "04244000",
//                        "SP",
//                        "São Paulo",
//                        "São João Clímaco",
//                        "Estrada das Lágrimas",
//                        300
//                    ),
//                    pontoChegada = LocalidadeDto(
//                        Coordenadas(
//                            latitude = 0.0,
//                            longitude = 0.0
//                        ),
//                        "12045000",
//                        "SP",
//                        "Taubaté",
//                        "Centro",
//                        "Avenida Independência",
//                        680
//                    )
//                )
//            )
//        )
    )

    var viagensFiltradas = MutableStateFlow(viagens.value)

    val isExpanded = MutableStateFlow(false)

    val currentFilterOption = MutableStateFlow("Filtrar")

    init {
        viewModelScope.launch {
            perfilUser.update { dataStoreManager.getPerfilUser() ?: "" }
        }
        _getViagens()
    }

    private fun _getViagens() {
        viewModelScope.launch {
            try {
                val idUser = dataStoreManager.getIdUser()
                val response = repository.findAllByUsuarioId(idUser!!)
                if (response.isSuccessful) {
                    Log.i(
                        "historicoViagens",
                        "Sucesso ao buscar viagens do usuário: ${response.body()}"
                    )
                    if (response.code() == 200) {
                        viagens.update { response.body() }
                    }
                } else {
                    Log.e(
                        "historicoViagens",
                        "Erro ao buscar viagens do usuário: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "historicoViagens",
                    "Erro ao buscar viagens do usuário: ${e.message}"
                )
            }
        }
    }

    fun onDismissRequest() {
        isExpanded.update { false }
    }

    fun expandDropdownMenu() {
        isExpanded.update { true }
    }

    fun onMenuItemClick(novoValor: String, dataSelecionada: LocalDate?) {
        currentFilterOption.update { novoValor }

        if (dataSelecionada != null) {
            viagensFiltradas.update { _filterViagens(dataSelecionada) }
        } else {
            viagensFiltradas.value = viagens.value
        }

        onDismissRequest()
    }

    private fun _filterViagens(data: LocalDate): List<ViagemListagemDto> {
        val viagensFiltradas = viagens.value!!.filter { viagem -> !viagem.dataInDate.isBefore(data) }

        return viagensFiltradas
    }
}