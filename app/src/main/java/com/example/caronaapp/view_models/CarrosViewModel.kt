package com.example.caronaapp.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.carro.CarroCriacaoDto
import com.example.caronaapp.data.dto.carro.CarroListagemDto
import com.example.caronaapp.data.entity.Carro
import com.example.caronaapp.service.repository.CarroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CarrosViewModel(
    val repository: CarroRepository
) : ViewModel() {

    // carros do Usuário
    var carros = MutableStateFlow<List<CarroListagemDto>?>(null)
        private set

    // controle do modal de Cadastrar Carro
    var isCreateDialogOpened = MutableStateFlow(false)
        private set

    // controle do modal de Editar Carro
    var isEditDialogOpened = MutableStateFlow(false)
        private set

    // controle do modal de Excluir Carro
    var isDeleteDialogOpened = MutableStateFlow(false)
        private set

    val novoCarro = CarroCriacaoDto()
    var editCarro = MutableStateFlow<CarroCriacaoDto?>(null)
        private set
    var deleteCarro = MutableStateFlow<Carro?>(null)
        private set

    var isError = MutableStateFlow(false)
        private set
    var isSuccess = MutableStateFlow(false)
        private set

    var errorMessage = MutableStateFlow("")
        private set
    var successMessage = MutableStateFlow("")
        private set

    init {
//        getCarrosUser()
    }

    private fun getCarrosUser() {
        viewModelScope.launch { carros.value = repository.findByUsuarioId(1).body()!! }
    }

    fun onDismissDeleteDialog() {
        isDeleteDialogOpened.value = false
        deleteCarro.value = null
    }

    fun onDismissCreateDialog() {
        isCreateDialogOpened.value = false
    }

    fun onDismissEditDialog() {
        isEditDialogOpened.value = false
        editCarro.value = null
    }

    fun onDeleteCLick(carro: CarroListagemDto) {
        isDeleteDialogOpened.value = true
        deleteCarro.value = Carro(
            id = carro.id,
            marca = carro.marca,
            modelo = carro.modelo,
            placa = carro.placa,
            cor = carro.cor
        )
    }

    fun onEditCLick(carro: CarroListagemDto) {
        isEditDialogOpened.value = true
        editCarro.value = CarroCriacaoDto(
            id = carro.id,
            marca = carro.marca,
            modelo = carro.modelo,
            placa = carro.placa,
            cor = carro.cor
        )
    }

    fun onCreateCLick() {
        isCreateDialogOpened.value = true
    }

    fun handleDeleteCarro() {
        viewModelScope.launch {
            try {
                val response = repository.delete(deleteCarro.value!!.id)

                if (response.isSuccessful) {
                    // atualizando lista de carros
                    val updatedCarrosList = carros.value!!.filter { carro ->  carro.id != deleteCarro.value!!.id }
                    carros.value = updatedCarrosList
                    deleteCarro.value = null

                    isSuccess.value = true
                    successMessage.value = "Carro excluído com sucesso"
                } else {
                    isError.value = true
                    errorMessage.value = "Não foi possível excluir o carro"
                    Log.e("carros", "Erro ao deletar carro: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                isError.value = true
                errorMessage.value = "Não foi possível excluir o carro"
                Log.e("carros", "Erro ao deletar carro: ${e.message}")
            }
        }
    }

    fun handleCreateCarro() {
        viewModelScope.launch {
            try {
                val response = repository.save(novoCarro)

                if (response.isSuccessful) {
                    // atualizando lista de carros
                    getCarrosUser()

                    isSuccess.value = true
                    successMessage.value = "Carro cadastrado com sucesso"
                } else {
                    isError.value = true
                    errorMessage.value = "Não foi possível cadastrar o carro"
                    Log.e("carros", "Erro ao cadastrar carro: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                isError.value = true
                errorMessage.value = "Não foi possível cadastrar o carro"
                Log.e("carros", "Erro ao cadastrar carro: ${e.message}")
            }
        }
    }

    fun handleEditCarro() {
        viewModelScope.launch {
            try {
                val response = repository.edit(editCarro.value!!.id!!, editCarro.value!!)

                if (response.isSuccessful) {
                    // atualizando lista de carros
                    getCarrosUser()

                    isSuccess.value = true
                    successMessage.value = "Carro editado com sucesso"
                } else {
                    isError.value = true
                    errorMessage.value = "Não foi possível editar o carro"
                    Log.e("carros", "Erro ao editar carro: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                isError.value = true
                errorMessage.value = "Não foi possível editar o carro"
                Log.e("carros", "Erro ao cadastrar carro: ${e.message}")
            }
        }
    }

}