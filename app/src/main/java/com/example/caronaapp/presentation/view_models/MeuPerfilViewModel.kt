package com.example.caronaapp.presentation.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caronaapp.data.dto.endereco.EnderecoCriacaoDto
import com.example.caronaapp.data.dto.usuario.UsuarioCriacaoDto
import com.example.caronaapp.data.dto.usuario.UsuarioDetalhesListagemDto
import com.example.caronaapp.data.repositories.UsuarioRepositoryImpl
import com.example.caronaapp.data.repositories.ViaCepRepositoryImpl
import com.example.caronaapp.di.DataStoreManager
import com.example.caronaapp.presentation.screens.meu_perfil.AvaliacoesCriterioUser
import com.example.caronaapp.presentation.screens.meu_perfil.MeuPerfilField
import com.example.caronaapp.presentation.screens.meu_perfil.MeuPerfilState
import com.example.caronaapp.presentation.screens.meu_perfil.MeuPerfilValidations
import com.example.caronaapp.utils.functions.calculateCriteriosFeedback
import com.example.caronaapp.utils.functions.isCepValido
import com.example.caronaapp.utils.functions.isEmailValid
import com.example.caronaapp.utils.functions.isNomeValido
import com.example.caronaapp.utils.functions.isNumeroValido
import com.example.caronaapp.utils.functions.isUrlFotoUserValida
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MeuPerfilViewModel(
    private val usuarioRepository: UsuarioRepositoryImpl,
    private val viaCepRepository: ViaCepRepositoryImpl,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val isLoadingScreen = MutableStateFlow(true)
    val perfilUser = MutableStateFlow("")
    val userData = MutableStateFlow<UsuarioDetalhesListagemDto?>(null)
    val isFotoValida = MutableStateFlow(false)
    val avaliacoesCriterioUser = MutableStateFlow(AvaliacoesCriterioUser())
    val meuPerfilState = MutableStateFlow(MeuPerfilState())
    val validations = MutableStateFlow(MeuPerfilValidations())

    private fun getDetalhesUsuario() {
        viewModelScope.launch {
            try {
                perfilUser.update { dataStoreManager.getPerfilUser() ?: "" }

                val idUser = dataStoreManager.getIdUser()
                val response = usuarioRepository.findById(idUser!!)

                if (response.isSuccessful) {
                    Log.i("meu perfil", "Usuario Data: ${response.body()}")

                    userData.update { response.body()!! }
                    meuPerfilState.update {
                        it.copy(
                            nome = userData.value?.nome ?: "",
                            email = userData.value?.email ?: "",
                            cpf = userData.value?.cpf ?: "",
                            perfil = userData.value?.perfil ?: "",
                            genero = userData.value?.genero ?: "",
                            dataNascimento = userData.value?.dataNascimentoDate ?: LocalDate.now(),
                            fotoAtual = userData.value?.fotoUrl ?: "",
                            enderecoCep = userData.value?.endereco?.cep ?: "",
                            enderecoUf = userData.value?.endereco?.uf ?: "",
                            enderecoCidade = userData.value?.endereco?.cidade ?: "",
                            enderecoBairro = userData.value?.endereco?.bairro ?: "",
                            enderecoLogradouro = userData.value?.endereco?.logradouro ?: "",
                            enderecoNumero = userData.value?.endereco?.numero ?: 0
                        )
                    }
                    avaliacoesCriterioUser.update {
                        calculateCriteriosFeedback(userData.value!!.avaliacoes)
                    }
                    isFotoValida.update {
                        if (userData.value == null) false else isUrlFotoUserValida(
                            userData.value!!.fotoUrl
                        )
                    }
                } else {
                    Log.e(
                        "meu perfil",
                        "Erro ao buscar informações do usuário: ${response.errorBody()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "meu perfil",
                    "Exception -> erro ao buscar informações do usuário: ${e.message}"
                )
            } finally {
                isLoadingScreen.update { false }
            }
        }
    }

    fun onChangeEvent(field: MeuPerfilField) {
        when (field) {
            is MeuPerfilField.Nome -> {
                meuPerfilState.update { it.copy(nome = field.value) }
                validations.update { it.copy(isNomeInvalido = (isNomeValido(field.value))) }
            }

            is MeuPerfilField.Email -> {
                meuPerfilState.update { it.copy(email = field.value) }
                validations.update { it.copy(isEmailInvalido = (isEmailValid(field.value))) }
            }

            is MeuPerfilField.Foto -> {
                meuPerfilState.update { it.copy(novaFoto = field.value) }
            }

            is MeuPerfilField.DataNascimento -> {
                meuPerfilState.update { it.copy(dataNascimento = field.value) }
            }

            is MeuPerfilField.EnderecoCep -> {
                meuPerfilState.update { it.copy(enderecoCep = field.value) }
                validations.update { it.copy(isCepInvalido = isCepValido(field.value)) }
            }

            is MeuPerfilField.EnderecoUf -> {
                meuPerfilState.update { it.copy(enderecoUf = field.value) }
            }

            is MeuPerfilField.EnderecoCidade -> {
                meuPerfilState.update { it.copy(enderecoCidade = field.value) }
            }

            is MeuPerfilField.EnderecoBairro -> {
                meuPerfilState.update { it.copy(enderecoBairro = field.value) }
            }

            is MeuPerfilField.EnderecoLogradouro -> {
                meuPerfilState.update { it.copy(enderecoLogradouro = field.value) }
            }

            is MeuPerfilField.EnderecoNumero -> {
                meuPerfilState.update { it.copy(enderecoNumero = field.value) }
                validations.update { it.copy(isNumeroInvalido = isNumeroValido(field.value)) }
            }
        }
    }

    fun onOpenModalClick(context: String) {
        when (context.uppercase()) {
            "NOME" -> meuPerfilState.update { it.copy(isNomeDialogEnabled = true) }
            "EMAIL" -> meuPerfilState.update { it.copy(isEmailDialogEnabled = true) }
            "DATA DE NASCIMENTO" -> meuPerfilState.update { it.copy(isDataNascimentoDialogEnabled = true) }
            "FOTO" -> meuPerfilState.update { it.copy(isFotoDialogEnabled = true) }
            "ENDEREÇO" -> meuPerfilState.update { it.copy(isEnderecoDialogEnabled = true) }
            "LOGOUT" -> meuPerfilState.update { it.copy(isLogoutDialogEnabled = true) }
        }
    }

    fun onDismissModalClick(context: String) {
        when (context.uppercase()) {
            "NOME" -> {
                meuPerfilState.update { it.copy(isNomeDialogEnabled = false) }
            }

            "EMAIL" -> {
                meuPerfilState.update { it.copy(isEmailDialogEnabled = false) }
            }

            "DATA DE NASCIMENTO" -> {
                meuPerfilState.update { it.copy(isDataNascimentoDialogEnabled = false) }
            }

            "FOTO" -> {
                meuPerfilState.update {
                    it.copy(
                        novaFoto = null,
                        isFotoDialogEnabled = false
                    )
                }
            }

            "ENDEREÇO" -> {
                meuPerfilState.update { it.copy(isEnderecoDialogEnabled = false) }
            }

            "LOGOUT" -> {
                meuPerfilState.update { it.copy(isLogoutDialogEnabled = false) }
            }
        }
    }

    fun updateUserData(id: Int) {
        viewModelScope.launch {
            try {
                val userToUpdate = UsuarioCriacaoDto(
                    nome = userData.value!!.nome,
                    email = userData.value!!.email,
                    cpf = userData.value!!.cpf,
                    genero = userData.value!!.genero,
                    perfil = userData.value!!.perfil,
                    dataNascimento = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    fotoUrl = meuPerfilState.value.fotoAtual,
                    endereco = EnderecoCriacaoDto(
                        id = userData.value!!.endereco.id,
                        cep = userData.value!!.endereco.cep,
                        uf = userData.value!!.endereco.uf,
                        cidade = userData.value!!.endereco.cidade,
                        bairro = userData.value!!.endereco.bairro,
                        logradouro = userData.value!!.endereco.logradouro,
                        numero = userData.value!!.endereco.numero,
                    ),
                )

                val response = usuarioRepository.update(id, userToUpdate)

                if (response.isSuccessful) {
                    userData.value = response.body()
                } else {
                    userData.value = null
                    Log.e(
                        "meu perfil",
                        "Erro ao atualizar usuário: ${response.errorBody()?.toString()}"
                    )
                }
            } catch (e: Exception) {
                userData.value = null
                Log.e("meu perfil", "Erro ao atualizar usuário: ${e.printStackTrace()}")
            }
        }
    }

    fun handleSearchCep() {
        viewModelScope.launch {
            try {
                val getEndereco = viaCepRepository.getEndereco(meuPerfilState.value.enderecoCep)

                if (getEndereco.isSuccessful) {
                    Log.i("viacep", "Resposta: ${getEndereco.body()}")
                    if (getEndereco.body()?.uf != null) {
                        meuPerfilState.update {
                            it.copy(
                                enderecoUf = getEndereco.body()?.uf.toString(),
                                enderecoCidade = getEndereco.body()?.cidade.toString(),
                                enderecoBairro = getEndereco.body()?.bairro.toString(),
                                enderecoLogradouro = getEndereco.body()?.logradouro.toString(),
                            )
                        }
                    } else {
                        validations.update { it.copy(isCepInvalido = true) }
                        meuPerfilState.update {
                            it.copy(
                                enderecoUf = "",
                                enderecoCidade = "",
                                enderecoBairro = "",
                                enderecoLogradouro = "",
                            )
                        }
                    }
                } else {
                    Log.e(
                        "viacep",
                        "Erro na resposta: ${getEndereco.errorBody()!!.string()}"
                    )
                    validations.update { it.copy(isCepInvalido = true) }
                }

            } catch (e: Exception) {
                Log.e(
                    "viacep",
                    "Erro ao buscar CEP ${meuPerfilState.value.enderecoCep}: ${e.printStackTrace()}"
                )

            }
        }
    }

    init {
        getDetalhesUsuario()
    }

    fun clearDataStore() {
        viewModelScope.launch { dataStoreManager.clearUserData() }
    }

}
