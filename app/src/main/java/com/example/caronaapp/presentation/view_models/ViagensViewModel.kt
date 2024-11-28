package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.usuario.UsuarioSimplesListagemDto
import com.example.caronaapp.data.dto.viagem.Coordenadas
import com.example.caronaapp.data.dto.viagem.ViagemListagemDto
import com.example.caronaapp.data.dto.viagem.ViagemProcuraDto
import com.example.caronaapp.data.repositories.MapboxRepositoryImpl
import com.example.caronaapp.data.repositories.ViagemRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.presentation.screens.viagens.ViagensField
import com.example.caronaapp.presentation.screens.viagens.ViagensState
import com.example.caronaapp.utils.functions.formatDate
import com.example.caronaapp.utils.functions.isUrlFotoUserValida
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class ViagensViewModel(
    private val viagemRepository: ViagemRepositoryImpl,
    private val dataStoreManager: DataStoreManager,
    private val mapboxRepository: MapboxRepositoryImpl
) : ViewModel() {

    val isLoadingScreen = MutableStateFlow(true)
    val idUser = MutableStateFlow(0)
    val perfilUser = MutableStateFlow("")
    private val viagemProcuraDto = MutableStateFlow(ViagemProcuraDto())
    val viagens = MutableStateFlow<List<ViagemListagemDto>?>(null)
    val state = MutableStateFlow(ViagensState())

    init {
        viewModelScope.launch {
            perfilUser.update { dataStoreManager.getPerfilUser() ?: "" }
            idUser.update { dataStoreManager.getIdUser() ?: 0 }
        }
    }

    fun setViagemDto(
        viagem: ViagemProcuraDto,
        pontoPartida: String,
        pontoDestino: String
    ) {
        viagemProcuraDto.update { viagem }
        state.update {
            it.copy(
                pontoPartida = pontoPartida,
                pontoDestino = pontoDestino,
                data = transformData(LocalDate.parse(viagem.data))
            )
        }
        procurarViagens()
    }

    private fun transformData(data: LocalDate): String {
        return if (data.isEqual(LocalDate.now())) {
            "Hoje"
        } else if (data.isEqual(LocalDate.now().plusDays(1))) {
            "Amanhã"
        } else {
            formatDate(data)
        }
    }

    private fun procurarViagens() {
        viewModelScope.launch {
            try {
                Log.i(
                    "procurarViagem",
                    "ViagemProcuraDto: ${viagemProcuraDto.value}"
                )
                val response = viagemRepository.findAll(viagemProcuraDto.value)

                if (response.isSuccessful) {
                    Log.i(
                        "procurarViagem",
                        "Sucesso ao buscar viagens: ${response.body()}"
                    )

                    val viagensComDistancia = response.body()?.content?.map { viagem ->
                        val distanciaPartida = calcularDistancia(
                            coordenadasPartida = viagemProcuraDto.value.pontoPartida!!,
                            coordenadasDestino = viagem.trajeto.pontoPartida.coordenadas
                        )
                        val distanciaDestino = calcularDistancia(
                            coordenadasPartida = viagemProcuraDto.value.pontoChegada!!,
                            coordenadasDestino = viagem.trajeto.pontoChegada.coordenadas
                        )

                        viagem.copy(
                            distanciaPartida = distanciaPartida,
                            distanciaDestino = distanciaDestino,
                            motorista = validarFotoMotorista(viagem.motorista)
                        )
                    }
                    viagens.update { viagensComDistancia }
                } else {
                    Log.e(
                        "procurarViagem",
                        "Erro ao buscar viagens: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "procurarViagem",
                    "Exception -> erro ao buscar viagens: ${e.printStackTrace()}"
                )
            } finally {
                isLoadingScreen.update { false }
            }
        }
    }

    fun showBottomSheet() {
        state.update { it.copy(showBottomSheet = true) }
    }

    fun onDismissBottomSheet() {
        state.update { it.copy(showBottomSheet = false) }
    }

    fun handleOnChange(field: ViagensField) {
        when (field) {
            is ViagensField.CapacidadePassageiros -> {
                viagemProcuraDto.update {
                    it.copy(capacidadePassageiros = field.value)
                }
                state.update {
                    it.copy(capacidadePassageiros = field.value)
                }
            }

            is ViagensField.PrecoMinimo -> {
                viagemProcuraDto.update {
                    it.copy(precoMinimo = field.value)
                }
                state.update {
                    it.copy(precoMinimo = field.value)
                }
            }

            is ViagensField.PrecoMaximo -> {
                viagemProcuraDto.update {
                    it.copy(precoMaximo = field.value)
                }
                state.update {
                    it.copy(precoMaximo = field.value)
                }
            }

            is ViagensField.ApenasMulheres -> {
                viagemProcuraDto.update {
                    it.copy(apenasMulheres = field.value)
                }
                state.update {
                    it.copy(apenasMulheres = field.value)
                }
            }
        }
    }

    fun handleFilterViagens() {
        onDismissBottomSheet()
        isLoadingScreen.update { true }
        procurarViagens()
    }

    fun clearFilters() {
        viagemProcuraDto.update {
            it.copy(
                capacidadePassageiros = null,
                precoMinimo = null,
                precoMaximo = null,
                apenasMulheres = null
            )
        }
    }

    private suspend fun calcularDistancia(
        coordenadasPartida: Coordenadas,
        coordenadasDestino: Coordenadas
    ): Double {
        try {
            val response = mapboxRepository.getDistanceBetweenPlaces(
                latitudePartida = coordenadasPartida.latitude,
                longitudePartida = coordenadasPartida.longitude,
                latitudeDestino = coordenadasDestino.latitude,
                longitudeDestino = coordenadasDestino.longitude,
            )

            return if (response.isSuccessful) {
                response.body()?.routes?.firstOrNull()?.distance?.div(1000) ?: 0.0
            } else {
                0.0
            }
        } catch (e: Exception) {
            Log.e(
                "detalhesViagem",
                "Exception -> erro ao calcular distância: ${e.printStackTrace()}"
            )
            return 0.0
        }
    }

    private suspend fun validarFotoMotorista(motorista: UsuarioSimplesListagemDto): UsuarioSimplesListagemDto {
        return motorista.copy(
            isFotoValida = isUrlFotoUserValida(motorista.fotoUrl)
        )
    }
}