package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.google_maps.GeocodeResponse
import com.example.caronaapp.data.dto.viagem.Coordenadas
import com.example.caronaapp.data.dto.viagem.ViagemProcuraDto
import com.example.caronaapp.data.repositories.GoogleMapsRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.presentation.screens.procurar_viagem.ProcurarViagemField
import com.example.caronaapp.presentation.screens.procurar_viagem.ProcurarViagemState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

class ProcurarViagemViewModel(
    private val googleMapsRepository: GoogleMapsRepositoryImpl,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val perfilUser = MutableStateFlow("")

    init {
        viewModelScope.launch {
            perfilUser.update { dataStoreManager.getPerfilUser() ?: "" }
        }
    }

    val viagemProcuraDto = MutableStateFlow(ViagemProcuraDto())
    val state = MutableStateFlow(ProcurarViagemState())

    val isDropdownPartidaOpened = MutableStateFlow(false)
    val isDropdownChegadaOpened = MutableStateFlow(false)

    // Tentativa com debounce
    private val _pontoPartida = MutableStateFlow("")
    val pontoPartida = _pontoPartida.asStateFlow()

    private val _pontoChegada = MutableStateFlow("")
    val pontoChegada = _pontoChegada.asStateFlow()

    @OptIn(FlowPreview::class)
    val pontoPartidaResults = pontoPartida
        .debounce(700)
        .filter { it.isNotEmpty() }
        .distinctUntilChanged() // Ignore valores iguais consecutivos
        .onEach { query ->
            searchAddress(query, true)
        }
        .launchIn(viewModelScope)

    @OptIn(FlowPreview::class)
    val pontoChegadaResults = pontoChegada
        .debounce(700)
        .filter { it.isNotEmpty() }
        .distinctUntilChanged() // Ignore valores iguais consecutivos
        .onEach { query ->
            searchAddress(query, false)
        }
        .launchIn(viewModelScope)

    fun onChangeEvent(field: ProcurarViagemField) {
        when (field) {
            is ProcurarViagemField.PontoPartida -> {
                state.update { it.copy(pontoPartida = field.value) }
                _pontoPartida.update { field.value }
            }

            is ProcurarViagemField.PontoChegada -> {
                state.update { it.copy(pontoChegada = field.value) }
                _pontoChegada.update { field.value }
            }

            is ProcurarViagemField.Data -> {
                state.update { it.copy(data = field.value) }
                viagemProcuraDto.update {
                    it.copy(
                        data = field.value.format(
                            DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd"
                            )
                        )
                    )
                }
            }
        }
    }

    private fun searchAddress(address: String, isPartida: Boolean) {
        viewModelScope.launch {
            try {
                val response = googleMapsRepository.getGeocode(address)

                if (response.isSuccessful) {
                    Log.i("geocoder", "Sucesso no Geocode: ${response.body()}")
                    Log.i("geocoder", "Endereços retornados: ${response.body()!!.results.size}")
                    Log.i("geocoder", "_________________________________________________________")

                    if (isPartida) {
                        isDropdownPartidaOpened.update { true }
                        state.update {
                            it.copy(
                                resultsPontoPartida = response.body()!!.results
                            )
                        }
                    } else {
                        isDropdownChegadaOpened.update { true }
                        state.update {
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

        state.update {
            it.copy(pontoPartida = pontoPartida.formatted_address)
        }
        isDropdownPartidaOpened.update { false }
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

        state.update {
            it.copy(pontoChegada = pontoChegada.formatted_address)
        }
        isDropdownChegadaOpened.update { false }
    }
}