package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.google_maps.GeocodeResponse
import com.example.caronaapp.data.dto.viagem.Coordenadas
import com.example.caronaapp.data.dto.viagem.ViagemProcuraDto
import com.example.caronaapp.data.repositories.GoogleMapsRepositoryImpl
import com.example.caronaapp.data.repositories.ViagemRepositoryImpl
import com.example.caronaapp.presentation.screens.procurar_viagem.ProcurarViagemField
import com.example.caronaapp.presentation.screens.procurar_viagem.ProcurarViagemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProcurarViagemViewModel(
    val viagemRepository: ViagemRepositoryImpl,
    val googleMapsRepository: GoogleMapsRepositoryImpl
) : ViewModel() {

    val viagemProcuraDto = MutableStateFlow(ViagemProcuraDto())
    val procurarViagemState = MutableStateFlow(ProcurarViagemState())

    val isDropdownPartidaOpened = MutableStateFlow(false)
    val isDropdownChegadaOpened = MutableStateFlow(false)

    fun onChangeEvent(field: ProcurarViagemField) {
        when (field) {
            is ProcurarViagemField.PontoPartida -> {
                procurarViagemState.update { it.copy(pontoPartida = field.value) }
                searchAddress(field.value, true)
            }

            is ProcurarViagemField.PontoChegada -> {
                procurarViagemState.update { it.copy(pontoChegada = field.value) }
                searchAddress(field.value, false)
            }

            is ProcurarViagemField.Data -> {
                procurarViagemState.update { it.copy(data = field.value) }
            }
        }
    }

    fun searchAddress(address: String, isPartida: Boolean) {
        viewModelScope.launch {
            try {
                val API_KEY = "AIzaSyBCgrMgCudI7Jcc3xd8DDZAlqb8_7lWvF4"
                val response = googleMapsRepository.getGeocode(address, API_KEY)
                if (response.isSuccessful) {
                    Log.i("geocoder", "Sucesso no Geocode: ${response.body()}")
                    Log.i("geocoder", "Endereços retornados: ${response.body()!!.results.size}")
                    Log.i("geocoder", "_________________________________________________________")

                    if (isPartida) {
                        isDropdownPartidaOpened.update { true }
                        procurarViagemState.update {
                            it.copy(
                                resultsPontoPartida = response.body()!!.results
                            )
                        }
                    } else {
                        isDropdownChegadaOpened.update { true }
                        procurarViagemState.update {
                            it.copy(
                                resultsPontoChegada = response.body()!!.results
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

    fun onDismissDropdownPartida() {
        isDropdownPartidaOpened.update { false }
    }

    fun onDismissDropdownChegada() {
        isDropdownChegadaOpened.update { false }
    }

    fun onDropDownPartidaClick(pontoPartida: GeocodeResponse.Result) {
        viagemProcuraDto.update {
            it.copy(
                pontoPartida = Coordenadas(
                    latitude = pontoPartida.geometry.location.latitude,
                    longitude = pontoPartida.geometry.location.longitude
                )
            )
        }

        procurarViagemState.update {
            it.copy(pontoPartida = pontoPartida.formatted_address)
        }

        isDropdownPartidaOpened.update { false }

        Log.i("geocoder", "Opção escolhida")
        Log.i("geocoder", "Endereço: ${pontoPartida.formatted_address}")
        Log.i("geocoder", "Coordenadas: ${pontoPartida.geometry.location}")
        Log.i("geocoder", "===============================================================")
    }

    fun onDropDownChegadaClick(pontoChegada: GeocodeResponse.Result) {
        viagemProcuraDto.update {
            it.copy(
                pontoChegada = Coordenadas(
                    latitude = pontoChegada.geometry.location.latitude,
                    longitude = pontoChegada.geometry.location.longitude
                )
            )
        }

        procurarViagemState.update {
            it.copy(pontoChegada = pontoChegada.formatted_address)
        }

        isDropdownChegadaOpened.update { false }
    }

    fun procurarViagens() {}
}