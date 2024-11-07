package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.usuario.UsuarioLoginDto
import com.example.caronaapp.data.repositories.UsuarioRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UsuarioRepositoryImpl,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var userLoginDto = MutableStateFlow(UsuarioLoginDto())
        private set

    var isLogged = MutableStateFlow(false)
        private set

    fun onLoginClick() {
        viewModelScope.launch {
            try {
                val response = repository.login(userLoginDto.value)
                if (response.isSuccessful) {
                    Log.i("login", "Login realizado com sucesso: ${response.body()}")
                    isLogged.value = true
                    dataStoreManager.setIdUser(response.body()!!.id)
                    dataStoreManager.setFotoUser(response.body()!!.fotoUrl)
                    dataStoreManager.setPerfilUser(response.body()!!.perfil)
                } else {
                    Log.e("login", "Erro ao realizar login: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("login", "Erro ao realizar login: ${e.message}")
            }
        }
    }

    fun onEmailChange(email: String) {
        userLoginDto.update { it.copy(email = email) }
    }

    fun onSenhaChange(senha: String) {
        userLoginDto.update { it.copy(senha = senha) }
    }
}