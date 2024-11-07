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

    val ID_USER = intPreferencesKey("idUser")
    val FOTO_USER = stringPreferencesKey("fotoUser")
    val PERFIL_USER = stringPreferencesKey("perfilUser")

    // Gravação
    suspend fun setIdUser(id: Int) {
        context.dataStore.edit { preferences ->
            preferences[ID_USER] = id
        }
    }

    suspend fun setFotoUser(fotoUrl: String) {
        context.dataStore.edit { preferences ->
            preferences[FOTO_USER] = fotoUrl
        }
    }

    suspend fun setPerfilUser(perfil: String) {
        context.dataStore.edit { preferences ->
            preferences[PERFIL_USER] = perfil
        }
    }

    // Leitura
    private val idUserFlow: Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[ID_USER]
    }

    private val fotoUserFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[FOTO_USER]
    }

    private val perfilUserFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PERFIL_USER]
    }

    suspend fun getIdUser(): Int? {
        return idUserFlow.first()
    }

    suspend fun getFotoUser(): String? {
        return fotoUserFlow.first()
    }

    suspend fun getPerfilUser(): String? {
        return perfilUserFlow.first()
    }
}