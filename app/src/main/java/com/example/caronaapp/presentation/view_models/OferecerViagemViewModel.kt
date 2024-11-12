package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import com.example.caronaapp.data.dto.viagem.Coordenadas
import com.example.caronaapp.data.dto.viagem.ViagemCriacaoDto
import com.example.caronaapp.data.repositories.CarroRepositoryImpl
import com.example.caronaapp.data.repositories.GoogleMapsRepositoryImpl
import com.example.caronaapp.data.repositories.ViagemRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.presentation.screens.oferecer_viagem.OferecerViagemEnderecoField
import com.example.caronaapp.presentation.screens.oferecer_viagem.OferecerViagemField
import com.example.caronaapp.presentation.screens.oferecer_viagem.OferecerViagemState
import com.example.caronaapp.utils.formatDate
import com.example.caronaapp.utils.formatTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

class OferecerViagemViewModel(
    private val viagemRepository: ViagemRepositoryImpl,
    private val carroRepository: CarroRepositoryImpl,
    private val dataStoreManager: DataStoreManager,
    private val googleMapsRepository: GoogleMapsRepositoryImpl
) : ViewModel() {
    val perfilUser = MutableStateFlow("")

    init {
        viewModelScope.launch {
            perfilUser.update { dataStoreManager.getPerfilUser() ?: "" }
        }
        getCarrosUser()
    }

    private val viagemData = MutableStateFlow(ViagemCriacaoDto())
    val state = MutableStateFlow(OferecerViagemState())

    val etapaAtual = MutableStateFlow(1)


    val carros = MutableStateFlow<List<CarroListagemDto>?>(
        null
//        listOf(
//            CarroListagemDto(
//                id = 1,
//                marca = "Fiat",
//                modelo = "Mobi",
//                placa = "GJB5A12",
//                cor = "Preto",
//                motorista = CarroListagemDto.MotoristaListagemDto(
//                    id = 1,
//                    nome = "Gustavo Medeiros"
//                )
//            ),
//            CarroListagemDto(
//                id = 2,
//                marca = "Chevrolet",
//                modelo = "Onix",
//                placa = "YAB7L04",
//                cor = "Vinho",
//                motorista = CarroListagemDto.MotoristaListagemDto(
//                    id = 1,
//                    nome = "Gustavo Medeiros"
//                )
//            ),
//            CarroListagemDto(
//                id = 3,
//                marca = "Honda",
//                modelo = "Fit",
//                placa = "AOC3G83",
//                cor = "Prata",
//                motorista = CarroListagemDto.MotoristaListagemDto(
//                    id = 1,
//                    nome = "Gustavo Medeiros"
//                )
//            )
//        )
    )

    private fun getCarrosUser() {
        viewModelScope.launch {
            try {
                val idUser = dataStoreManager.getIdUser()
                val response = carroRepository.findByUsuarioId(idUser!!)

                if (response.isSuccessful) {
                    Log.i("oferecerCarona", "Sucesso ao buscar carros do usuário")
                    if (response.code() == 200) {
                        carros.update { response.body() }
                    }
                } else {
                    Log.e(
                        "oferecerCarona",
                        "Erro ao buscar carros do usuário: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "oferecerCarona",
                    "Exception -> erro ao buscar carros do usuário: ${e.printStackTrace()}"
                )
            }
        }
    }

    fun onChangeEvent(field: OferecerViagemField) {
        when (field) {
            is OferecerViagemField.PontoPartida -> {
                state.update {
                    it.copy(pontoPartida = field.value)
                }
                searchAddress(field.value, true)
            }

            is OferecerViagemField.PontoDestino -> {
                state.update {
                    it.copy(pontoDestino = field.value)
                }
                searchAddress(field.value, false)
            }

            is OferecerViagemField.Data -> {
                viagemData.update {
                    it.copy(data = field.value.format(DateTimeFormatter.ISO_LOCAL_DATE))
                }

                state.update {
                    it.copy(data = formatDate(field.value))
                }
            }

            is OferecerViagemField.HorarioPartida -> {
                viagemData.update {
                    it.copy(horarioPartida = field.value.format(DateTimeFormatter.ISO_LOCAL_TIME))
                }

                state.update {
                    it.copy(hora = formatTime(field.value))
                }
            }

            is OferecerViagemField.CapacidadePassageiros -> {
                viagemData.update {
                    it.copy(capacidadePassageiros = field.value)
                }

                state.update {
                    it.copy(capacidadePassageiros = field.value)
                }
            }

            is OferecerViagemField.Preco -> {
                viagemData.update {
                    it.copy(preco = field.value)
                }

                state.update {
                    it.copy(preco = field.value)
                }
            }

            is OferecerViagemField.ApenasMulheres -> {
                viagemData.update {
                    it.copy(apenasMulheres = field.value)
                }

                state.update {
                    it.copy(apenasMulheres = field.value)
                }
            }

            is OferecerViagemField.Carro -> {
                viagemData.update {
                    it.copy(carroId = field.value.id)
                }

                state.update {
                    it.copy(
                        carro = "${field.value.marca} ${field.value.modelo}",
                        isCarrosDropdownExpanded = false
                    )
                }
            }
        }
    }

    private fun searchAddress(address: String, isPartida: Boolean) {
        viewModelScope.launch {
            try {
                val API_KEY = "AIzaSyBCgrMgCudI7Jcc3xd8DDZAlqb8_7lWvF4"
                val response = googleMapsRepository.getGeocode(address, API_KEY)
                if (response.isSuccessful) {
                    Log.i("geocoder", "Sucesso no Geocode: ${response.body()}")
                    Log.i("geocoder", "Endereços retornados: ${response.body()!!.results.size}")
                    Log.i("geocoder", "_________________________________________________________")

                    if (isPartida) {
                        state.update {
                            it.copy(
                                isPartidaDropdownExpanded = true,
                                resultsPontoPartida = response.body()!!.results
                            )
                        }
                    } else {
                        state.update {
                            it.copy(
                                isDestinoDropdownExpanded = true,
                                resultsPontoDestino = response.body()!!.results
                            )
                        }
                    }
                } else {
                    Log.e("geocoder", "Erro no Geocode: ${response.errorBody()}")
                    Log.e("geocoder", "_________________________________________________________")

                }
            } catch (e: Exception) {
                Log.e("geocoder", "Exception -> erro ao realizar geocode: ${e.printStackTrace()}")
                Log.e("geocoder", "Erro: ${e.message}")
                Log.e("geocoder", "_________________________________________________________")
            }
        }
    }

    fun onEnderecoClick(field: OferecerViagemEnderecoField) {
        when (field) {
            is OferecerViagemEnderecoField.PontoPartida -> {
                viagemData.update {
                    it.copy(
                        pontoPartida = Coordenadas(
                            field.value.geometry.location.latitude,
                            field.value.geometry.location.longitude,
                        )
                    )
                }

                state.update {
                    it.copy(
                        pontoPartida = field.value.formatted_address,
                        isPartidaDropdownExpanded = false
                    )
                }
            }

            is OferecerViagemEnderecoField.PontoDestino -> {
                viagemData.update {
                    it.copy(
                        pontoDestino = Coordenadas(
                            field.value.geometry.location.latitude,
                            field.value.geometry.location.longitude,
                        )
                    )
                }

                state.update {
                    it.copy(
                        pontoDestino = field.value.formatted_address,
                        isDestinoDropdownExpanded = false
                    )
                }

            }
        }
    }

    fun handleNextClick() {
        when (etapaAtual.value) {
            1 -> etapaAtual.update { 2 }
            2 -> etapaAtual.update { 3 }
            3 -> saveViagem()
        }
    }

    fun handleBackClick() {
        when (etapaAtual.value) {
            2 -> etapaAtual.update { 1 }
            3 -> etapaAtual.update { 2 }
        }
    }

    fun onCarrosDropdownClick() {
        state.update { it.copy(isCarrosDropdownExpanded = true) }
    }

    fun onCarrosDismissRequest() {
        state.update { it.copy(isCarrosDropdownExpanded = false) }
    }

    private fun saveViagem() {
        Log.i("oferecerCarona", "ViagemCriacaoDto: ${viagemData.value}")
//        viewModelScope.launch {
//            try {
//                viagemData.update { it.copy(motoristaId = dataStoreManager.getIdUser()!!) }
//
//                val response = viagemRepository.save(viagemData.value)
//
//                if (response.isSuccessful) {
//                    Log.i(
//                        "oferecerViagem",
//                        "Sucesso ao salvar viagem: ${response.body()}"
//                    )
//                } else {
//                    Log.e(
//                        "oferecerViagem",
//                        "Erro ao salvar viagem: ${response.errorBody()}"
//                    )
//                }
//            } catch (e: Exception) {
//                Log.e(
//                    "oferecerViagem",
//                    "Exception -> erro ao salvar viagem: ${e.printStackTrace()}"
//                )
//            }
//        }
    }
}