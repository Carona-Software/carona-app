package com.example.caronaapp.presentation.screens.feature.conversas

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.di.DataStoreManager
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    private val firebaseDatabase = Firebase.database

    val perfilUser = MutableStateFlow("")
    private val db = FirebaseFirestore.getInstance()

    private val _searchedUsers = MutableStateFlow<List<UsuarioCriacaoDto>>(emptyList())
    val searchedUsers: StateFlow<List<UsuarioCriacaoDto>> = _searchedUsers

    init {
        viewModelScope.launch { perfilUser.update { dataStoreManager.getPerfilUser() ?: "" } }
    }


    fun searchUsers(query: String) {
        viewModelScope.launch {
            if (query.isNotBlank()) {
                db.collection("users")
                    .whereGreaterThanOrEqualTo("nome", query)
                    .whereLessThanOrEqualTo("nome", query + "\uf8ff")
                    .get()
                    .addOnSuccessListener { snapshot ->
                        val users = snapshot.documents.mapNotNull { document ->
                            document.toObject(UsuarioCriacaoDto::class.java)
                        }
                        _searchedUsers.value = users
                    }
                    .addOnFailureListener { exception ->
                        _searchedUsers.value = emptyList()
                        Log.e("HomeViewModel", "Erro ao buscar usuários: ${exception.message}")
                    }
            } else {
                _searchedUsers.value = emptyList()
            }
        }
    }

    fun clearSearch() {
        _searchedUsers.value = emptyList()
    }

    fun onUserSelected(user: UsuarioCriacaoDto, navController: NavController) {
        navController.navigate("chat_screen/${user.userId}")
    }
}