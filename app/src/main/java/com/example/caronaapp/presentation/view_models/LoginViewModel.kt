package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.usuario.UsuarioLoginDto
import com.example.caronaapp.data.repositories.UsuarioRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.utils.functions.setPasswordVisibility
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UsuarioRepositoryImpl,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val userLoginDto = MutableStateFlow(UsuarioLoginDto())

    val isLoading = MutableStateFlow(false)
    val isLoginSuccessful = MutableStateFlow(false)
    val isError = MutableStateFlow(false)
    val showPassword = MutableStateFlow(false)

    fun onLoginClick() {
        viewModelScope.launch {
            isLoading.update { true }
            try {
                Log.i("login", "Login Dto: ${userLoginDto.value}")
                val response = repository.login(userLoginDto.value)
                if (response.isSuccessful) {
                    Log.i("login", "Login realizado com sucesso: ${response.body()}")
                    isLoginSuccessful.update { true }

                    val usuario = response.body()!!
                    dataStoreManager.clearUserData()

                    dataStoreManager.setIdUser(usuario.id)
                    dataStoreManager.setPerfilUser(usuario.perfil)
                    dataStoreManager.setGeneroUser(usuario.genero)

                    Log.i("dataStore", "----------------------------------- ")
                    Log.i("dataStore", "DataStore: ")
                    Log.i("dataStore", "IdUser: ${dataStoreManager.getIdUser()}")
                    Log.i("dataStore", "PerfilUser: ${dataStoreManager.getPerfilUser()}")
                    Log.i("dataStore", "GeneroUser: ${dataStoreManager.getGeneroUser()}")
                } else {
                    Log.e("login", "Erro ao realizar login: ${response.errorBody()}")
                    isError.update { true }
                }
            } catch (e: Exception) {
                Log.e("login", "Erro ao realizar login: ${e.message}")
            } finally {
                isLoading.update { false }
            }
        }
    }

    fun setLoginSuccessfulToFalse() {
        isLoginSuccessful.update { false }
    }

    fun setIsErrorToFalse() {
        isError.update { false }
    }

    fun onEmailChange(email: String) {
        userLoginDto.update { it.copy(email = email) }
    }

    fun onSenhaChange(senha: String) {
        userLoginDto.update { it.copy(senha = senha) }
    }

    fun setLoginPasswordVisibility() {
        showPassword.update { setPasswordVisibility(showPassword.value) }
    }
}