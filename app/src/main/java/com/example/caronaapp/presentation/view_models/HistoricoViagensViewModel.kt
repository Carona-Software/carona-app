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

    val isLoadingScreen = MutableStateFlow(true)
    val perfilUser = MutableStateFlow("")
    val viagens = MutableStateFlow<List<ViagemListagemDto>?>(null)
    var viagensFiltradas = MutableStateFlow<List<ViagemListagemDto>>(emptyList())
    val isExpanded = MutableStateFlow(false)
    val currentFilterOption = MutableStateFlow("Filtrar")

    init {
        getViagens()
    }

    private fun getViagens() {
        viewModelScope.launch {
            try {
                perfilUser.update { dataStoreManager.getPerfilUser() ?: "" }

                val idUser = dataStoreManager.getIdUser()
                val response = repository.findAllByUsuarioId(idUser!!)

                if (response.isSuccessful) {
                    Log.i(
                        "historicoViagens",
                        "Sucesso ao buscar viagens do usuário: ${response.body()}"
                    )
                    if (response.code() == 200) {
                        viagens.update { response.body() }
                        viagensFiltradas.update { response.body() ?: emptyList() }
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
            } finally {
                isLoadingScreen.update { false }
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
            viagensFiltradas.update { filterViagens(dataSelecionada) }
        } else {
            viagensFiltradas.update { viagens.value ?: emptyList() }
        }

        onDismissRequest()
    }

    private fun filterViagens(data: LocalDate): List<ViagemListagemDto> {
        val viagensFiltradas = viagens.value!!.filter { viagem -> !viagem.dataInDate.isBefore(data) }

        return viagensFiltradas
    }
}