package com.example.caronaapp.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.usuario.UsuarioLoginDto
import com.example.caronaapp.service.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(val repository: UsuarioRepository) : ViewModel() {

    var userLoginDto = MutableStateFlow(UsuarioLoginDto())
        private set

    var isLogged = MutableStateFlow(false)
        private set

    fun onLoginClick() {
        viewModelScope.launch {
            try {
                val response = repository.login(userLoginDto.value)
                if (response.isSuccessful) {
                    isLogged.value = true
                    // salvar infos no Data Store

                    Log.i("login", "Login realizado com sucesso: ${response.body()}")
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