package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.carro.CarroCriacaoDto
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import com.example.caronaapp.data.dto.vpic_carros.VpicCarrosMarcaResponse
import com.example.caronaapp.data.repositories.CarroRepositoryImpl
import com.example.caronaapp.data.repositories.VpicCarrosRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.presentation.screens.carros.CarroField
import com.example.caronaapp.presentation.screens.carros.CarrosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CarrosViewModel(
    private val carrosRepository: CarroRepositoryImpl,
    private val dataStoreManager: DataStoreManager,
    private val vpicCarrosRepository: VpicCarrosRepositoryImpl
) : ViewModel() {

    val isLoadingScreen = MutableStateFlow(true)
    val state = MutableStateFlow(CarrosUiState())

    init {
        getCarrosUser()
    }

    private fun getCarrosUser() {
        viewModelScope.launch {
            try {
                val idUser = dataStoreManager.getIdUser()
                val response = carrosRepository.findByUsuarioId(idUser!!)

                if (response.isSuccessful) {
                    Log.i("carros", "Sucesso ao buscar carros do usuário: ${response.body()}")
                    if (response.code() == 200) {
                        state.update { it.copy(carros = response.body()) }
                    }
                } else {
                    Log.e(
                        "carros",
                        "Erro ao buscar carros do usuário: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "carros",
                    "Exception -> erro ao buscar carros do usuário: ${e.printStackTrace()}"
                )
            } finally {
                isLoadingScreen.update { false }
            }
        }
    }

    fun onDeleteClick(carro: CarroListagemDto) {
        state.update {
            it.copy(
                isDeleteDialogOpened = true,
                deleteCarroData = carro
            )
        }
    }

    fun onEditClick(carro: CarroListagemDto) {
        state.update {
            it.copy(
                isEditDialogOpened = true,
                editCarroData = CarroCriacaoDto(
                    id = carro.id,
                    marca = carro.marca,
                    modelo = carro.modelo,
                    placa = carro.placa,
                    cor = carro.cor
                )
            )
        }
    }

    fun onCreateClick() {
        viewModelScope.launch {
            try {
                state.update { it.copy(isCreateDialogOpened = true) }

                val response = vpicCarrosRepository.getMarcasCarro()

                if (response.isSuccessful) {
                    Log.i("carros", "Sucesso ao buscar marcas de carro: ${response.body()}")
                    state.update {
                        it.copy(
                            marcasCarroData = organizeAndCapitalizeList(
                                response.body()?.results ?: emptyList()
                            )
                        )
                    }
                } else {
                    Log.e("carros", "Erro ao buscar marcas de carro: ${response.errorBody()}")
                }

            } catch (e: Exception) {
                Log.e("carros", "Exception -> erro ao buscar marcas de carro: ${e.message}")
            }
        }
    }

    fun onChangeEvent(field: CarroField, isEditCarro: Boolean) {
        if (isEditCarro) {
            when (field) {
                is CarroField.Marca -> {
                    state.update { currentState ->
                        currentState.copy(
                            editCarroData = currentState.editCarroData.copy(
                                marca = field.value,
                                modelo = ""
                            ),
                            isMarcasDropdownExpanded = false
                        )
                    }
                    getModelosCarroByMarca(field.value)
                }

                is CarroField.Modelo -> {
                    state.update { currentState ->
                        currentState.copy(
                            editCarroData = currentState.editCarroData.copy(
                                modelo = field.value
                            ),
                            isModelosDropdownExpanded = false
                        )
                    }
                }

                is CarroField.Placa -> {
                    state.update { currentState ->
                        currentState.copy(
                            editCarroData = currentState.editCarroData.copy(
                                placa = field.value
                            )
                        )
                    }
                }

                is CarroField.Cor -> {
                    state.update { currentState ->
                        currentState.copy(
                            editCarroData = currentState.editCarroData.copy(
                                cor = field.value
                            ),
                            isCoresDropdownExpanded = false
                        )
                    }
                }
            }
        } else {
            when (field) {
                is CarroField.Marca -> {
                    state.update { currentState ->
                        currentState.copy(
                            createCarroData = currentState.createCarroData.copy(
                                marca = field.value,
                                modelo = ""
                            ),
                            isMarcasDropdownExpanded = false
                        )
                    }
                    getModelosCarroByMarca(field.value)
                }

                is CarroField.Modelo -> {
                    state.update { currentState ->
                        currentState.copy(
                            createCarroData = currentState.createCarroData.copy(
                                modelo = field.value
                            ),
                            isModelosDropdownExpanded = false
                        )
                    }
                }

                is CarroField.Placa -> {
                    state.update { currentState ->
                        currentState.copy(
                            createCarroData = currentState.createCarroData.copy(
                                placa = field.value
                            )
                        )
                    }
                }

                is CarroField.Cor -> {
                    state.update { currentState ->
                        currentState.copy(
                            createCarroData = currentState.createCarroData.copy(
                                cor = field.value
                            ),
                            isCoresDropdownExpanded = false
                        )
                    }
                }
            }
        }
    }

    fun handleDeleteCarro() {
        viewModelScope.launch {
            try {
                val response = carrosRepository.delete(state.value.deleteCarroData!!.id)

                if (response.isSuccessful) {
                    Log.i("carros", "Sucesso ao deletar carro")

                    // atualizando lista de carros
                    val updatedCarrosList =
                        state.value.carros!!.filter { carro -> carro.id != state.value.deleteCarroData!!.id }

                    state.update {
                        it.copy(
                            carros = updatedCarrosList.ifEmpty { null },
                            deleteCarroData = null,
                            isDeleteDialogOpened = false,
                            isSuccess = true,
                            successMessage = "Carro excluído com sucesso"
                        )
                    }
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Não foi possível excluir o carro"
                        )
                    }
                    Log.e("carros", "Erro ao deletar carro: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Não foi possível excluir o carro"
                    )
                }
                Log.e("carros", "Erro ao deletar carro: ${e.message}")
            }
        }
    }

    fun handleCreateCarro() {
        viewModelScope.launch {
            try {
                state.update {
                    it.copy(
                        createCarroData = it.createCarroData.copy(
                            fkUsuario = dataStoreManager.getIdUser() ?: 0
                        )
                    )
                }
                val response = carrosRepository.save(state.value.createCarroData)

                if (response.isSuccessful) {
                    // atualizando lista de carros
                    getCarrosUser()
                    Log.i("carros", "Sucesso ao cadastrar carro: ${response.body()}")

                    state.update {
                        it.copy(
                            isCreateDialogOpened = false,
                            isSuccess = true,
                            successMessage = "Carro cadastrado com sucesso",
                            createCarroData = CarroCriacaoDto()
                        )
                    }
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Não foi possível cadastrar o carro"
                        )
                    }
                    Log.e("carros", "Erro ao cadastrar carro: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Não foi possível cadastrar o carro"
                    )
                }
                Log.e("carros", "Erro ao cadastrar carro: ${e.message}")
            }
        }
    }

    fun handleEditCarro() {
        viewModelScope.launch {
            try {
                state.update {
                    it.copy(
                        editCarroData = it.editCarroData.copy(
                            fkUsuario = dataStoreManager.getIdUser() ?: 0
                        )
                    )
                }

                val response = carrosRepository.edit(
                    state.value.editCarroData.id!!,
                    state.value.editCarroData
                )

                if (response.isSuccessful) {
                    // atualizando lista de carros
                    getCarrosUser()

                    Log.i("carros", "Sucesso ao editar carro: ${response.body()}")

                    state.update {
                        it.copy(
                            isEditDialogOpened = false,
                            isSuccess = true,
                            successMessage = "Carro editado com sucesso",
                            editCarroData = CarroCriacaoDto()
                        )
                    }
                } else {
                    state.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Não foi possível editar o carro"
                        )
                    }
                    Log.e("carros", "Erro ao editar carro: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                state.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Não foi possível editar o carro"
                    )
                }
                Log.e("carros", "Erro ao cadastrar carro: ${e.message}")
            }
        }
    }

    fun setIsSuccessToFalse() {
        state.update {
            it.copy(
                isSuccess = false,
                successMessage = ""
            )
        }
    }

    fun onMarcasDropdownClick() {
        state.update { it.copy(isMarcasDropdownExpanded = true) }
    }

    fun onModelosDropdownClick() {
        state.update { it.copy(isModelosDropdownExpanded = true) }
    }

    fun onCoresDropdownClick() {
        state.update { it.copy(isCoresDropdownExpanded = true) }
    }

    fun onDismissDeleteDialog() {
        state.update {
            it.copy(
                isDeleteDialogOpened = false,
                deleteCarroData = null
            )
        }
    }

    fun onDismissCreateDialog() {
        state.update {
            it.copy(
                isCreateDialogOpened = false,
                createCarroData = CarroCriacaoDto()
            )
        }
    }

    fun onDismissEditDialog() {
        state.update {
            it.copy(
                isEditDialogOpened = false,
                editCarroData = CarroCriacaoDto()
            )
        }
    }

    fun onDismissMarcasDropdown() {
        state.update { it.copy(isMarcasDropdownExpanded = false) }
    }

    fun onDismissModelosDropdown() {
        state.update { it.copy(isModelosDropdownExpanded = false) }
    }

    fun onDismissCoresDropdown() {
        state.update { it.copy(isCoresDropdownExpanded = false) }
    }

    private fun getModelosCarroByMarca(marca: String) {
        viewModelScope.launch {
            try {
                val response = vpicCarrosRepository.getModelosCarroByMarca(marca)

                if (response.isSuccessful) {
                    Log.i("carros", "Sucesso ao buscar modelos de carro: ${response.body()}")
                    state.update {
                        it.copy(
                            modelosCarroData = response.body()?.results?.map { modelo -> modelo.name }
                                ?: emptyList()
                        )
                    }
                } else {
                    Log.e("carros", "Erro ao buscar modelos de carro: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("carros", "Exception -> erro ao buscar modelos de carro: ${e.message}")

            }
        }
    }

    private fun organizeAndCapitalizeList(inputList: List<VpicCarrosMarcaResponse.Marca>): List<String> {
        return inputList.map {
            it.name.lowercase().split(" ").joinToString(" ") { word ->
                word.replaceFirstChar { char ->
                    char.uppercaseChar()
                }
            }
        }.sorted()
    }
}