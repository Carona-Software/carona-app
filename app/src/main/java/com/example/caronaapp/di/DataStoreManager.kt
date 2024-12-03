package com.example.caronaapp.di

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class DataStoreManager(private val context: Context) {

    private val ONBOARDING_DONE = booleanPreferencesKey("onboardingDone")
    private val ID_USER = intPreferencesKey("idUser")
    private val PERFIL_USER = stringPreferencesKey("perfilUser")
    private val GENERO_USER = stringPreferencesKey("generoUser")
    private val TOKEN_FIREBASE_USER = stringPreferencesKey("tokenFirebaseUser")

    // Gravação
    suspend fun setOnboardingDone() {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_DONE] = true
        }
    }

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

    suspend fun setTokenFirebaseUser(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_FIREBASE_USER] = token
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

    private val onboardingDoneFlow: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[ONBOARDING_DONE]
    }

    private val tokenFirebaseUserFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_FIREBASE_USER]
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

    suspend fun getOnboardingState(): Boolean? {
        return onboardingDoneFlow.first()
    }

    suspend fun getTokenFirebaseUser(): String? {
        return tokenFirebaseUserFlow.first()
    }

    // Limpar
    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences[ID_USER] = 0
            preferences[PERFIL_USER] = ""
            preferences[GENERO_USER] = ""
        }
    }
}