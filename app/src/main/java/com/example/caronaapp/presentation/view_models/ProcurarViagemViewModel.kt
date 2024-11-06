package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun onChangeEvent(field: ProcurarViagemField) {
        when (field) {
            is ProcurarViagemField.PontoPartida -> {
                procurarViagemState.update { it.copy(pontoPartida = field.value) }
                searchAddress(field.value)
            }

            is ProcurarViagemField.PontoChegada -> {
                procurarViagemState.update { it.copy(pontoChegada = field.value) }
            }

            is ProcurarViagemField.Data -> {
                procurarViagemState.update { it.copy(data = field.value) }
            }
        }
    }

    fun searchAddress(address: String) {
        viewModelScope.launch {
            try {
                val API_KEY = "AIzaSyBCgrMgCudI7Jcc3xd8DDZAlqb8_7lWvF4"
                val response = googleMapsRepository.getGeocode(address, API_KEY)
                if (response.isSuccessful) {
                    Log.i("geocoder", "Sucesso no Geocode: ${response.body()}")
                    Log.i("geocoder", "_________________________________________________________")
                    procurarViagemState.update {
                        it.copy(
                            enderecoPontoPartida = if (response.body() != null) response.body()!!.results[0].formatted_address
                            else ""
                        )
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

    fun procurarViagens() {}
}