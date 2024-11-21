package com.example.caronaapp.di

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class DataStoreManager(private val context: Context) {

    private val ID_USER = intPreferencesKey("idUser")
    private val PERFIL_USER = stringPreferencesKey("perfilUser")
    private val GENERO_USER = stringPreferencesKey("generoUser")

    // Gravação
    suspend fun setIdUser(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[ID_USER] = id
        }
    }

    suspend fun setPerfilUser(perfil: String) {
        context.dataStore.edit { preferences ->
            preferences[PERFIL_USER] = perfil
        }
    }

    suspend fun setGeneroUser(genero: String) {
        context.dataStore.edit { preferences ->
            preferences[GENERO_USER] = genero
        }
    }

    // Leitura
    private val idUserFlow: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[ID_USER]
    }

    private val perfilUserFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PERFIL_USER]
    }

    private val generoUserFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[GENERO_USER]
    }

    suspend fun getIdUser(): Int? {
        return idUserFlow.first()
    }

    suspend fun getPerfilUser(): String? {
        return perfilUserFlow.first()
    }

    suspend fun getGeneroUser(): String? {
        return generoUserFlow.first()
    }

    // Limpar
    suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}