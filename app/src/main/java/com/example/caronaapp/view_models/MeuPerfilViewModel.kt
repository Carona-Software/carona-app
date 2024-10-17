package com.example.caronaapp.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.service.repository.UsuarioRepository
import com.example.caronaapp.service.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MeuPerfilViewModel(
    val repository: UsuarioRepository
): ViewModel() {

    var userInfo = MutableStateFlow<UsuarioDetalhesListagemDto?>(null)
        private set

    var userToUpdate = MutableStateFlow<UsuarioCriacaoDto?>(null)

    private val apiUsuario: UsuarioRepository

    init {
        apiUsuario = RetrofitService.getApiUsuario()
        getDetalhesUsuario(1)
    }


    fun getDetalhesUsuario(id: Int) {
        viewModelScope.launch {
            try {
                val response = apiUsuario.findById(id)

                if (response.isSuccessful) {
                    userInfo.value = response.body()!!
                } else {
                    Log.e("meu perfil", "Erro ao buscar informações do usuário: ${response.errorBody()?.toString()}")
                }
            } catch (e: Exception) {
                Log.e("meu perfil", "Erro ao buscar informações do usuário: ${e.message}")
            }
        }
    }

}